package results.simulations;

import java.util.List;

public class DtoSimulationInfo {
    private final Integer id;
    private final String simulationMode;
    private final String failedReason;
    private final Integer ticks;
    private final Integer seconds;
    private final List<DtoSimulationEntity> simulationEntities;

    public DtoSimulationInfo(Integer id, String simulationMode, String failedReason, Integer ticks, Integer seconds, List<DtoSimulationEntity> simulationEntities) {
        this.id = id;
        this.simulationMode = simulationMode;
        this.failedReason = failedReason;
        this.ticks = ticks;
        this.seconds = seconds;
        this.simulationEntities = simulationEntities;
    }

    public String getSimulationMode() {
        return simulationMode;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public Integer getId() {
        return id;
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
