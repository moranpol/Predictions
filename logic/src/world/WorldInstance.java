package world;

import entity.EntityInstance;
import entity.EntityManager;
import environment.EnvironmentInstance;
import grid.Grid;
import property.PropertyInstance;
import rule.Rule;
import termination.Termination;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WorldInstance implements Serializable {
    private final Map<String, EntityManager> entities;
    private final EnvironmentInstance environmentVariables;
    private final List<Rule> rules;
    private final Termination termination;
    private final Grid grid;
    private final Map<String, EntityCountGraph> entityCountGraphMap;

    public WorldInstance(Map<String, EntityManager> entities, EnvironmentInstance environmentVariables, List<Rule> rules, Termination termination, Grid grid) {
        this.entities = entities;
        this.environmentVariables = environmentVariables;
        this.rules = rules;
        this.termination = termination;
        this.grid = grid;
        entityCountGraphMap = new HashMap<>();
        updateEntityCountGraphMap();
    }

    public void updateEntityCountGraphMap(){
        for(EntityManager entityManager : entities.values()){
            if(!entityCountGraphMap.containsKey(entityManager.getName())) {
                entityCountGraphMap.put(entityManager.getName(), new EntityCountGraph());
            }
            entityCountGraphMap.get(entityManager.getName()).setEntityQuantity(entityManager.getEntityInstance().size());
        }
    }

    public Termination getTermination() {
        return termination;
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
