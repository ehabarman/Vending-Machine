package machines.food.snack;

import Exceptions.*;
import displays.DisplayFactory;
import displays.food.SnackVendingMachineDisplay;
import enums.CardTypes;
import enums.Currency;
import keypads.KeypadFactory;
import keypads.food.SnackVendingMachineKeypad;
import org.junit.Test;
import payment_systems.PaymentSystemFactoryBuilder;
import payment_systems.food.FoodVendingPaymentSystemFactory;
import payment_systems.food.systems.SnackVendingPaymentSystem;
import payments.money.Card;
import payments.money.Coin;
import payments.money.Note;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SnackVendingMachineTest {

    @Test
    public void pressKey() {
        SnackVendingMachine snackVendingMachine = buildMachine();

        assertThrows(UnknownKeyException.class, () -> {
            snackVendingMachine.pressKey("0");
        });

        assertThrows(UnknownKeyException.class, () -> {
            snackVendingMachine.pressKey("a");
        });

        snackVendingMachine.pressKey("OK");
        assertTrue(snackVendingMachine.getDisplay().compareTo("CHOOSE FIRST") == 0);

        snackVendingMachine.pressKey("A");
        snackVendingMachine.pressKey("1");
        snackVendingMachine.pressKey("OK");
        assertTrue(snackVendingMachine.getDisplay().compareTo("ITEM OUT") == 0);

        initSnackMachine(snackVendingMachine);

        snackVendingMachine.pressKey("A");
        snackVendingMachine.pressKey("1");
        snackVendingMachine.pressKey("OK");
        assertTrue(snackVendingMachine.getDisplay().compareTo("Galaxy: 1.0") == 0);

    }

    @Test
    public void cancelOrder() {
        SnackVendingMachine snackVendingMachine = buildMachine();
        assertTrue(snackVendingMachine.cancelOrder().size() == 0);
        initSnackMachine(snackVendingMachine);
        snackVendingMachine.pressKey("A");
        snackVendingMachine.pressKey("1");
        snackVendingMachine.pressKey("OK");
        assertTrue(snackVendingMachine.getDisplay().compareTo("Galaxy: 1.0") == 0);
        assertTrue(snackVendingMachine.cancelOrder().size() == 0);
        assertTrue(snackVendingMachine.getDisplay().compareTo("WAITING ORDER") == 0);

    }

    @Test
    public void InsertRefundCoins() throws Exception {
        SnackVendingMachine snackVendingMachine = buildMachine();
        initSnackMachine(snackVendingMachine);
        Coin coin1 = new Coin(Currency.USD, 0.5);
        Coin coin2 = new Coin(Currency.USD, 0.2);
        Coin coin3 = new Coin(Currency.USD, 0.2);
        Coin coin4 = new Coin(Currency.USD, 0.2);

        snackVendingMachine.insertCoin(coin1);
        assertTrue(snackVendingMachine.getDisplay().compareTo("WAITING ORDER") == 0);
        snackVendingMachine.pressKey("A");
        snackVendingMachine.pressKey("1");
        snackVendingMachine.pressKey("OK");
        snackVendingMachine.insertCoin(coin1);
        assertTrue(snackVendingMachine.getDisplay().compareTo("Inserted 0.5") == 0);
        snackVendingMachine.insertCoin(coin2);
        snackVendingMachine.insertCoin(coin3);
        assertTrue(snackVendingMachine.getDisplay().compareTo("Inserted " + (coin1.getValue() +
                coin2.getValue() + coin3.getValue())) == 0);
        assertTrue(snackVendingMachine.refundCoins().size() == 3);
        assertTrue(snackVendingMachine.getDisplay().compareTo("Inserted 0.0") == 0);

        snackVendingMachine.insertCoin(coin1);
        snackVendingMachine.insertCoin(coin2);
        snackVendingMachine.insertCoin(coin3);
        snackVendingMachine.insertCoin(coin4);
        assertTrue(snackVendingMachine.getDisplay().compareTo("WAITING ORDER") == 0);

    }

    @Test
    public void InsertRefundNotes() throws Exception {

        SnackVendingMachine snackVendingMachine = buildMachine();
        initSnackMachine(snackVendingMachine);
        snackVendingMachine.setPrice(0,0,50);
        Note note1 = new Note(Currency.USD, 20);
        Note note2 = new Note(Currency.USD, 20);
        Note note3 = new Note(Currency.USD, 20);
        snackVendingMachine.insertNote(note1);
        assertTrue(snackVendingMachine.getDisplay().compareTo("WAITING ORDER") == 0);
        snackVendingMachine.pressKey("A");
        snackVendingMachine.pressKey("1");
        snackVendingMachine.pressKey("OK");
        snackVendingMachine.insertNote(note1);
        assertTrue(snackVendingMachine.getDisplay().compareTo("Inserted 20.0") == 0);
        snackVendingMachine.insertNote(note2);
        assertTrue(snackVendingMachine.getDisplay().compareTo("Inserted 40.0") == 0);
        assertTrue(snackVendingMachine.refundNotes().size() == 2);
        assertTrue(snackVendingMachine.getDisplay().compareTo("Inserted 0.0") == 0);

        snackVendingMachine.insertNote(note1);
        snackVendingMachine.insertNote(note2);
        snackVendingMachine.insertNote(note3);
        assertTrue(snackVendingMachine.getDisplay().compareTo("WAITING ORDER") == 0);
    }

    @Test
    public void InsertRefundCard() throws Exception {
        SnackVendingMachine snackVendingMachine = buildMachine();
        initSnackMachine(snackVendingMachine);
        snackVendingMachine.setPrice(0,0,50);
        Card card = new Card(Currency.USD, 4, CardTypes.VISA);
        snackVendingMachine.insertCard(card);
        assertTrue(snackVendingMachine.getDisplay().compareTo("WAITING ORDER") == 0);
        snackVendingMachine.pressKey("E");
        snackVendingMachine.pressKey("1");
        snackVendingMachine.pressKey("OK");
        snackVendingMachine.insertCard(card);
        assertTrue(snackVendingMachine.getDisplay().compareTo("Inserted 4.0") == 0);
        assertTrue(snackVendingMachine.refundCard() == card);
        assertTrue(snackVendingMachine.getDisplay().compareTo("Inserted 0.0") == 0);
        snackVendingMachine.cancelOrder();

        snackVendingMachine.pressKey("D");
        snackVendingMachine.pressKey("1");
        snackVendingMachine.pressKey("OK");
        snackVendingMachine.insertCard(card);
        assertTrue(snackVendingMachine.getDisplay().compareTo("WAITING ORDER") == 0);
    }

    @Test
    public void slotContentAndPrice() {
        SnackVendingMachine snackVendingMachine = buildMachine();
        initSnackMachine(snackVendingMachine);
        assertTrue(snackVendingMachine.getPrice(0, 3) == 1);
        assertTrue(snackVendingMachine.getPrice(1, 2) == 2);
        assertTrue(snackVendingMachine.getPrice(2, 1) == 3);
        assertTrue(snackVendingMachine.getPrice(3, 0) == 4);
        assertTrue(snackVendingMachine.getInventorySlotContent(0, 0).compareTo(
                "[Galaxy, Galaxy, Galaxy, Galaxy, Galaxy](size=5)") == 0);
        assertTrue(snackVendingMachine.getInventorySlotContent(4, 4).compareTo(
                "[Large Cake, Large Cake, Large Cake, Large Cake, Large Cake](size=5)") == 0);
        assertThrows(WrongValueException.class, () -> {
            snackVendingMachine.setPrice(0, 0, -5);
        });
        assertThrows(FullInventorySlotException.class, () -> {
            snackVendingMachine.fillInventorySlot(0, 0, "test item", 1);
        });


    }

    private SnackVendingMachine buildMachine() {
        SnackVendingMachineKeypad snackVendingMachineKeypad = (SnackVendingMachineKeypad) KeypadFactory.buildKeypad(
                KeypadFactory.SNACK_VENDING_MACHINE_KEYPAD);

        FoodVendingPaymentSystemFactory foodVendingPaymentSystemFactory =
                (FoodVendingPaymentSystemFactory) PaymentSystemFactoryBuilder.buildPaymentSystemFactory(
                        PaymentSystemFactoryBuilder.FOOD_VENDING_PAYMENT_SYSTEM_FACTORY);

        SnackVendingPaymentSystem snackVendingPaymentSystem = (SnackVendingPaymentSystem) foodVendingPaymentSystemFactory.
                buildSystem(FoodVendingPaymentSystemFactory.SNACK_VENDING_MACHINE_PAYMENT_SYSTEM);

        SnackVendingMachineDisplay snackVendingMachineDisplay =
                (SnackVendingMachineDisplay) DisplayFactory.buildDisplay(DisplayFactory.SNACK_VENDING_MACHINE_DISPLAY);

        return new SnackVendingMachine(snackVendingMachineKeypad, snackVendingMachineDisplay, snackVendingPaymentSystem);
    }

    private void initSnackMachine(SnackVendingMachine snackVendingMachine) {
        int rows = snackVendingMachine.ROWS;
        int cols = snackVendingMachine.COLS;
        int maxSize = snackVendingMachine.MAX_SIZE;
        String[][] items = {
                {"Galaxy", "Dairy Milk", "Small MR Chips", "Small Tiger", "Cake"},
                {"Bubbles", "moon", "small lotus", "Azka", " Tirma"},
                {"Flutes", "sharks", "Dairy Milk Bubbles", "Galaxy Bubbles", "big lotus"},
                {"Medium MR Chips", "Medium Tiger", "Medium Flutes", "Jum", "Taco"},
                {"Large MR Chips", "Large Tiger", "Large Flutes", "Large Galaxy", "Large Cake"},
        };

        // initialize prices to the row number
        // row 1 = 1, row 2 = 2, ..... row 5 = 5
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                snackVendingMachine.setPrice(i, j, i + 1);

        // fill inventory with items
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++) {
                snackVendingMachine.fillInventorySlot(i, j, items[i][j], maxSize);
            }
    }
}