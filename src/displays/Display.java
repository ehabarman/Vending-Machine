package displays;

public interface Display {

    public String getCurrentDisplay();
    public void setCurrentDisplay(String newDisplay);
    public void appendToCurrentDisplay(String toAppend);
}
