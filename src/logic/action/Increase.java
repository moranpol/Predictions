package logic.action;

import logic.action.expression.Expression;
import logic.entity.EntityInstance;

public class Increase extends Action{
    private final String propertyName;
    private Expression by;

    public Increase(String entityName, String propertyName, Expression by) {
        //todo
        super(entityName);
        this.propertyName = propertyName;
        this.by = by;
    }

    @Override
    public void activateAction(EntityInstance mainEntity) {
        //todo
        Number value;
        //value = by.decipher
    }
}
