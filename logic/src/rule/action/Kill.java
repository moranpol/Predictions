package rule.action;

import entity.EntityInstance;
import exceptions.MissMatchValuesException;

public class Kill extends Action{

    public Kill(String entityName, SecondaryEntity secondaryEntity) {
        super(entityName, secondaryEntity);
    }

    @Override
    public void activateAction(Context context) {
        EntityInstance entityInstance;
        try{
            entityInstance = getEntityInstance(context);
        } catch (Exception e){
            throw new MissMatchValuesException(e.getMessage() + " is not one of the main or second instances.\n" +
                    "    Kill action failed.");
        }
        entityInstance.killInstance();
    }
}
