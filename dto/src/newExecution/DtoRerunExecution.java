package newExecution;

import newExecution.dtoEntities.DtoEntitiesPopulation;
import newExecution.dtoEnvironment.DtoEnvironmentInitialize;

import java.util.List;

public class DtoRerunExecution {
    private final List<DtoEntitiesPopulation> dtoEntitiesPopulationList;
    private final List<DtoEnvironmentInitialize> dtoEnvironmentInitializeList;

    public DtoRerunExecution(List<DtoEntitiesPopulation> dtoEntitiesPopulationList, List<DtoEnvironmentInitialize> dtoEnvironmentInitializeList) {
        this.dtoEntitiesPopulationList = dtoEntitiesPopulationList;
        this.dtoEnvironmentInitializeList = dtoEnvironmentInitializeList;
    }

    public List<DtoEntitiesPopulation> getDtoEntitiesPopulationList() {
        return dtoEntitiesPopulationList;
    }

    public List<DtoEnvironmentInitialize> getDtoEnvironmentInitializeList() {
        return dtoEnvironmentInitializeList;
    }
}
