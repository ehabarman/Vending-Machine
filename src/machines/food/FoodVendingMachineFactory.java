package machines.food;

import Exceptions.NotImplementedException;
import Exceptions.NotSupportedTypeException;
import displays.DisplayFactory;
import displays.food.SnackVendingMachineDisplay;
import keypads.KeypadFactory;
import keypads.food.SnackVendingMachineKeypad;
import machines.VendingMachineFactory;
import machines.food.snack.SnackVendingMachine;
import payment_systems.PaymentSystemFactoryBuilder;
import payment_systems.food.FoodVendingPaymentSystemFactory;
import payment_systems.food.systems.SnackVendingPaymentSystem;

public class FoodVendingMachineFactory implements VendingMachineFactory {

    public static final String SNACK_VENDING_MACHINE = "snack_vending_machine";
    public static final String SANDWICH_VENDING_MACHINE = "sandwich_vending_machine";

    @Override
    public FoodVendingMachine buildMachine(String machineType) throws NotSupportedTypeException {

        switch (machineType) {
            case SNACK_VENDING_MACHINE:
                return assembleSnackVendingMachine();
            case SANDWICH_VENDING_MACHINE:
                throw new NotImplementedException("sandwich vending machine is not implemented");
            default:
                throw new NotSupportedTypeException("'" + machineType + "' is not a supported vending machine");
        }

    }

    private SnackVendingMachine assembleSnackVendingMachine() throws NotSupportedTypeException {
        SnackVendingMachineKeypad snackVendingMachineKeypad = (SnackVendingMachineKeypad) KeypadFactory.buildKeypad(
                KeypadFactory.SNACK_VENDING_MACHINE_KEYPAD);

        FoodVendingPaymentSystemFactory foodVendingPaymentSystemFactory =
                (FoodVendingPaymentSystemFactory) PaymentSystemFactoryBuilder.buildPaymentSystemFactory(
                        PaymentSystemFactoryBuilder.FOOD_VENDING_PAYMENT_SYSTEM_FACTORY);

        SnackVendingPaymentSystem snackVendingPaymentSystem = (SnackVendingPaymentSystem) foodVendingPaymentSystemFactory.
                buildSystem(FoodVendingPaymentSystemFactory.SNACK_VENDING_MACHINE_PAYMENT_SYSTEM);

        SnackVendingMachineDisplay snackVendingMachineDisplay =
                (SnackVendingMachineDisplay) DisplayFactory.buildDisplay(DisplayFactory.SNACK_VENDING_MACHINE_DISPLAY);

        return new SnackVendingMachine(snackVendingMachineKeypad, snackVendingMachineDisplay,snackVendingPaymentSystem );
    }
}
