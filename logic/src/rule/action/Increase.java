package rule.action;

import entity.EntityInstance;
import rule.action.expression.Expression;

import java.util.List;
import java.util.Map;

public class Increase extends Action{
    private final String propertyName;
    private Expression by;

    public Increase(String entityName, String propertyName, Expression by) {
        super(entityName);
        this.propertyName = propertyName;
        this.by = by;
    }

    @Override
    public void activateAction(Map<String, List<EntityInstance>> entities) {
        //todo
    }
}
