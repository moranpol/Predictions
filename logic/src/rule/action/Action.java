package rule.action;

import entity.EntityInstance;
import exceptions.InvalidNameException;

import java.io.Serializable;

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

    public SecondaryEntity getSecondaryEntity() {
        return secondaryEntity;
    }

    public void setSecondaryEntity(SecondaryEntity secondaryEntity) {
        this.secondaryEntity = secondaryEntity;
    }

    public EntityInstance getEntityInstance(Context context){
        if(context.getMainEntityInstance().getName().equals(mainEntityName)){
            return context.getMainEntityInstance();
        } else if(context.getSecondEntityInstance() != null && context.getSecondEntityInstance().getName().equals(mainEntityName)){
           return context.getSecondEntityInstance();
        } else {
            throw new InvalidNameException("main entity " + mainEntityName);
        }
    }
}
