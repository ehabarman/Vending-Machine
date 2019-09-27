package Exceptions;

public class CardSlotInUseException extends RuntimeException {
    public CardSlotInUseException(String message){
        System.out.println(message);
    }
}
