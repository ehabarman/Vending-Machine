package payment_systems;


import Exceptions.NotImplementedException;
import Exceptions.NotSupportedTypeException;
import payment_systems.food.FoodVendingPaymentSystemFactory;

public class PaymentSystemFactoryBuilder {

    public static final String FOOD_VENDING_PAYMENT_SYSTEM_FACTORY = "FOOD_VENDING_PAYMENT_SYSTEM_FACTORY";
    public static final String BOOKS_VENDING_PAYMENT_SYSTEM_FACTORY = "BOOKS_VENDING_PAYMENT_SYSTEM_FACTORY";

    public static PaymentSystemFactory buildPaymentSystemFactory(String paymentSystemFactoryType)
            throws NotSupportedTypeException {

        switch (paymentSystemFactoryType) {
            case FOOD_VENDING_PAYMENT_SYSTEM_FACTORY:
                return new FoodVendingPaymentSystemFactory();
            case BOOKS_VENDING_PAYMENT_SYSTEM_FACTORY:
                throw new NotImplementedException("books vending machine payment system factory is not implemented");
            default:
                throw new NotSupportedTypeException("'" + paymentSystemFactoryType +
                        "' is not a supported payment system factory");
        }
    }
}
