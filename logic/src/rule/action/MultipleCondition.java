package rule.action;

import enums.Logicals;

import java.util.List;

public class MultipleCondition extends Condition{
    private final List<Condition> conditions;
    private final Logicals logic;
    private Integer orCount = 0;
    private Integer andCount = 0;

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

        if(orCount + andCount == getTotalCount()){
            return null;
        }

        return false;
    }

    private Boolean orCondition(Context context){
        Boolean result;
        for (Condition condition : conditions) {
            result = condition.invokeCondition(context);
            if(result == null){
                orCount++;
            } else if (result) {
                return true;
            }
        }

        return orCount == conditions.size();
    }

    private Boolean andCondition(Context context){
        Boolean result;
        for (Condition condition : conditions) {
            result = condition.invokeCondition(context);
            if(result == null){
                andCount++;
            } else if (!result) {
                return false;
            }
        }

        return andCount != conditions.size();
    }
}
