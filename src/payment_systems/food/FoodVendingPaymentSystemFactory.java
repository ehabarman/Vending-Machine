package payment_systems.food;

import Exceptions.NotSupportedTypeException;
import payment_systems.PaymentSystem;
import payment_systems.PaymentSystemFactory;
import payment_systems.food.systems.SnackVendingPaymentSystem;

public class FoodVendingPaymentSystemFactory implements PaymentSystemFactory {

    public static final String SNACK_VENDING_MACHINE_PAYMENT_SYSTEM = "SNACK_VENDING_MACHINE_PAYMENT_SYSTEM";

    @Override
    public PaymentSystem buildSystem(String systemType) throws NotSupportedTypeException {

        switch (systemType){
            case SNACK_VENDING_MACHINE_PAYMENT_SYSTEM:
                return new SnackVendingPaymentSystem();
            default:
                throw new NotSupportedTypeException(systemType + " is not a supported payment system type");
        }

    }
}
