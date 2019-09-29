package slots;

import payments.MoneyPaymentMethod;
import payments.money.Coin;

import java.util.ArrayList;

public class CoinSlot extends Slot {
    private ArrayList<Coin> insertedCoins = new ArrayList<Coin>();

    public CoinSlot(MoneyPaymentMethod[] acceptableList) {
        super(acceptableList);
        super.slotType="Coin";
    }

    public double insertCoin( Coin coin){
        doesMachineAccept(coin);
        insertedCoins.add(coin);
        insertedMoney+= coin.getValue();
        return coin.getValue();

    }

    public ArrayList<Coin> refund(){
        ArrayList temp = insertedCoins;
        insertedCoins = new ArrayList<Coin>();
        insertedMoney = 0;
        return temp;
    }
}
