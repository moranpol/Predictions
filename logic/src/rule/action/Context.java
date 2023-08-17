package rule.action;

import entity.EntityInstance;

import java.util.Objects;

public class Context {
    private EntityInstance entityInstance;

    public EntityInstance getEntityInstance() {
        return entityInstance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Context context = (Context) o;
        return Objects.equals(entityInstance, context.entityInstance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityInstance);
    }

    public void setEntityInstance(EntityInstance entityInstance) {
        this.entityInstance = entityInstance;
    }
}
