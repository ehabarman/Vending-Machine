package Exceptions;

public class WrongValueException extends RuntimeException {
    public WrongValueException(String message){
        System.out.println(message);
    }
}
