package Exceptions;

public class UnknownKeyException extends RuntimeException{
    public UnknownKeyException(String message){
        System.out.println(message);
    }
}
