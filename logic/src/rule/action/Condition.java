package rule.action;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

public abstract class Condition extends Action{
    private List<Action> thenActions = null;
    private List<Action> elseActions = null;
    private Integer totalCount = 0;


    public Condition(String entityName, SecondaryEntity secondaryEntity) {
        super(entityName, secondaryEntity);
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void increaseTotalCountBy1(){
        totalCount++;
    }

    public void setThenActions(List<Action> thenActions) {
        this.thenActions = thenActions;
    }

    public void setElseActions(List<Action> elseActions) {
        this.elseActions = elseActions;
    }

    public abstract Boolean invokeCondition(Context context);

    @Override
    public void activateAction(Context context) {
        Boolean result = invokeCondition(context);
        if(result == null){
            return;
        }

        if (result){
            invokeListActions(thenActions, context);
        } else if (elseActions != null) {
            invokeListActions(elseActions, context);
        }
    }
}
