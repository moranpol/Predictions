package exceptions;

public class SimulationFailedException extends RuntimeException{
    public SimulationFailedException(String message, Integer simulationId) {
        super("simulation id " + simulationId + " failed.\n    " + message + "\n");
    }
}
