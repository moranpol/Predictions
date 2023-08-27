package rule.action;

import java.io.Serializable;
import java.util.Objects;

public abstract class Action implements Serializable {
    private final String entityName;

    public Action(String entityName) {
        this.entityName = entityName;
    }

    public abstract void activateAction(Context context);

    public String getEntityName() {
        return entityName;
    }
}
