package exceptions;

public class InvalidNameException extends RuntimeException{

    public InvalidNameException(String message) {
        super("Invalid name - " + message);
    }
}
