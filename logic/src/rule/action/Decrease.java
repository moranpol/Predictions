package rule.action;

import entity.EntityInstance;

import java.util.List;
import java.util.Map;

public class Decrease extends Action{
    private final String propertyName;
    private Expression by;

    public Decrease(String entityName) {
        //todo
        super(entityName);
        propertyName = null;
    }

    @Override
    public void activateAction(Map<String, List<EntityInstance>> entities) {
        //todo
    }
}
