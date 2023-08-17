package manager;

import entity.EntityDefinition;
import jaxb.LoadXml;
import menuChoice2.*;
import property.PropertyDefinition;
import property.Range;
import rule.Activation;
import rule.Rule;
import rule.action.Action;
import world.WorldDefinition;
import menuChoice1.DtoXmlPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LogicManager {
    private WorldDefinition worldDefinition;

    public void ReadXmlFile(DtoXmlPath dtoXmlPath){
        LoadXml loadXml = new LoadXml();
        worldDefinition = loadXml.loadAndValidateXml(dtoXmlPath.getPath());
    }

    public DtoWorldInfo displayWorld(){
        List<DtoEntity> dtoEntity = createDtoEntityList();
        List<DtoRule> dtoRules = new ArrayList<>();

        for (Rule rule : worldDefinition.getRules()) {
            dtoRules.add(createDtoRule(rule));
        }

        return new DtoWorldInfo(dtoEntity, dtoRules, createDtoTermination());
    }

    private List<DtoEntity> createDtoEntityList() {
        List<DtoEntity> dtoEntity = new ArrayList<>();
        for (EntityDefinition entity: worldDefinition.getEntities().values()) {
            dtoEntity.add(createDtoEntity(entity));
        }

        return dtoEntity;
    }

    private DtoEntity createDtoEntity(EntityDefinition entityDefinition) {
        return new DtoEntity(entityDefinition.getName(), entityDefinition.getPopulation(),
                createDtoEntityPropertyList(entityDefinition.getPropertiesOfAllPopulation()));
    }

    private List<DtoProperty> createDtoEntityPropertyList(Map<String, PropertyDefinition> propertyDefinitionMap) {
        List<DtoProperty> dtoEntity = new ArrayList<>();
        for (PropertyDefinition prop: propertyDefinitionMap.values()) {
            dtoEntity.add(createEntityProperty(prop));
        }

        return dtoEntity;
    }

    private DtoProperty createEntityProperty(PropertyDefinition prop) {
        if(prop.getRange() == null){
            return new DtoProperty(prop.getName(), prop.getType().toString().toLowerCase(), null, prop.isRandomInit());
        } else {
            return new DtoProperty(prop.getName(), prop.getType().toString().toLowerCase(), createDtoRange(prop.getRange()),
                    prop.isRandomInit());
        }
    }

    private DtoRange createDtoRange(Range range){
        return new DtoRange(range.getFrom(), range.getTo());
    }

    private DtoRule createDtoRule(Rule rule){
        List<String> actionNames = new ArrayList<>();

        for (Action action : rule.getActionList()) {
            actionNames.add(action.getClass().getName());
        }

        return new DtoRule(rule.getName(), createDtoActivation(rule.getActivation()), rule.getActionList().size(),
                actionNames);
    }

    private DtoActivation createDtoActivation(Activation activation){
        return new DtoActivation(activation.getTicks(), activation.getProbability());
    }

    private DtoTermination createDtoTermination(){
        return new DtoTermination(worldDefinition.getTermination().getTicks(), worldDefinition.getTermination().getSeconds());
    }
}
