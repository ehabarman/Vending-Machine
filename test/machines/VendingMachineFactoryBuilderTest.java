package machines;

import Exceptions.NotImplementedException;
import Exceptions.NotSupportedTypeException;
import machines.food.FoodVendingMachineFactory;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VendingMachineFactoryBuilderTest {

    @org.junit.Test
    public void buildDisplay() throws Exception {

        VendingMachineFactory vendingMachineFactory = VendingMachineFactoryBuilder.buildFactory(
                VendingMachineFactoryBuilder.FOOD_VENDING_MACHINE_FACTORY);
        assertEquals( true, vendingMachineFactory instanceof FoodVendingMachineFactory);
        assertNotEquals(null, vendingMachineFactory);
        assertThrows(NotImplementedException.class, () -> {
            VendingMachineFactoryBuilder.buildFactory(VendingMachineFactoryBuilder.DRINKS_VENDING_MACHINE_FACTORY);
        });
        assertThrows(NotSupportedTypeException.class, () -> {
            VendingMachineFactoryBuilder.buildFactory("vegetables");
        });
    }
}