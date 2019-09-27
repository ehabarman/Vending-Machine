package keypads.food;

import keypads.Keypad;

public class SnackVendingMachineKeypad implements Keypad {

    private final String[] KEYS = {"A", "B", "C", "D", "E",
            "1", "2", "3", "4", "5", "OK"};

    @Override
    public boolean hasKey(String key) {
        for (String value : KEYS)
            if (value.equals(key))
                return true;
        return false;
    }

    @Override
    public String[] listKeys() {
        return KEYS;
    }
}
