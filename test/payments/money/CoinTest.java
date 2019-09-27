package payments.money;

import enums.Currency;
import org.junit.Test;

import static org.junit.Assert.*;

public class CoinTest {

    @Test
    public void CoinTest(){
        Coin coin = new Coin(Currency.USD, 20);
        assertEquals(20.0,coin.getValue(),0);
        assertEquals(Currency.USD,coin.getCurrency());
        assertEquals(0,coin.compareTo(new Coin(Currency.USD, 20)));
        assertNotEquals(0,coin.compareTo(new Coin(Currency.EURO, 20)));
    }

}