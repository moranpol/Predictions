package rule.action;

import entity.EntityInstance;
import enums.PropertyType;
import enums.ReplaceMode;
import factory.FactoryDefinition;
import factory.FactoryInstance;
import factory.FactoryPropertyInstance;
import helpers.CheckFunctions;
import property.IntProperty;
import property.PropertyDefinition;
import property.PropertyInstance;

import java.util.HashMap;
import java.util.Map;

public class Replace extends Action{
    private final String entityNameToCreate;
    private final ReplaceMode mode;

    public Replace(String entityName, String entityNameToCreate, ReplaceMode mode) {
        super(entityName);
        this.entityNameToCreate = entityNameToCreate;
        this.mode = mode;
    }

    @Override
    public void activateAction(Context context) {
        switch (mode){
            case DERIVED:
                context.setNewEntityInstances(createEntityInstanceDerived(context));
                break;
            case SCRATCH:
                context.setNewEntityInstances(createEntityInstanceScratch(context));
                break;
        }

        context.getEntityInstance().killInstance();
    }

    private EntityInstance createEntityInstanceScratch(Context context) {
        return new EntityInstance(entityNameToCreate,
                FactoryPropertyInstance.createPropertyInstanceMap(context.getWorldDefinition().getEntities().get(entityNameToCreate).getPropertiesOfAllPopulation()));
    }

    private EntityInstance createEntityInstanceDerived(Context context) {
        Map<String, PropertyInstance> propertyInstanceMap = new HashMap<>();
        for (PropertyInstance prop : context.getEntityInstance().getProperties().values()) {
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
