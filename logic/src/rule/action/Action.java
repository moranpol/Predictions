package rule.action;

import java.io.Serializable;
import java.util.Objects;

public abstract class Action implements Serializable {
    private final String entityName;

    public Action(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return Objects.equals(entityName, action.entityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityName);
    }

    public abstract void activateAction(Context context);

    public String getEntityName() {
        return entityName;
    }
}
