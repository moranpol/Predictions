package rule.action;

import java.util.List;

public abstract class Condition extends Action{
    private List<Action> thenActions = null;
    private List<Action> elseActions = null;

    public Condition(String entityName) {
        super(entityName);
    }

    public void setThenActions(List<Action> thenActions) {
        this.thenActions = thenActions;
    }

    public void setElseActions(List<Action> elseActions) {
        this.elseActions = elseActions;
    }
}
