package payment_systems;

import Exceptions.NotImplementedException;
import Exceptions.NotSupportedTypeException;
import org.junit.Test;
import payment_systems.food.FoodVendingPaymentSystemFactory;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaymentSystemFactoryBuilderTest {

    @Test
    public void buildPaymentSystemFactory() {
        PaymentSystemFactory vendingPaymentSystemFactory = PaymentSystemFactoryBuilder.buildPaymentSystemFactory(
                PaymentSystemFactoryBuilder.FOOD_VENDING_PAYMENT_SYSTEM_FACTORY);

        assertEquals(true, vendingPaymentSystemFactory instanceof FoodVendingPaymentSystemFactory);
        assertNotEquals(null, vendingPaymentSystemFactory);
        assertThrows(NotSupportedTypeException.class, () -> {
            PaymentSystemFactoryBuilder.buildPaymentSystemFactory("drinks");
        });
        assertThrows(NotImplementedException.class, () -> {
            PaymentSystemFactoryBuilder.buildPaymentSystemFactory(
                    PaymentSystemFactoryBuilder.BOOKS_VENDING_PAYMENT_SYSTEM_FACTORY);
        });
    }
}