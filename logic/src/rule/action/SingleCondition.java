package rule.action;

import entity.EntityInstance;
import enums.Operators;

import java.util.List;
import java.util.Map;

public class SingleCondition extends Condition{
    private final String propertyName;
    private Expression value;
    private Operators operator;

    public SingleCondition(String entityName, String propertyName, Expression value, Operators operator) {
        //todo
        super(entityName);
        this.propertyName = propertyName;
        this.value = value;
        this.operator = operator;
    }

    @Override
    public void activateAction(Map<String, List<EntityInstance>> entities) {
        //todo


    }
}
