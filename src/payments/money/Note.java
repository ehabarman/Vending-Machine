package payments.money;


import enums.Currency;
import payments.MoneyPaymentMethod;
import payments.PaymentMethod;

public class Note extends MoneyPaymentMethod {

    private final Currency currency;
    private final double value;

    public Note(Currency currency, double value) {
        this.currency = currency;
        this.value = value;
    }

    @Override
    public Currency getCurrency() {
        return this.currency;
    }

    public double getValue() {
        return this.value;
    }

    @Override
    public int compareTo(PaymentMethod o) {
        Note note = (Note) o;
        if (! this.getCurrency().equals(note.getCurrency()))
            return -1;
        if (this.getValue() != note.getValue() )
            return 1;
        return 0;
    }
}
