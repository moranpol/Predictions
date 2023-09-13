package newExecution.dtoEntities;

import java.util.List;

public class DtoEntityNames {
    private final List<String> entityNames;

    public DtoEntityNames(List<String> entityNames) {
        this.entityNames = entityNames;
    }

    public List<String> getEntityNames() {
        return entityNames;
    }
}
