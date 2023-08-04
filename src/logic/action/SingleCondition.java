package logic.action;

import logic.action.expression.Expression;
import logic.entity.EntityInstance;
import logic.enums.Operators;

public class SingleCondition extends Condition{
    private String propertyName;
    private Expression value;
    private Operators operator;

    @Override
    public void activateAction(EntityInstance mainEntity) {
        //todo


    }
}
