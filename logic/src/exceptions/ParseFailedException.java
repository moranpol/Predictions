package exceptions;

import enums.PropertyType;

public class ParseFailedException extends RuntimeException{
    public ParseFailedException(String message, PropertyType type) {
        super(message + " - invalid input, cannot parse to type " + type);
    }
}
