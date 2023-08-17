package rule.action;

import entity.EntityInstance;

import java.util.List;
import java.util.Objects;

public abstract class Condition extends Action{
    private List<Action> thenActions = null;
    private List<Action> elseActions = null;

    public Condition(String entityName) {
        super(entityName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Condition condition = (Condition) o;
        return Objects.equals(thenActions, condition.thenActions) && Objects.equals(elseActions, condition.elseActions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), thenActions, elseActions);
    }

    public void setThenActions(List<Action> thenActions) {
        this.thenActions = thenActions;
    }

    public void setElseActions(List<Action> elseActions) {
        this.elseActions = elseActions;
    }

    public abstract Boolean invokeCondition(EntityInstance entity);

    @Override
    public void activateAction(Context context) {
        if (invokeCondition(context.getEntityInstance())){
            invokeListActions(thenActions, context);
        } else if (elseActions != null) {
            invokeListActions(elseActions, context);
        }
    }

    private void invokeListActions(List<Action> actionList, Context context){
        for (Action action : actionList) {
            action.activateAction(context);
        }
    }
}
