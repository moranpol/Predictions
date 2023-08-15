package rule.action;

import entity.EntityInstance;import java.util.List;
import java.util.Map;

public abstract class Action {
    private final String entityName;

    protected Action(String entityName) {
        this.entityName = entityName;
    }

    public abstract void activateAction(Map<String, List<EntityInstance>> entities);
}
