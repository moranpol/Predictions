package rule.action;

import entity.EntityInstance;
import exceptions.InvalidNameException;

import java.io.Serializable;
import java.util.List;

public abstract class Action implements Serializable {
    private final String mainEntityName;
    private SecondaryEntity secondaryEntity;

    public Action(String entityName, SecondaryEntity secondaryEntity) {
        this.mainEntityName = entityName;
        this.secondaryEntity = secondaryEntity;
    }

    public abstract void activateAction(Context context);

    public String getMainEntityName() {
        return mainEntityName;
    }

    public String getSecondaryEntityName() {
        if(secondaryEntity != null) {
            return secondaryEntity.getSecondEntityName();
        }

        return null;
    }

    public SecondaryEntity getSecondaryEntity() {
        return secondaryEntity;
    }

    public void setSecondaryEntity(SecondaryEntity secondaryEntity) {
        this.secondaryEntity = secondaryEntity;
    }

    public EntityInstance checkAndGetMainEntityInstance(Context context){
        if(context.getMainEntityInstance().getName().equals(mainEntityName)){
            return context.getMainEntityInstance();
        } else if(context.getSecondEntityName().equals(mainEntityName)){
           return context.getSecondEntityInstance();
        } else {
            throw new InvalidNameException("main entity " + mainEntityName);
        }
    }

    public EntityInstance checkAndGetSecondEntityInstance(Context context, EntityInstance mainEntity){
        if(!mainEntity.getName().equals(context.getMainEntityInstance().getName())){
            return context.getMainEntityInstance();
        }else {
            return context.getSecondEntityInstance();
        }
    }

    public String checkAndGetSecondEntityName(Context context, EntityInstance mainEntity){
        if(!mainEntity.getName().equals(context.getMainEntityInstance().getName())){
            return context.getMainEntityInstance().getName();
        }else {
            return context.getSecondEntityName();
        }
    }

    public void invokeListActions(List<Action> actionList, Context context){
        Context actionContext = new Context(context.getEntities(), context.getWorldDefinition(), context.getEnvironmentInstance(), context.getGrid(), context.getNewEntityInstances());
        for (Action action : actionList) {
            EntityInstance mainEntityInstance = action.checkAndGetMainEntityInstance(context);
            if(mainEntityInstance != null) {
                actionContext.setMainEntityInstance(mainEntityInstance);
                actionContext.setSecondEntityInstance(checkAndGetSecondEntityInstance(context, mainEntityInstance));
                actionContext.setSecondEntityName(checkAndGetSecondEntityName(context, mainEntityInstance));
                action.activateAction(actionContext);
            }
        }
    }
}
