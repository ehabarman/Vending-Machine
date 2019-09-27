package machines;

import Exceptions.NotImplementedException;
import Exceptions.NotSupportedTypeException;
import machines.food.FoodVendingMachineFactory;

public class VendingMachineFactoryBuilder {
    public static final String FOOD_VENDING_MACHINE_FACTORY = "food_vending_machine_factory";
    public static final String DRINKS_VENDING_MACHINE_FACTORY = "drinks_vending_machine_factory";
    public static final String BOOKS_VENDING_MACHINE_FACTORY = "books_vending_machine_factory";


    public static VendingMachineFactory buildFactory(String factoryType) throws NotSupportedTypeException {

        switch (factoryType) {
            case FOOD_VENDING_MACHINE_FACTORY:
                return new FoodVendingMachineFactory();
            case DRINKS_VENDING_MACHINE_FACTORY:
                throw new NotImplementedException("drinks vending machine factory is not implemented");
            case BOOKS_VENDING_MACHINE_FACTORY:
                throw new NotImplementedException("books vending machine factory is not implemented");
            default:
                throw new NotSupportedTypeException("'"+factoryType+ "' is not a supported machine factory");
        }
    }
}
