package logic.action;

import logic.action.expression.Expression;
import logic.entity.EntityInstance;

public class Set extends Action{
    private final String propertyName;
    private Expression value;

    public Set(String entityName) {
        //todo
        super(entityName);
        propertyName = null;
    }

    @Override
    public void activateAction(EntityInstance mainEntity) {
        //todo
    }
}
