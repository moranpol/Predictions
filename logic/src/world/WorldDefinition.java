package world;

import entity.EntityDefinition;
import environment.EnvironmentDefinition;
import rule.Rule;
import termination.Termination;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WorldDefinition implements Serializable {
    private final EnvironmentDefinition environmentVariables;
    private final Map<String, EntityDefinition> entities;
    private final List<Rule> rules;
    private final Termination termination;

    public WorldDefinition(EnvironmentDefinition environmentVariables, Map<String, EntityDefinition> entities, List<Rule> rules, Termination termination) {
        this.environmentVariables = environmentVariables;
        this.entities = entities;
        this.rules = rules;
        this.termination = termination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorldDefinition that = (WorldDefinition) o;
        return Objects.equals(environmentVariables, that.environmentVariables) && Objects.equals(entities, that.entities) && Objects.equals(rules, that.rules) && Objects.equals(termination, that.termination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(environmentVariables, entities, rules, termination);
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
}
