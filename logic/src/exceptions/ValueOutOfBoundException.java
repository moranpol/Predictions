package exceptions;

import property.Range;

public class ValueOutOfBoundException extends RuntimeException{
    private final String message;

    public ValueOutOfBoundException(String message, float from, float to, float value) {
        this.message = message + " - value " + value + " must be between " + from + " - " + to;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
