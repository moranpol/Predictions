package results.simulationEnded;

import java.util.Map;

public class DtoSimulationEndedDetails {
    private final Integer id;
    private final String startTime;
    private final Map<String, DtoSimulationEndedEntity> dtoEntityMap;

    public DtoSimulationEndedDetails(Integer id, String startTime, Map<String, DtoSimulationEndedEntity> dtoEntityMap) {
        this.id = id;
        this.startTime = startTime;
        this.dtoEntityMap = dtoEntityMap;
    }

    public Integer getId() {
        return id;
    }

    public String getStartTime() {
        return startTime;
    }

    public Map<String, DtoSimulationEndedEntity> getDtoEntityMap() {
        return dtoEntityMap;
    }
}
