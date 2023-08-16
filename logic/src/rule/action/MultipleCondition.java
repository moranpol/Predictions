package rule.action;

import entity.EntityInstance;
import enums.Logicals;

import java.util.List;

public class MultipleCondition extends Condition{
    private final List<Condition> conditions;
    private final Logicals logic;

    public MultipleCondition(String entityName, List<Condition> conditions, Logicals logic) {
        super(entityName);
        this.conditions = conditions;
        this.logic = logic;
    }

    @Override
    public Boolean invokeCondition(EntityInstance entity) {
        switch (logic){
            case OR:
                return orCondition(entity);
            case AND:
                return andCondition(entity);
        }

        return false;
    }

    private Boolean orCondition(EntityInstance entity){
        for (Condition condition : conditions) {
            if(condition.invokeCondition(entity)){
                return true;
            }
        }

        return false;
    }

    private Boolean andCondition(EntityInstance entity){
        for (Condition condition : conditions) {
            if(!condition.invokeCondition(entity)){
                return false;
            }
        }

        return true;
    }
}
