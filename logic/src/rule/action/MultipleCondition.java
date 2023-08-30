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
        for (Condition condition : conditions) {
            if(condition.invokeCondition(context)){
                return true;
            }
        }

        return false;
    }

    private Boolean andCondition(Context context){
        for (Condition condition : conditions) {
            if(!condition.invokeCondition(context)){
                return false;
            }
        }

        return true;
    }
}
