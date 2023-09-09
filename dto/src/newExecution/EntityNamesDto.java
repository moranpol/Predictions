package newExecution;

import java.util.List;

public class EntityNamesDto {
    private final List<String> entityNames;

    public EntityNamesDto(List<String> entityNames) {
        this.entityNames = entityNames;
    }

    public List<String> getEntityNames() {
        return entityNames;
    }
}
