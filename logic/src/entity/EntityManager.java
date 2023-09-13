package entity;

import java.io.Serializable;
import java.util.List;

public class EntityManager implements Serializable {
    private final String name;
    private final List<EntityInstance> entityInstance;

    public EntityManager(String name, List<EntityInstance> entityInstance) {
        this.name = name;
        this.entityInstance = entityInstance;
    }

    public String getName() {
        return name;
    }

    public List<EntityInstance> getEntityInstance() {
        return entityInstance;
    }

    public void addInstance(EntityInstance entityInstance){
        this.entityInstance.add(entityInstance);
    }
}
