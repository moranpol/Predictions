package rule.action;

import entity.EntityInstance;

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
