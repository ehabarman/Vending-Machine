package machines.food;

import Exceptions.NotImplementedException;
import Exceptions.NotSupportedTypeException;
import machines.food.snack.SnackVendingMachine;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FoodVendingMachineFactoryTest {

    @Test
    public void buildMachine() {
        FoodVendingMachineFactory foodVendingMachineFactory = new FoodVendingMachineFactory();
        FoodVendingMachine foodVendingMachine = foodVendingMachineFactory.buildMachine(
                foodVendingMachineFactory.SNACK_VENDING_MACHINE);

        assertTrue(foodVendingMachine instanceof SnackVendingMachine);
        assertNotEquals(null, foodVendingMachineFactory);
        assertThrows(NotImplementedException.class, () -> {
            foodVendingMachineFactory.buildMachine(foodVendingMachineFactory.SANDWICH_VENDING_MACHINE);
        });
        assertThrows(NotSupportedTypeException.class, () -> {
            foodVendingMachineFactory.buildMachine("vegetables");
        });
    }
}