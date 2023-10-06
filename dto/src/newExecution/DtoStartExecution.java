package newExecution;

import newExecution.dtoEntities.DtoEntitiesPopulation;
import newExecution.dtoEnvironment.DtoEnvironmentInitialize;

import java.util.List;

public class DtoStartExecution {
    private final List<DtoEntitiesPopulation> dtoEntitiesPopulationList;
    private final List<DtoEnvironmentInitialize> dtoEnvironmentInitializeList;
    private final Integer simulationId;
    private final Integer requestId;

    public DtoStartExecution(List<DtoEntitiesPopulation> dtoEntitiesPopulationList, List<DtoEnvironmentInitialize> dtoEnvironmentInitializeList, Integer simulationId, Integer requestId) {
        this.dtoEntitiesPopulationList = dtoEntitiesPopulationList;
        this.dtoEnvironmentInitializeList = dtoEnvironmentInitializeList;
        this.simulationId = simulationId;
        this.requestId = requestId;
    }

    public List<DtoEntitiesPopulation> getDtoEntitiesPopulationList() {
        return dtoEntitiesPopulationList;
    }

    public List<DtoEnvironmentInitialize> getDtoEnvironmentInitializeList() {
        return dtoEnvironmentInitializeList;
    }

    public Integer getSimulationId() {
        return simulationId;
    }

    public Integer getRequestId() {
        return requestId;
    }
}
