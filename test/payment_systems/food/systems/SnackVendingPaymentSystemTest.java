package payment_systems.food.systems;

import Exceptions.SlotSupportLimitationException;
import enums.CardTypes;
import enums.Currency;
import org.junit.Test;
import payments.money.Card;
import payments.money.Coin;
import payments.money.Note;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SnackVendingPaymentSystemTest {

    @Test
    public void insertRefundCoinTest() throws Exception {
        SnackVendingPaymentSystem paymentSystem = new SnackVendingPaymentSystem();
        Coin acceptableCoin = new Coin(Currency.USD, 0.1);
        Coin notAcceptableCoin = new Coin(Currency.USD, 0.25);
        paymentSystem.insertCoin(acceptableCoin);
        assertTrue(paymentSystem.refundCoins().get(0) == acceptableCoin);
        assertThrows(SlotSupportLimitationException.class, () -> {
            paymentSystem.insertCoin(notAcceptableCoin);
        });
        assertTrue(paymentSystem.refundCoins().size() == 0);
    }

    @Test
    public void insertRefundNoteTest() throws Exception {
        SnackVendingPaymentSystem paymentSystem = new SnackVendingPaymentSystem();
        Note acceptableNote = new Note(Currency.USD, 20);
        Note notAcceptableNote = new Note(Currency.USD, 10);
        paymentSystem.insertNote(acceptableNote);
        assertEquals(true, paymentSystem.refundNotes().get(0) == acceptableNote);
        assertThrows(SlotSupportLimitationException.class, () -> {
            paymentSystem.insertNote(notAcceptableNote);
        });
        assertEquals(true, paymentSystem.refundNotes().size() == 0);
    }

    @Test
    public void insertRefundCardTest() throws Exception {
        SnackVendingPaymentSystem paymentSystem = new SnackVendingPaymentSystem();
        Card acceptableCard = new Card(Currency.USD, 30, CardTypes.VISA);
        Card notAcceptableCard = new Card(Currency.JD, 30, CardTypes.VISA);
        paymentSystem.insertCard(acceptableCard);
        assertTrue(paymentSystem.refundCard() == acceptableCard);
        assertThrows(SlotSupportLimitationException.class, () -> {
            paymentSystem.insertCard(notAcceptableCard);
        });
        assertTrue(paymentSystem.refundCard() == null);
    }

    @Test
    public void requiredMoneyTest() {
        SnackVendingPaymentSystem paymentSystem = new SnackVendingPaymentSystem();
        paymentSystem.setRequiredMoney(50.5);
        assertTrue(paymentSystem.getRequiredMoney() == 50.5);
    }

    @Test
    public void insertRefundAllTest() throws Exception {
        SnackVendingPaymentSystem paymentSystem = new SnackVendingPaymentSystem();
        Card acceptableCard = new Card(Currency.USD, 30, CardTypes.VISA);
        Note acceptableNote = new Note(Currency.USD, 20);
        Coin acceptableCoin = new Coin(Currency.USD, 0.1);
        paymentSystem.insertCard(acceptableCard);
        paymentSystem.insertNote(acceptableNote);
        paymentSystem.insertCoin(acceptableCoin);

        assertTrue(paymentSystem.getTotalInsertedMoney() == 50.1);
        paymentSystem.refundCard();
        assertTrue(paymentSystem.getTotalInsertedMoney() == 20.1);
        paymentSystem.refundAll();
        assertTrue(paymentSystem.getTotalInsertedMoney() == 0);

    }
}