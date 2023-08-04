package logic.action;

import logic.action.expression.Expression;
import logic.entity.EntityInstance;

public class Increase extends Action{
    private String propertyName;
    private Expression by;

    @Override
    public void activateAction(EntityInstance mainEntity) {
        //todo
        Number value;
        //value = by.decipher
    }
}
