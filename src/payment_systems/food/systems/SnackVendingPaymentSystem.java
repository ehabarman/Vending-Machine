package payment_systems.food.systems;

import enums.CardTypes;
import enums.Currency;
import payment_systems.PaymentSystem;
import payments.PaymentMethod;
import payments.money.Card;
import payments.money.Coin;
import payments.money.Note;
import slots.CardSlot;
import slots.CoinSlot;
import slots.NoteSlot;

import java.util.ArrayList;


public class SnackVendingPaymentSystem implements PaymentSystem {

    private final CoinSlot coinSlot;
    private final NoteSlot noteSlot;
    private final CardSlot cardSlot;

    private double totalInsertedMoney = 0;
    private double requiredMoney = 0;

    public SnackVendingPaymentSystem(){
        Coin[] acceptableCoins = {new Coin(Currency.USD, 0.1), new Coin(Currency.USD, 0.2),
                new Coin(Currency.USD, 0.5), new Coin(Currency.USD, 1.0)};
        Note[] acceptableNotes = {new Note(Currency.USD, 20), new Note(Currency.USD, 50)};
        Card[] acceptableCards = {new Card(Currency.USD, 0, CardTypes.VISA),
                new Card(Currency.USD, 0, CardTypes.CREDITCARD),
                new Card(Currency.USD, 0, CardTypes.SHOPPINGCARD)};
        coinSlot = new CoinSlot(acceptableCoins);
        noteSlot = new NoteSlot(acceptableNotes);
        cardSlot = new CardSlot(acceptableCards);
    }

    public double insertCoin(Coin coin) throws Exception {
        double value = coinSlot.insertCoin(coin);
        totalInsertedMoney+= value;
        return totalInsertedMoney;
    }

    public ArrayList<Coin> refundCoins(){
        totalInsertedMoney-= coinSlot.getInsertedMoney();
        return coinSlot.refund();
    }

    public double insertNote(Note note) throws Exception {
        double value = noteSlot.insertNote(note);
        totalInsertedMoney+= value;
        return totalInsertedMoney;
    }

    public ArrayList<Note> refundNotes(){
        totalInsertedMoney-= noteSlot.getInsertedMoney();
        return noteSlot.refund();
    }

    public double insertCard(Card card) throws Exception {
        double value = cardSlot.insertCard(card);
        totalInsertedMoney+= value;
        return totalInsertedMoney;
    }

    public Card refundCard(){
        totalInsertedMoney-= cardSlot.getInsertedMoney();
        return cardSlot.removeCard();
    }

    public void setRequiredMoney( double requiredMoney ){
        this.requiredMoney = requiredMoney;
    }

    public double getRequiredMoney(){
        return this.requiredMoney;
    }

    public double getTotalInsertedMoney(){
        return totalInsertedMoney;
    }

    public ArrayList<PaymentMethod> refundAll(){
        ArrayList<PaymentMethod> all = new ArrayList();
        all.addAll(refundCoins());
        all.addAll(refundNotes());
        Card card = refundCard();
        if (card != null)
            all.add(card);
        return all;
    }
}
