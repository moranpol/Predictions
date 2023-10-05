package newExecution;

import newExecution.dtoEntities.DtoEntityNames;
import newExecution.dtoEnvironment.DtoEnvironment;

import java.util.List;

public class DtoNewExecution {
    private final DtoEntityNames dtoEntityNames;
    private final List<DtoEnvironment> dtoEnvironmentList;
    private final Integer maxPopulation;

    public DtoNewExecution(DtoEntityNames dtoEntityNames, List<DtoEnvironment> dtoEnvironmentList, Integer maxPopulation) {
        this.dtoEntityNames = dtoEntityNames;
        this.dtoEnvironmentList = dtoEnvironmentList;
        this.maxPopulation = maxPopulation;
    }

    public DtoEntityNames getDtoEntityNames() {
        return dtoEntityNames;
    }

    public List<DtoEnvironment> getDtoEnvironmentList() {
        return dtoEnvironmentList;
    }

    public Integer getMaxPopulation() {
        return maxPopulation;
    }
}
