package rule;

import entity.EntityInstance;
import entity.EntityManager;
import rule.action.Action;
import rule.action.Context;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Rule implements Serializable {
    private final String name;
    private final List<Action> actionList;
    private final Activation activation;

    public String getName() {
        return name;
    }

    public Rule(String name, List<Action> actionList, Activation activation) {
        this.name = name;
        this.actionList = actionList;
        this.activation = activation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return Objects.equals(name, rule.name) && Objects.equals(actionList, rule.actionList) && Objects.equals(activation, rule.activation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, actionList, activation);
    }

    public List<Action> getActionList() {
        return actionList;
    }

    public Activation getActivation() {
        return activation;
    }

    public void activeRule(Map<String, EntityManager> entities, Integer currentTick){
        if (activation.checkIfActivate(currentTick)){
            for (Action action : actionList) {
                try {
                    Context context = new Context();
                    for (EntityInstance entityInstance : entities.get(action.getEntityName()).getEntityInstance()){
                        context.setEntityInstance(entityInstance);
                        action.activateAction(context);
                    }
                    entities.get(action.getEntityName()).killInstances();
                }catch (Exception e){
                    throw new RuntimeException("Rule name: " + name + "\n    " + e.getMessage());
                }
            }
        }
    }
}
