package rule;

import entity.EntityInstance;
import rule.action.Action;

import java.util.List;
import java.util.Map;

public class Rule {
    private final String name;
    private List<Action> actionList;
    private Activation activation;

    public Rule(String name, List<Action> actionList, Activation activation) {
        this.name = name;
        this.actionList = actionList;
        this.activation = activation;
    }

    public void activeRule(Map<String, List<EntityInstance>> entities){
        //todo
    }

    @Override
    public String toString() {
        return "Rule{" +
                "name='" + name + '\'' +
                ", actionList=" + actionList +
                ", activation=" + activation +
                '}';
    }
}
