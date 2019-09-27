package displays;

import Exceptions.NotImplementedException;
import Exceptions.NotSupportedTypeException;
import displays.food.SnackVendingMachineDisplay;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DisplayFactoryTest {

    @org.junit.Test
    public void buildDisplay() throws Exception {

        Display display = DisplayFactory.buildDisplay(DisplayFactory.SNACK_VENDING_MACHINE_DISPLAY);
        assertEquals( true, display instanceof SnackVendingMachineDisplay);
        assertNotEquals(null, display);
        assertThrows(NotImplementedException.class, () -> {
            DisplayFactory.buildDisplay(DisplayFactory.BOOKS_VENDING_MACHINE_DISPLAY);
        });
        assertThrows(NotSupportedTypeException.class, () -> {
            DisplayFactory.buildDisplay("Drinks Display");
        });

    }
}