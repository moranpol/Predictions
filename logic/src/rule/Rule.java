package rule;

import entity.EntityInstance;
import generated.PRDRule;
import rule.action.Action;
import java.util.List;
import java.util.Map;

public class Rule {
    private final String name;
    private List<Action> actionList;
    private Activation activation;

    public Rule(PRDRule rule) {
        this.name = rule.getName();
        this.activation = new Activation(rule.getPRDActivation());
        //todo - actionList
    }

    public void activeRule(Tick ticks, Map<String, List<EntityInstance>> entities){
        if(activation.checkIfActivate(ticks)){
            for (Action action : actionList) {
                action.activateAction(entities);
            }
        }
    }
}
