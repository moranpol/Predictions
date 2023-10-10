package results.simulationFailed;

public class DtoSimulationFailedDetails {
    private final Integer simulationId;
    private final Integer requestId;
    private final String startTime;
    private final String failedReason;

    public DtoSimulationFailedDetails(Integer simulationId, Integer requestId, String startTime, String failedReason) {
        this.simulationId = simulationId;
        this.requestId = requestId;
        this.startTime = startTime;
        this.failedReason = failedReason;
    }

    public Integer getSimulationId() {
        return simulationId;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getFailedReason() {
        return failedReason;
    }
}
