package Exceptions;

public class NotSupportedTypeException extends RuntimeException {

    public NotSupportedTypeException(String message){
        System.out.println(message);
    }

}
