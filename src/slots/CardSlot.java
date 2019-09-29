package slots;

import Exceptions.CardSlotInUseException;
import payments.MoneyPaymentMethod;
import payments.money.Card;

public class CardSlot extends Slot {

    private Card insertedCard = null;

    public CardSlot(MoneyPaymentMethod[] acceptableList) {
        super(acceptableList);
        super.slotType = "Card";
    }

    public double insertCard(Card card) throws CardSlotInUseException {
        if (insertedCard == null) {
            doesMachineAccept(card);
            insertedCard = card;
            insertedMoney = card.getBalance();
            return card.getBalance();
        } else {
            throw new CardSlotInUseException("Cardslot can take a single card at a time");
        }
    }

    public Card removeCard(){
        Card card = this.insertedCard;
        insertedCard = null;
        insertedMoney = 0;
        return card;
    }


}
