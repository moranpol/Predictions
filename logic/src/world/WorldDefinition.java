package world;

import entity.EntityDefinition;
import environment.EnvironmentDefinition;
import rule.Rule;
import termination.Termination;

import java.util.List;
import java.util.Map;

public class WorldDefinition {
    private EnvironmentDefinition environmentVariables;
    private Map<String, EntityDefinition> entities;
    private List<Rule> rules;
    private Termination termination;

    public WorldDefinition(EnvironmentDefinition environmentVariables, Map<String, EntityDefinition> entities, List<Rule> rules, Termination termination) {
        this.environmentVariables = environmentVariables;
        this.entities = entities;
        this.rules = rules;
        this.termination = termination;
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

    @Override
    public String toString() {
        return "WorldDefinition{" +
                "environmentVariables=" + environmentVariables +
                ", entities=" + entities +
                ", rules=" + rules +
                ", termination=" + termination +
                '}';
    }
}
