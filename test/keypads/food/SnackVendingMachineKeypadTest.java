package keypads.food;

import org.junit.Test;

import static org.junit.Assert.*;

public class SnackVendingMachineKeypadTest {

    @Test
    public void hasKey() {
        SnackVendingMachineKeypad snackVendingMachineKeypad = new SnackVendingMachineKeypad();
        assertEquals(true, snackVendingMachineKeypad.hasKey("A"));
        assertEquals(true, snackVendingMachineKeypad.hasKey("B"));
        assertEquals(true, snackVendingMachineKeypad.hasKey("C"));
        assertEquals(true, snackVendingMachineKeypad.hasKey("1"));
        assertEquals(true, snackVendingMachineKeypad.hasKey("2"));
        assertEquals(true, snackVendingMachineKeypad.hasKey("OK"));
        assertEquals(false, snackVendingMachineKeypad.hasKey("OKK"));
        assertEquals(false, snackVendingMachineKeypad.hasKey("9"));
        assertEquals(false, snackVendingMachineKeypad.hasKey("7"));
        assertEquals(false, snackVendingMachineKeypad.hasKey("6"));
        assertEquals(false, snackVendingMachineKeypad.hasKey("Del"));

    }

    @Test
    public void listKeys() {
        SnackVendingMachineKeypad snackVendingMachineKeypad = new SnackVendingMachineKeypad();
        String[] keys = snackVendingMachineKeypad.listKeys();
        assertNotEquals(null, keys);
        assertNotEquals(0, keys.length);
        assertEquals(true, keys[keys.length-1] instanceof String);
    }
}