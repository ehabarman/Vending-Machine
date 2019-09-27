package slots;

import Exceptions.SlotSupportLimitationException;
import enums.Currency;
import org.junit.Test;
import payments.money.Coin;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CoinSlotTest {

    @Test
    public void CoinSlotTest() {
        Coin[] acceptableCoins = {new Coin(Currency.USD, 0.1), new Coin(Currency.USD, 0.2),
                new Coin(Currency.USD, 0.5), new Coin(Currency.USD, 1.0)};
        CoinSlot coinSlot = new CoinSlot(acceptableCoins);

        assertThrows(SlotSupportLimitationException.class, () -> {
            coinSlot.insertCoin(new Coin(Currency.USD, 0.6));
        });

        Coin coin = new Coin(Currency.USD, 1);
        assertEquals(true, coinSlot.insertCoin(coin) == 1);
        coin = new Coin(Currency.USD, 0.5);
        assertEquals(0.5, coinSlot.insertCoin(coin), 0);
        assertEquals(1.5, coinSlot.getInsertedMoney(), 0);
        assertEquals(true, coinSlot.refund() instanceof ArrayList);
        assertEquals(0.0, coinSlot.getInsertedMoney(), 0);
    }

}