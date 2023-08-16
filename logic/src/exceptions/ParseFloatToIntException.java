package exceptions;

public class ParseFloatToIntException extends RuntimeException{
    public ParseFloatToIntException(String message) {
        super("Invalid parse - cannot parse float type to int type.\n" + message);
    }
}
