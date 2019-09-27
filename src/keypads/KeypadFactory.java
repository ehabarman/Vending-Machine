package keypads;

import Exceptions.NotImplementedException;
import Exceptions.NotSupportedTypeException;
import keypads.food.SnackVendingMachineKeypad;

public class KeypadFactory {

    public static final String SNACK_VENDING_MACHINE_KEYPAD = "snack_vending_machine_keypad";
    public static final String BOOKS_VENDING_MACHINE_KEYPAD = "books_vending_machine_keypad";


    public static Keypad buildKeypad(String keypadType) throws NotSupportedTypeException {

        switch (keypadType) {
            case SNACK_VENDING_MACHINE_KEYPAD:
                return new SnackVendingMachineKeypad();
            case BOOKS_VENDING_MACHINE_KEYPAD:
                throw new NotImplementedException(" books vending machine keypad is not implemented");
            default:
                throw new NotSupportedTypeException("'" + keypadType + "'is not a supported Keypad");
        }
    }
}
