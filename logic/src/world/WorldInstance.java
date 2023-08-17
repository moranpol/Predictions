package world;

import entity.EntityManager;
import environment.EnvironmentInstance;
import rule.Rule;
import termination.Termination;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WorldInstance {
    private final Map<String, EntityManager> entities;
    private final EnvironmentInstance environmentVariables;
    private final List<Rule> rules;
    private final Termination termination;

    public WorldInstance(Map<String, EntityManager> entities, EnvironmentInstance environmentVariables, List<Rule> rules, Termination termination) {
        this.entities = entities;
        this.environmentVariables = environmentVariables;
        this.rules = rules;
        this.termination = termination;
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

    public EnvironmentInstance getEnvironmentVariables() {
        return environmentVariables;
    }

    public void runSimulationTick(Integer currentTick) {
        for (Rule rule : rules) {
            rule.activeRule(entities, currentTick);
        }
    }
}
