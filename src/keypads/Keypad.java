package keypads;

public interface Keypad {

    public boolean hasKey(String key);

    public String[] listKeys();
}
