package logic.action;

import logic.action.expression.Expression;
import logic.entity.EntityInstance;

public class Decrease extends Action{
    private final String propertyName;
    private Expression by;

    public Decrease(String entityName) {
        //todo
        super(entityName);
        propertyName = null;
    }

    @Override
    public void activateAction(EntityInstance mainEntity) {
        //todo
    }
}
