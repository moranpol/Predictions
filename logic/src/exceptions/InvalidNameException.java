package exceptions;

public class InvalidNameException extends Exception{

    public InvalidNameException(String message) {
        super("Invalid name - " + message);
    }

}
