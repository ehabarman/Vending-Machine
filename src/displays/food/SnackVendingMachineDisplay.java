package displays.food;

import displays.Display;

public class SnackVendingMachineDisplay implements Display {

    private String display = "";

    @Override
    public String getCurrentDisplay() {
        return display;
    }

    @Override
    public void setCurrentDisplay(String newDisplay) {
        this.display = newDisplay;
    }

    @Override
    public void appendToCurrentDisplay(String toAppend) {
        this.display += toAppend;
    }

}
