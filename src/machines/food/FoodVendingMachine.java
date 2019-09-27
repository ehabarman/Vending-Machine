package machines.food;

import Exceptions.OutOfStockException;
import machines.VendingMachine;

import java.util.Stack;

public abstract class FoodVendingMachine extends VendingMachine {

    protected Stack[][] inventory ;
    protected double[][] prices;

    public abstract void pressKey(String key) throws OutOfStockException;

    public abstract void setPrice(int row, int col, int newPrice);

    public abstract void fillInventorySlot(int row, int col, String item, int amount);

}
