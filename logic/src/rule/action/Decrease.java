package rule.action;

import entity.EntityInstance;

import java.util.List;
import java.util.Map;

public class Decrease extends Action{
    private final String propertyName;
    private Expression by;

    public Decrease(String entityName, String propertyName, Expression by) {
        super(entityName);
        this.propertyName = propertyName;
        this.by = by;
    }

    @Override
    public void activateAction(Map<String, List<EntityInstance>> entities) {
        //todo
    }
}
