package machines.food.snack;

import Exceptions.*;
import displays.food.SnackVendingMachineDisplay;
import keypads.food.SnackVendingMachineKeypad;
import machines.food.FoodVendingMachine;
import payment_systems.food.systems.SnackVendingPaymentSystem;
import payments.PaymentMethod;
import payments.money.Card;
import payments.money.Coin;
import payments.money.Note;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class SnackVendingMachine extends FoodVendingMachine {

    public ArrayList<String> boughtItems = new ArrayList<>();
    public final int MAX_SIZE = 5;
    public final int COLS = 5;
    public final int ROWS = 5;
    private char chosenRow = '_';
    private char chosenCol = '_';
    private SnackVendingPaymentSystem paymentSystem;

    public SnackVendingMachine(SnackVendingMachineKeypad keypad, SnackVendingMachineDisplay display, SnackVendingPaymentSystem paymentSystem) {
        this.keypad = keypad;
        this.display = display;
        this.paymentSystem = paymentSystem;
        this.prices = new double[ROWS][COLS];
        inventory = new Stack[ROWS][COLS];

        init();
    }

    private void init() {

        display.setCurrentDisplay("WAITING ORDER");

        // default pricing set to 1
        for (double[] price : prices)
            Arrays.fill(price, 1);

        // create stack slots in the inventory
        for ( int i = 0; i < inventory.length;i++)
            for ( int j = 0 ; j < inventory[i].length;j++)
                inventory[i][j] = new Stack<String>();

    }

    @Override
    public void pressKey(String key){

        if (!keypad.hasKey(key))
            throw new UnknownKeyException(key + " is an unknown key to this machine");
        try {
            switch (key){

                case "A":
                case "B":
                case "C":
                case "D":
                case "E":
                    rowButtonAction(key);
                    break;
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                    colButtonAction(key);
                    break;
                case "OK":
                    okButtonAction();
                    break;
            }
        }
        catch (OutOfStockException e){
            display.setCurrentDisplay("ITEM OUT");
            resetTheChoice();
        }
        catch (NeedChoiceException e){
            display.setCurrentDisplay("CHOOSE FIRST");
            resetTheChoice();

        }


    }

    private void okButtonAction(){
        if(machineState == states.WAITING_ORDER){
            if ( ('A' > chosenRow || chosenRow > 'E') || ('1' > chosenCol || chosenCol > '5') )
                throw new NeedChoiceException(" Need to choose an item first ");

            if (inventory[chosenRow-'A'][chosenCol-'1'].size()==0)
                throw new OutOfStockException("["+chosenRow+"]["+chosenCol+"] is out of stock");

            double itemPrice = getPrice(chosenRow-'A',chosenCol-'1');
            String itemName = (String) inventory[chosenRow - 'A'][chosenCol - '1'].peek();

            paymentSystem.setRequiredMoney(itemPrice);
            display.setCurrentDisplay(itemName +": "+itemPrice);
            machineState = states.WAITING_MONEY;
        }
    }

    public ArrayList<PaymentMethod> cancelOrder(){
        if(machineState == states.WAITING_MONEY){
            resetTheChoice();
            machineState = states.WAITING_ORDER;
            display.setCurrentDisplay("WAITING ORDER");
            System.out.println(" Order Cancelled");
            paymentSystem.setRequiredMoney(0);
        }
        return paymentSystem.refundAll();
    }

    public ArrayList<Coin> refundCoins(){
        ArrayList<Coin> coins = paymentSystem.refundCoins();
        display.setCurrentDisplay("Inserted "+paymentSystem.getTotalInsertedMoney());
        return coins;
    }

    public ArrayList<Note> refundNotes(){
        ArrayList<Note> notes = paymentSystem.refundNotes();
        display.setCurrentDisplay("Inserted "+paymentSystem.getTotalInsertedMoney());
        return notes;
    }

    public Card refundCard(){
        Card card = paymentSystem.refundCard();
        display.setCurrentDisplay("Inserted "+paymentSystem.getTotalInsertedMoney());
        return card;
    }

    public double insertCoin(Coin coin) throws Exception {
        double change = 0;
        if (machineState == states.WAITING_MONEY){
            double totalInsertedMoney = paymentSystem.insertCoin(coin);
            display.setCurrentDisplay("Inserted " + totalInsertedMoney);
            change = isEnoughMoneyInserted();
        }
        else
            System.out.println("Not accepted, you can insert coins after selecting order");

        return change;
    }

    public double insertNote(Note note) throws Exception {
        double change = 0;
        if (machineState == states.WAITING_MONEY){
            double totalInsertedMoney = paymentSystem.insertNote(note);
            display.setCurrentDisplay("Inserted " + totalInsertedMoney);
            change = isEnoughMoneyInserted();
        }
        else
            System.out.println("Not accepted, you can insert notes after selecting order");
        return change;
    }

    public double insertCard(Card card) throws Exception {
        double change = 0;
        if (machineState == states.WAITING_MONEY){
            double totalInsertedMoney = paymentSystem.insertCard(card);
            display.setCurrentDisplay("Inserted " + totalInsertedMoney);
            change = isEnoughMoneyInserted();
        }
        else
            System.out.println("Not accepted, you can insert card after selecting order");
        return change;
    }

    private double isEnoughMoneyInserted() throws InterruptedException {
        double totalInsertedMoney = paymentSystem.getTotalInsertedMoney();
        double requiredMoney = paymentSystem.getRequiredMoney();
        double change = 0;
        if(totalInsertedMoney >= requiredMoney )
        {
            System.out.println(getDisplay());
            change = totalInsertedMoney-requiredMoney;
            String item = (String) inventory[chosenRow-'A'][chosenCol-'1'].pop();
            boughtItems.add(item);
            display.setCurrentDisplay("your change = " + change);
            System.out.println("your change = " + change);
            Thread.sleep(4000);
            display.setCurrentDisplay("WAITING ORDER");
            resetTheChoice();
            machineState = states.WAITING_ORDER;
            paymentSystem.setRequiredMoney(0);
            paymentSystem.refundAll();
        }
        return change;
    }

    private void resetTheChoice(){
        chosenCol='_';
        chosenRow='_';
    }

    private void rowButtonAction( String key){
        if (machineState == states.WAITING_ORDER){
            chosenRow = key.charAt(0);
            display.setCurrentDisplay(chosenRow+""+chosenCol);
        }
    }

    private void colButtonAction( String key){
        if (machineState == states.WAITING_ORDER)
        {
            chosenCol = key.charAt(0);
            display.setCurrentDisplay(chosenRow+""+chosenCol);
        }
    }

    @Override
    public void setPrice(int row, int col, int newPrice) {
        if ( newPrice <= 0)
            throw new WrongValueException("using negative value for item price is not allowed");

        prices[row][col] = newPrice;
    }

    @Override
    public void fillInventorySlot(int row, int col, String item, int amount) throws WrongValueException,
            FullInventorySlotException {

        if (amount < 0)
            throw new WrongValueException("if 'amount' < 0 then you are stealing, be more careful next time");

        for (; inventory[row][col].size() != MAX_SIZE && amount != 0; amount--) {
            inventory[row][col].push(item);
            amount--;
        }
        if (amount > 0)
            throw new FullInventorySlotException("The slot is full and " + amount + " couldn't be added");

    }

    public double getPrice(int row, int col){
        return prices[row][col];
    }

    public String getInventorySlotContent( int row, int col){

        Stack inventorySlot = inventory[row][col];
        return inventorySlot.toString() +  "(size="+inventorySlot.size()+")";
    }

    public String getDisplay(){
        return display.getCurrentDisplay();
    }
}
