package Exceptions;

public class NeedChoiceException extends RuntimeException {
    public NeedChoiceException(String message){
        System.out.println(message);
    }
}
