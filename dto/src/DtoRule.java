import rule.Activation;
import rule.action.Action;

import java.util.List;

public class DtoRule {

    private final String name;
    private DtoActivation activation;
    private Integer numOfActions;
    private List<String> actionNames;

    public DtoRule(String name, Activation activation,List<Action> actionList) {
        this.name = name;
        //this.activation = activation;
        this.numOfActions = actionList.size();
        //this.actionNames = actionNames;
    }

    public String getName() {
        return name;
    }

    public DtoActivation getActivation() {
        return activation;
    }

    public Integer getNumOfActions() {
        return numOfActions;
    }

    public List<String> getActionNames() {
        return actionNames;
    }
}
