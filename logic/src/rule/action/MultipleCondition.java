package rule.action;

import enums.Logicals;

import java.util.List;

public class MultipleCondition extends Condition{
    private final List<Condition> conditions;
    private final Logicals logic;

    public int getConditionsAmount(){
        return conditions.size();
    }

    public String getLogicString() {
        return logic.toString();
    }

    public MultipleCondition(String entityName, List<Condition> conditions, Logicals logic, SecondaryEntity secondaryEntity) {
        super(entityName, secondaryEntity);
        this.conditions = conditions;
        this.logic = logic;
    }

    @Override
    public Boolean invokeCondition(Context context) {
        switch (logic){
            case OR:
                return orCondition(context);
            case AND:
                return andCondition(context);
        }

        return false;
    }

    private Boolean orCondition(Context context){
        if(isAllConditionsNull(context)){
            return null;
        }

        Boolean result;
        for (Condition condition : conditions) {
            result = condition.invokeCondition(context);
            if(result != null && result){
                return true;
            }
        }

        return false;
    }

    private Boolean andCondition(Context context){
        if(isAllConditionsNull(context)){
            return null;
        }

        Boolean result;
        for (Condition condition : conditions) {
            result = condition.invokeCondition(context);
            if(result != null && !result){
                return false;
            }
        }

        return true;
    }

    private Boolean isAllConditionsNull(Context context){
        for (Condition condition : conditions) {
            if(condition.invokeCondition(context) != null){
                return false;
            }
        }
        return true;
    }
}
