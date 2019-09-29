package slots;

import Exceptions.SlotSupportLimitationException;
import payments.MoneyPaymentMethod;

public abstract class Slot {
    private final MoneyPaymentMethod[] acceptableList;
    protected String slotType;
    protected double insertedMoney;

    public Slot(MoneyPaymentMethod[] acceptableList) {
        this.acceptableList = acceptableList;
    }

    public boolean doesMachineAccept(MoneyPaymentMethod method) throws SlotSupportLimitationException {
        for (MoneyPaymentMethod acceptable : acceptableList)
        {

            if (acceptable.compareTo(method) == 0)
                return true;
        }
        throw new SlotSupportLimitationException(slotType+ "slot doesn't support this "+slotType);
    }
    public double getInsertedMoney(){
        return insertedMoney;
    }

}
