package logic.action;

import logic.entity.EntityInstance;

public abstract class Action {
    private String entityName;

    public abstract void activateAction(EntityInstance mainEntity);
}
