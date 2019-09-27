package Exceptions;

public class FullInventorySlotException extends RuntimeException {
    public FullInventorySlotException(String message){
        System.out.println(message);
    }
}
