package results.simulationRunningDetails;

import java.util.List;

public class DtoSimulationRunningDetails {
    private final Integer simulationId;
    private final Integer requestId;
    private final String simulationMode;
    private final String startTime;
    private final Integer ticks;
    private final Integer seconds;
    private final List<DtoSimulationEntity> simulationEntities;

    public DtoSimulationRunningDetails(Integer simulationId, Integer requestId, String simulationMode, String startTime, Integer ticks, Integer seconds, List<DtoSimulationEntity> simulationEntities) {
        this.simulationId = simulationId;
        this.requestId = requestId;
        this.simulationMode = simulationMode;
        this.startTime = startTime;
        this.ticks = ticks;
        this.seconds = seconds;
        this.simulationEntities = simulationEntities;
    }

    public Integer getSimulationId() {
        return simulationId;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public String getSimulationMode() {
        return simulationMode;
    }

    public String getStartTime() {
        return startTime;
    }

    public Integer getTicks() {
        return ticks;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public List<DtoSimulationEntity> getSimulationEntities() {
        return simulationEntities;
    }
}
