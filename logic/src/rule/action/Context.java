package rule.action;

import entity.EntityInstance;
import entity.EntityManager;
import world.WorldDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Context {
    private EntityInstance entityInstance;
    private final Map<String, EntityManager> entities;
    private final WorldDefinition worldDefinition;
    private List<EntityInstance> newEntityInstances;

    public Context(Map<String, EntityManager> entities, WorldDefinition worldDefinition) {
        this.entities = entities;
        this.worldDefinition = worldDefinition;
        newEntityInstances = new ArrayList<>();
    }

    public EntityInstance getEntityInstance() {
        return entityInstance;
    }

    public Map<String, EntityManager> getEntities() {
        return entities;
    }

    public WorldDefinition getWorldDefinition() {
        return worldDefinition;
    }

    public List<EntityInstance> getNewEntityInstances() {
        return newEntityInstances;
    }

    public void setNewEntityInstances(EntityInstance newEntityInstances) {
        this.newEntityInstances.add(newEntityInstances);
    }

    public void setEntityInstance(EntityInstance entityInstance) {
        this.entityInstance = entityInstance;
    }
}
