package world;

import entity.EntityInstance;
import entity.EntityManager;
import environment.EnvironmentInstance;
import grid.Grid;
import property.PropertyInstance;
import rule.Rule;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldInstance implements Serializable {
    private final Map<String, EntityManager> entities;
    private final EnvironmentInstance environmentVariables;
    private final List<Rule> rules;
    private final Grid grid;
    private final Map<String, EntityCountGraph> entityCountGraphMap;
    private final Integer sleep;

    public WorldInstance(Map<String, EntityManager> entities, EnvironmentInstance environmentVariables, List<Rule> rules, Grid grid, Integer sleep) {
        this.entities = entities;
        this.environmentVariables = environmentVariables;
        this.rules = rules;
        this.grid = grid;
        this.sleep = sleep;
        entityCountGraphMap = new HashMap<>();
        updateEntityCountGraphMap();
    }

    public EnvironmentInstance getEnvironmentVariables() {
        return environmentVariables;
    }

    public Integer getSleep() {
        return sleep;
    }

    private void updateEntityCountGraphMap(){
        for(EntityManager entityManager : entities.values()){
            if(!entityCountGraphMap.containsKey(entityManager.getName())) {
                entityCountGraphMap.put(entityManager.getName(), new EntityCountGraph());
            }
            entityCountGraphMap.get(entityManager.getName()).setEntityQuantity(entityManager.getEntityInstance().size());
        }
    }

    public Map<String, EntityManager> getEntities() {
        return entities;
    }

    public Grid getGrid() {
        return grid;
    }

    public Map<String, EntityCountGraph> getEntityCountGraphMap() {
        return entityCountGraphMap;
    }

    public void runSimulationTick(Integer currentTick, WorldDefinition worldDefinition) {
        for (EntityManager entityManager : entities.values()){
            for (EntityInstance entityInstance : entityManager.getEntityInstance()){
                grid.moveInstance(entityInstance);
            }
        }

        for (Rule rule : rules) {
            rule.activeRule(entities, currentTick, worldDefinition, grid, environmentVariables);
        }

        for (EntityManager entityManager : entities.values()){
            for (EntityInstance entityInstance : entityManager.getEntityInstance()){
                for(PropertyInstance propertyInstance : entityInstance.getProperties().values()){
                    propertyInstance.setValueCounterByTicks();
                }
            }
        }

        if(currentTick % 5000 == 0){
            updateEntityCountGraphMap();
        }
    }
}
