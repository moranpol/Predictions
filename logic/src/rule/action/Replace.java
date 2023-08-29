package rule.action;

import entity.EntityInstance;
import enums.ReplaceMode;
import exceptions.MissMatchValuesException;
import factory.FactoryPropertyInstance;
import property.PropertyDefinition;
import property.PropertyInstance;

import java.util.HashMap;
import java.util.Map;

public class Replace extends Action{
    private final String entityNameToCreate;
    private final ReplaceMode mode;

    public Replace(String entityName, String entityNameToCreate, ReplaceMode mode, SecondaryEntity secondaryEntity) {
        super(entityName, secondaryEntity);
        this.entityNameToCreate = entityNameToCreate;
        this.mode = mode;
    }

    @Override
    public void activateAction(Context context) {
        EntityInstance entityInstance;
        try{
            entityInstance = getEntityInstance(context);
        } catch (Exception e){
            throw new MissMatchValuesException(e.getMessage() + " is not one of the main or second instances.\n" +
                    "    Replace action failed.");
        }

        switch (mode){
            case DERIVED:
                context.setNewEntityInstances(createEntityInstanceDerived(context, entityInstance));
                break;
            case SCRATCH:
                context.setNewEntityInstances(createEntityInstanceScratch(context));
                break;
        }

        entityInstance.killInstance();
    }

    private EntityInstance createEntityInstanceScratch(Context context) {
        return new EntityInstance(entityNameToCreate,
                FactoryPropertyInstance.createPropertyInstanceMap(context.getWorldDefinition().getEntities().get(entityNameToCreate).getPropertiesOfAllPopulation()));
    }

    private EntityInstance createEntityInstanceDerived(Context context, EntityInstance entityInstance) {
        Map<String, PropertyInstance> propertyInstanceMap = new HashMap<>();
        for (PropertyInstance prop : entityInstance.getProperties().values()) {
            PropertyDefinition propertyToCreate =
                    context.getWorldDefinition().getEntities().get(entityNameToCreate).getPropertiesOfAllPopulation().get(prop.getName());
            if (propertyToCreate != null && propertyToCreate.getType() == prop.getType()) {
                PropertyDefinition propertyDefinition = new PropertyDefinition(prop.getType(), prop.getName(),
                        false, propertyToCreate.getRange(), prop.getValue());
                propertyInstanceMap.put(propertyToCreate.getName(), FactoryPropertyInstance.createPropertyInstance(propertyDefinition));
            }
        }
        for (PropertyDefinition prop : context.getWorldDefinition().getEntities().get(entityNameToCreate).getPropertiesOfAllPopulation().values()){
            if(!propertyInstanceMap.containsKey(prop.getName())){
                propertyInstanceMap.put(prop.getName(), FactoryPropertyInstance.createPropertyInstance(prop));
            }
        }

        return new EntityInstance(entityNameToCreate, propertyInstanceMap);
    }
}
