package payments.money;

import enums.Currency;
import payments.MoneyPaymentMethod;
import payments.PaymentMethod;

public class Coin extends MoneyPaymentMethod{

    private final Currency currency;
    private final double value;

    public Coin(Currency currency, double value) {
        this.currency = currency;
        this.value = value;
    }

    public Currency getCurrency(){
        return this.currency;
    }

    public double getValue(){
        return this.value;
    }

    @Override
    public int compareTo(PaymentMethod o) {
        Coin coin = (Coin) o;
        if (! this.getCurrency().equals(coin.getCurrency()))
            return -1;
        if (this.getValue() != coin.getValue() )
            return 1;
        return 0;
    }
}
