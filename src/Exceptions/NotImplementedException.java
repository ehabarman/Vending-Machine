package Exceptions;

public class NotImplementedException extends RuntimeException {
    public NotImplementedException(String message){
        System.out.println(message);
    }
}
