package machines;

import displays.Display;
import keypads.Keypad;
import payment_systems.PaymentSystem;

public abstract class VendingMachine {
    protected states machineState = states.WAITING_ORDER;
    protected static enum states{
        WAITING_ORDER,
        WAITING_MONEY,
        PENDING,
    }
    protected Keypad keypad;
    protected Display display;

}
