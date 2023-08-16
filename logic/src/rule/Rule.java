package rule;

import entity.EntityInstance;
import entity.EntityManager;
import rule.action.Action;
import rule.action.Context;

import java.util.List;
import java.util.Map;

public class Rule {
    private final String name;
    private List<Action> actionList;
    private Activation activation;

    public String getName() {
        return name;
    }

    public Rule(String name, List<Action> actionList, Activation activation) {
        this.name = name;
        this.actionList = actionList;
        this.activation = activation;
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
                    throw new RuntimeException("Rule name: " + name + "\n" + e.getMessage());
                }
            }
        }
    }
}
