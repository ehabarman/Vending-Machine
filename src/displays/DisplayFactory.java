package displays;

import Exceptions.NotImplementedException;
import Exceptions.NotSupportedTypeException;
import displays.food.SnackVendingMachineDisplay;

public class DisplayFactory {

    public static final String SNACK_VENDING_MACHINE_DISPLAY = "SNACK VENDING MACHINE DISPLAY";
    public static final String BOOKS_VENDING_MACHINE_DISPLAY = "BOOKS VENDING MACHINE DISPLAY";

    public static Display buildDisplay(String displayType) throws NotSupportedTypeException {

        switch (displayType){
            case SNACK_VENDING_MACHINE_DISPLAY:
                return new SnackVendingMachineDisplay();
            case BOOKS_VENDING_MACHINE_DISPLAY:
                throw new NotImplementedException(" books vending machine is not implemented");
            default:
                throw new NotSupportedTypeException("'" + displayType + "' is not a supported display");

        }

    }
}
