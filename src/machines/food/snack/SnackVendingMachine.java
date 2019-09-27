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
            resetWaitingOrderState();
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
            resetWaitingOrderState();
            paymentSystem.setRequiredMoney(0);
        }
        return paymentSystem.refundAll();
    }

    public ArrayList<Coin> refundCoins(){
        return paymentSystem.refundCoins();
    }

    public ArrayList<Note> refundNotes(){
        return paymentSystem.refundNotes();
    }

    public Card refundCard(){
        return paymentSystem.refundCard();
    }

    public void insertCoin(Coin coin) throws Exception {
        if (machineState == states.WAITING_MONEY){
            double totalInsertedMoney = paymentSystem.insertCoin(coin);
            display.setCurrentDisplay("Inserted " + totalInsertedMoney);
            isEnoughMoneyInserted();
        }
        else
            System.out.println("Not accepted, you can insert coins after selecting order");
    }

    public void insertNote(Note note) throws Exception {
        if (machineState == states.WAITING_MONEY){
            double totalInsertedMoney = paymentSystem.insertNote(note);
            display.setCurrentDisplay("Inserted " + totalInsertedMoney);
            isEnoughMoneyInserted();
        }
        else
            System.out.println("Not accepted, you can insert notes after selecting order");
    }

    public void insertCard(Card card) throws Exception {
        if (machineState == states.WAITING_MONEY){
            double totalInsertedMoney = paymentSystem.insertCard(card);
            display.setCurrentDisplay("Inserted " + totalInsertedMoney);
            isEnoughMoneyInserted();
        }
        else
            System.out.println("Not accepted, you can insert card after selecting order");
    }

    private void isEnoughMoneyInserted(){
        double totalInsertedMoney = paymentSystem.getTotalInsertedMoney();
        double requiredMoney = paymentSystem.getRequiredMoney();

        if(totalInsertedMoney >= requiredMoney )
        {
            System.out.println("pending item: "+ inventory[chosenRow-'A'][chosenCol-'1'].pop());
            System.out.println("your change = " + (totalInsertedMoney-requiredMoney));
            resetWaitingOrderState();
            machineState = states.WAITING_ORDER;
            paymentSystem.setRequiredMoney(0);
            paymentSystem.refundAll();
        }
    }

    private void resetWaitingOrderState(){
        chosenCol='_';
        chosenRow='_';
        display.setCurrentDisplay("WAITING ORDER");
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
}
