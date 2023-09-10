package rule.action;

import java.util.List;

public abstract class Condition extends Action{
    private List<Action> thenActions = null;
    private List<Action> elseActions = null;
    private static Integer totalCount = 0;
    private static Integer orCount = 0;
    private static Integer andCount = 0;

    public Condition(String entityName, SecondaryEntity secondaryEntity) {
        super(entityName, secondaryEntity);
    }

    public static void initializeTotalCount() {
        Condition.totalCount = 0;
    }

    public static void initializeOrCount() {
        Condition.orCount = 0;
    }

    public static void initializeAndCount() {
        Condition.totalCount = 0;
    }

    public static void increaseTotalCountBy1(){
        totalCount++;
    }

    public static void increaseOrCountBy1(){
        orCount++;
    }

    public static void increaseAndCountBy1(){
        andCount++;
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
        initializeAndCount();
        initializeOrCount();
        initializeTotalCount();
        Boolean result = invokeCondition(context);
        if(orCount + andCount == totalCount){
            return;
        }

        if (result){
            invokeListActions(thenActions, context);
        } else if (elseActions != null) {
            invokeListActions(elseActions, context);
        }
    }
}
