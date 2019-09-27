package keypads;

import Exceptions.NotImplementedException;
import Exceptions.NotSupportedTypeException;
import keypads.food.SnackVendingMachineKeypad;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class KeypadFactoryTest {

    @org.junit.Test
    public void buildDisplay() throws NotSupportedTypeException {

        Keypad display = KeypadFactory.buildKeypad(KeypadFactory.SNACK_VENDING_MACHINE_KEYPAD);
        assertEquals(true, display instanceof SnackVendingMachineKeypad);
        assertNotEquals(null, display);
        assertThrows(NotSupportedTypeException.class, () -> {
            KeypadFactory.buildKeypad("drinks");
        });
        assertThrows(NotImplementedException.class, () -> {
            KeypadFactory.buildKeypad(KeypadFactory.BOOKS_VENDING_MACHINE_KEYPAD);
        });

    }
}