package slots;

import Exceptions.CardSlotInUseException;
import Exceptions.SlotSupportLimitationException;
import enums.CardTypes;
import enums.Currency;
import org.junit.Test;
import payments.money.Card;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CardSlotTest {

    @Test
    public void CardSlotTest() {

        Card[] acceptableCards = {new Card(Currency.USD, 0, CardTypes.VISA),
                new Card(Currency.USD, 0, CardTypes.CREDITCARD),
                new Card(Currency.USD, 0, CardTypes.SHOPPINGCARD)};

        CardSlot cardSlot = new CardSlot(acceptableCards);

        assertThrows(SlotSupportLimitationException.class, () -> {
            cardSlot.insertCard(new Card(Currency.JD, 0, CardTypes.VISA));
        });

        Card card = new Card(Currency.USD, 20, CardTypes.VISA);
        assertEquals(20, cardSlot.insertCard(card), 0);

        assertThrows(CardSlotInUseException.class, () -> {
            cardSlot.insertCard(new Card(Currency.USD, 0, CardTypes.VISA));
        });

        assertEquals(true, card == cardSlot.removeCard());


    }
}