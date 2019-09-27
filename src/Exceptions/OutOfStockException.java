package Exceptions;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String message){
        System.out.println(message);
    }
}
