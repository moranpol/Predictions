package rule.action;

import entity.EntityInstance;
import entity.EntityManager;

public class Context {
    private EntityInstance entityInstance;

    public EntityInstance getEntityInstance() {
        return entityInstance;
    }

    public void setEntityInstance(EntityInstance entityInstance) {
        this.entityInstance = entityInstance;
    }
}
