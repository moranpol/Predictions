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
        Boolean result;
        switch (logic){
            case OR:
                return orCondition(context);
            case AND:
                return andCondition(context);
        }

        return false;
    }

    private Boolean orCondition(Context context){
        int count = 0;
        Boolean result;
        for (Condition condition : conditions) {
            result = condition.invokeCondition(context);
            if(result == null){
                increaseOrCountBy1();
                count++;
            } else if (result) {
                return true;
            }
        }

        return count == conditions.size();
    }

    private Boolean andCondition(Context context){
        int count = 0;
        Boolean result;
        for (Condition condition : conditions) {
            result = condition.invokeCondition(context);
            if(result == null){
                increaseAndCountBy1();
                count++;
            } else if (!result) {
                return false;
            }
        }

        return count != conditions.size();
    }
}
