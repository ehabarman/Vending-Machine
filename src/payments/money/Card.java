package payments.money;

import enums.CardTypes;
import enums.Currency;
import payments.MoneyPaymentMethod;
import payments.PaymentMethod;


public class Card extends MoneyPaymentMethod {

    private final Currency currency;
    private double balance;
    private final CardTypes type;

    public Card(Currency currency, double balance, CardTypes type) {
        this.currency = currency;
        this.balance = balance;
        this.type = type;
    }

    @Override
    public Currency getCurrency() {
        return this.currency;
    }

    public double getBalance() {
        return this.balance;
    }

    public CardTypes getType(){
        return this.type;
    }

    public boolean hasSufficientBalance( double value){
        if ( value <= getBalance())
            return true;
        return false;
    }

    public double pay(double value){

        if(hasSufficientBalance(value)){
            balance -= value;
            return value;
        }
        return 0;
    }

    @Override
    public int compareTo(PaymentMethod o) {
        Card card = (Card) o;
        if (! this.getCurrency().equals(card.getCurrency()))
            return -1;
        if (this.getType() != card.getType() )
            return 1;
        return 0;
    }
}
