package rule.action;

import entity.EntityInstance;
import entity.EntityManager;
import grid.Grid;
import world.WorldDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Context {
    private EntityInstance MainEntityInstance;
    private EntityInstance secondEntityInstance = null;
    private final Map<String, EntityManager> entities;
    private final WorldDefinition worldDefinition;
    private final List<EntityInstance> newEntityInstances;
    private final Grid grid;
    private String secondEntityName = null;

    public Context(Map<String, EntityManager> entities, WorldDefinition worldDefinition, Grid grid) {
        this.entities = entities;
        this.worldDefinition = worldDefinition;
        this.grid = grid;
        newEntityInstances = new ArrayList<>();
    }

    public EntityInstance getMainEntityInstance() {
        return MainEntityInstance;
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

    public EntityInstance getSecondEntityInstance() {
        return secondEntityInstance;
    }

    public Grid getGrid() {
        return grid;
    }

    public String getSecondEntityName() {
        return secondEntityName;
    }

    public void setNewEntityInstances(EntityInstance newEntityInstances) {
        this.newEntityInstances.add(newEntityInstances);
    }

    public void setSecondEntityInstance(EntityInstance secondEntityInstance) {
        this.secondEntityInstance = secondEntityInstance;
    }

    public void setMainEntityInstance(EntityInstance mainEntityInstance) {
        this.MainEntityInstance = mainEntityInstance;
    }

    public void setSecondEntityName(String secondEntityName) {
        this.secondEntityName = secondEntityName;
    }
}
