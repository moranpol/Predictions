package rule.action;

import entity.EntityInstance;
import enums.ReplaceMode;
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

    public ReplaceMode getMode() {
        return mode;
    }

    public String getEntityNameToCreate() {
        return entityNameToCreate;
    }

    @Override
    public void activateAction(Context context) {
        if(context.getMainEntityInstance().isDead()){
            return;
        }

        EntityInstance newEntity;
        if (mode == ReplaceMode.DERIVED) {
            newEntity = createEntityInstanceDerived(context);
        } else {
            newEntity = createEntityInstanceScratch(context);
        }

        context.getGrid().replaceInstancesLocation(context.getMainEntityInstance(), newEntity);
        context.setNewEntityInstances(newEntity);
        context.getMainEntityInstance().killInstance();
    }

    private EntityInstance createEntityInstanceScratch(Context context) {
        return new EntityInstance(entityNameToCreate,
                FactoryPropertyInstance.createPropertyInstanceMap(context.getWorldDefinition().getEntities().get(entityNameToCreate).getPropertiesOfAllPopulation()));
    }

    private EntityInstance createEntityInstanceDerived(Context context) {
        EntityInstance deriveEntity = context.getMainEntityInstance();
        Map<String, PropertyInstance> propertyInstanceMap = new HashMap<>();
        for (PropertyInstance prop : deriveEntity.getProperties().values()) {
            PropertyDefinition propertyToCreate =
                    context.getWorldDefinition().getEntities().get(entityNameToCreate).getPropertiesOfAllPopulation().get(prop.getName());
            if (propertyToCreate != null && propertyToCreate.getType() == prop.getType()) {
                PropertyDefinition propertyDefinition = new PropertyDefinition(prop.getType(), prop.getName(),
                        false, propertyToCreate.getRange(), prop.getCurrValue());
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
