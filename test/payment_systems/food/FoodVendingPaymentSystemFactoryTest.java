package payment_systems.food;

import Exceptions.NotSupportedTypeException;
import org.junit.Test;
import payment_systems.PaymentSystem;
import payment_systems.food.systems.SnackVendingPaymentSystem;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FoodVendingPaymentSystemFactoryTest {

    @Test
    public void buildSystem() {
        FoodVendingPaymentSystemFactory foodVendingPaymentSystemFactory = new FoodVendingPaymentSystemFactory();
        PaymentSystem paymentSystem = foodVendingPaymentSystemFactory.buildSystem(
                FoodVendingPaymentSystemFactory.SNACK_VENDING_MACHINE_PAYMENT_SYSTEM);

        assertEquals( true, paymentSystem instanceof SnackVendingPaymentSystem);
        assertNotEquals(null, paymentSystem);
        assertThrows(NotSupportedTypeException.class, () -> {
            foodVendingPaymentSystemFactory.buildSystem("");
        });
    }
}