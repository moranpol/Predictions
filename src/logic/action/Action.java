package logic.action;

import logic.entity.EntityInstance;

public abstract class Action {
    private final String entityName;

    protected Action(String entityName) {
        //todo
        this.entityName = entityName;
    }

    public abstract void activateAction(EntityInstance mainEntity);
}
