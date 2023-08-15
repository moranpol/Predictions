package rule.action;

import rule.action.expression.Expression;
import entity.EntityInstance;

import java.util.List;
import java.util.Map;

public class Set extends Action{
    private final String propertyName;
    private Expression value;

    public Set(String entityName, String propertyName, Expression value) {
        super(entityName);
        this.propertyName = propertyName;
        this.value = value;
    }

    @Override
    public void activateAction(Map<String, List<EntityInstance>> entities) {
        //todo
    }
}
