package payments.money;

import enums.CardTypes;
import enums.Currency;
import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {

    @Test
    public void CardTest() {
        Card card = new Card(Currency.USD, 20, CardTypes.VISA);
        assertEquals(20.0,card.getBalance(),0);
        assertEquals(Currency.USD,card.getCurrency());
        assertEquals(CardTypes.VISA,card.getType());
        assertEquals(0,card.compareTo(new Card(Currency.USD, 20, CardTypes.VISA)));
        assertNotEquals(0,card.compareTo(new Card(Currency.EURO, 20, CardTypes.VISA)));
        assertEquals(20.0,card.pay(20),0);
        assertEquals(0.0,card.pay(30),0);
    }
}