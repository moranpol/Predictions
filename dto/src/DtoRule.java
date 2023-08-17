import java.util.List;

public class DtoRule {

    private final String name;
    private DtoActivation activation;
    private Integer numOfActions;
    private List<String> actionNames;

    public DtoRule(String name, DtoActivation activation, Integer numOfActions, List<String> actionNames) {
        this.name = name;
        this.activation = activation;
        this.numOfActions = numOfActions;
        this.actionNames = actionNames;
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
