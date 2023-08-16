package world;

import entity.EntityManager;
import environment.EnvironmentInstance;
import rule.Rule;
import termination.Termination;

import java.util.List;
import java.util.Map;

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
