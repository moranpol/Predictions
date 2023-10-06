package results.simulations;

public class DtoSimulationInfo {
    private final Integer simulationId;
    private final Integer requestId;
    private final String simulationMode;

    public DtoSimulationInfo(Integer simulationId, Integer requestId, String simulationMode) {
        this.simulationId = simulationId;
        this.requestId = requestId;
        this.simulationMode = simulationMode;
    }

    public String getSimulationMode() {
        return simulationMode;
    }

    public Integer getSimulationId() {
        return simulationId;
    }

    public Integer getRequestId() {
        return requestId;
    }
}
