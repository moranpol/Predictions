package world;

import entity.EntityInstance;
import entity.EntityManager;
import environment.EnvironmentInstance;
import grid.Grid;
import rule.Rule;
import termination.Termination;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WorldInstance implements Serializable {
    private final Map<String, EntityManager> entities;
    private final EnvironmentInstance environmentVariables;
    private final List<Rule> rules;
    private final Termination termination;
    private final Grid grid;

    public WorldInstance(Map<String, EntityManager> entities, EnvironmentInstance environmentVariables, List<Rule> rules, Termination termination, Grid grid) {
        this.entities = entities;
        this.environmentVariables = environmentVariables;
        this.rules = rules;
        this.termination = termination;
        this.grid = grid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorldInstance that = (WorldInstance) o;
        return Objects.equals(entities, that.entities) && Objects.equals(environmentVariables, that.environmentVariables) && Objects.equals(rules, that.rules) && Objects.equals(termination, that.termination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entities, environmentVariables, rules, termination);
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

    public void runSimulationTick(Integer currentTick, WorldDefinition worldDefinition) {
        for (EntityManager entityManager : entities.values()){
            for (EntityInstance entityInstance : entityManager.getEntityInstance()){
                grid.moveInstance(entityInstance);
            }
        }

        for (Rule rule : rules) {
            rule.activeRule(entities, currentTick, worldDefinition, grid);
        }
    }
}
