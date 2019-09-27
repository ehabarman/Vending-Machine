package displays.food;

import org.junit.Test;

import static org.junit.Assert.*;

public class SnackVendingMachineDisplayTest {

    @Test
    public void CurrentDisplay() {
        SnackVendingMachineDisplay snackVendingMachineDisplay = new SnackVendingMachineDisplay();
        assertEquals("", snackVendingMachineDisplay.getCurrentDisplay());
        snackVendingMachineDisplay.setCurrentDisplay("hi");
        assertEquals("hi", snackVendingMachineDisplay.getCurrentDisplay());
        snackVendingMachineDisplay.appendToCurrentDisplay(" world");
        assertEquals("hi world", snackVendingMachineDisplay.getCurrentDisplay());
        snackVendingMachineDisplay.setCurrentDisplay("");
        assertEquals("", snackVendingMachineDisplay.getCurrentDisplay());
    }

}