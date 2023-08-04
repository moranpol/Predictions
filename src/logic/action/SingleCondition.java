package logic.action;

import logic.action.expression.Expression;
import logic.entity.EntityInstance;
import logic.enums.Operators;

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
    public void activateAction(EntityInstance mainEntity) {
        //todo


    }
}
