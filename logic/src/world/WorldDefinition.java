package world;

import entity.EntityDefinition;
import environment.EnvironmentDefinition;
import grid.Grid;
import rule.Rule;
import termination.Termination;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldDefinition implements Serializable {
    private final EnvironmentDefinition environmentVariables;
    private final Map<String, EntityDefinition> entities;
    private final List<Rule> rules;
    private final Termination termination;
    private final Grid grid;
    private final Integer numOfThreads;

    public WorldDefinition(EnvironmentDefinition environmentVariables, Map<String, EntityDefinition> entities, List<Rule> rules, Termination termination, Grid grid, Integer numOfThreads) {
        this.environmentVariables = environmentVariables;
        this.entities = entities;
        this.rules = rules;
        this.termination = termination;
        this.grid = grid;
        this.numOfThreads = numOfThreads;
    }

    public WorldDefinition(WorldDefinition otherWorldDefinition){
        this.environmentVariables = new EnvironmentDefinition(otherWorldDefinition.environmentVariables);
        this.entities = new HashMap<>();
        for(EntityDefinition entity : otherWorldDefinition.getEntities().values()){
            this.entities.put(entity.getName(), new EntityDefinition(entity));
        }
        this.rules = otherWorldDefinition.getRules();
        this.termination = otherWorldDefinition.getTermination();
        this.grid = otherWorldDefinition.getGrid();
        this.numOfThreads = otherWorldDefinition.getNumOfThreads();
    }

    public EnvironmentDefinition getEnvironmentVariables() {
        return environmentVariables;
    }

    public Map<String, EntityDefinition> getEntities() {
        return entities;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public Termination getTermination() {
        return termination;
    }

    public Grid getGrid() {
        return grid;
    }

    public Integer getNumOfThreads() {
        return numOfThreads;
    }
}
