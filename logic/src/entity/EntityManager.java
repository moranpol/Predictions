package entity;

import java.util.Map;

public class EntityManager {
    private final String name;
    private Map<Integer, EntityInstance> entityInstance;

    public EntityManager(String name, Map<Integer, EntityInstance> entityInstance) {
        this.name = name;
        this.entityInstance = entityInstance;
    }

    public String getName() {
        return name;
    }

    public Map<Integer, EntityInstance> getEntityInstance() {
        return entityInstance;
    }
}
