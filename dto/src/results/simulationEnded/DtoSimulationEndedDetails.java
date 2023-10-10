package results.simulationEnded;

import java.util.Map;

public class DtoSimulationEndedDetails {
    private final Integer simulationId;
    private final Integer requestId;
    private final String startTime;
    private final Map<String, DtoSimulationEndedEntity> dtoEntityMap;

    public DtoSimulationEndedDetails(Integer simulationId, Integer requestId, String startTime, Map<String, DtoSimulationEndedEntity> dtoEntityMap) {
        this.simulationId = simulationId;
        this.requestId = requestId;
        this.startTime = startTime;
        this.dtoEntityMap = dtoEntityMap;
    }

    public Integer getSimulationId() {
        return simulationId;
    }

    public String getStartTime() {
        return startTime;
    }

    public Map<String, DtoSimulationEndedEntity> getDtoEntityMap() {
        return dtoEntityMap;
    }

    public Integer getRequestId() {
        return requestId;
    }
}
