package entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class EntityManager implements Serializable {
    private final String name;
    private final List<EntityInstance> entityInstance;

    public EntityManager(String name, List<EntityInstance> entityInstance) {
        this.name = name;
        this.entityInstance = entityInstance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityManager that = (EntityManager) o;
        return Objects.equals(name, that.name) && Objects.equals(entityInstance, that.entityInstance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, entityInstance);
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
