package factory;

import entity.EntityDefinition;
import enums.ActionType;
import enums.PropertyType;
import environment.EnvironmentDefinition;
import exceptions.InvalidNameException;
import exceptions.MissMatchValuesException;
import jaxb.schema.generated.*;
import property.PropertyDefinition;
import property.Range;
import rule.Rule;
import world.WorldDefinition;

import java.util.*;

public abstract class FactoryDefinition {

    public static WorldDefinition createWorldDefinition(PRDWorld prdWorld){
        EnvironmentDefinition environment = createEnvironmentDefinition(prdWorld.getPRDEvironment());
        Map<String, EntityDefinition> entities = new HashMap<>();
        List<Rule> rules = new ArrayList<>();

        for (PRDEntity entity : prdWorld.getPRDEntities().getPRDEntity()) {
            entities.put(entity.getName(), createEntityDefinition(entity));
        }

        //createRule(entities);
        for (PRDRule rule : prdWorld.getPRDRules().getPRDRule()) {
            rules.add(new Rule(rule));
        }
        //todo - validate termination

        return null;
    }

    private static EnvironmentDefinition createEnvironmentDefinition(PRDEvironment prdEvironment){
        Map<String, PropertyDefinition> environmentVariables = new HashMap<>();
        for (PRDEnvProperty prop : prdEvironment.getPRDEnvProperty()) {
            if(!environmentVariables.containsKey(prop.getPRDName())){
                environmentVariables.put(prop.getPRDName(), createEnvironmentPropertyDefinition(prop));
            }
            else{
                throw new InvalidNameException("environment " + prop.getPRDName() + " name already exist");
            }
        }

        return new EnvironmentDefinition(environmentVariables);
    }

    private static PropertyDefinition createEnvironmentPropertyDefinition(PRDEnvProperty prdEnvProperty){
        Range range = null;
        if(prdEnvProperty.getPRDRange() != null){
            range = createRange(prdEnvProperty.getPRDRange());
        }

        return new PropertyDefinition(createPropertyType(prdEnvProperty.getType()), prdEnvProperty.getPRDName(),
                null, range, null);
    }

    private static Range createRange(PRDRange prdRange){
        return new Range(prdRange.getFrom(), prdRange.getTo());
    }

    private static PropertyType createPropertyType(String type){
        return Enum.valueOf(PropertyType.class, type);
    }

    private static EntityDefinition createEntityDefinition(PRDEntity prdEntity){
        Map<String, PropertyDefinition> properties = new HashMap<>();
        for (PRDProperty prop : prdEntity.getPRDProperties().getPRDProperty()) {
            if(!properties.containsKey(prop.getPRDName())){
                properties.put(prop.getPRDName(), createEntityPropertyDefinition(prop));
            }
            else{
                throw new InvalidNameException("property " + prop.getPRDName() + " name in " + prdEntity.getName() +
                        " entity already exist");
            }
        }

        return new EntityDefinition(prdEntity.getName(), prdEntity.getPRDPopulation(), properties);
    }

    private static PropertyDefinition createEntityPropertyDefinition(PRDProperty prdProperty){
        PropertyType type = createPropertyType(prdProperty.getType());
        Range range = null;
        Object init = null;
        if (prdProperty.getPRDRange() != null){
            range = createRange(prdProperty.getPRDRange());
        }
        if(prdProperty.getPRDValue().getInit() != null){
            init = createInitByType(type, prdProperty.getPRDValue().getInit());
        }

        return new PropertyDefinition(type, prdProperty.getPRDName(), prdProperty.getPRDValue().isRandomInitialize(),
                range, init);
    }

    private static Rule createRule(Map<String, EntityDefinition> entities, PRDRules rules){
        for (PRDRule rule : rules.getPRDRule()) {
            for (PRDAction action : rule.getPRDActions().getPRDAction()) {
                if(!entities.containsKey(action.getEntity())) {
                    throw new InvalidNameException(action.getEntity() + " entity name not exist - action name: "
                            + action.getType() + " in rule: " + rule.getName());
                }
                if(!entities.get(action.getEntity()).getPropertiesOfAllPopulation().containsKey(action.getProperty())){
                    throw new InvalidNameException(action.getProperty() + " property name not exist in " + action.getEntity() +
                            " entity name - action name: " + action.getType() + " in rule: " + rule.getName());
                }
            }
        }

        return null;
    }

    private static void validateCalculationActions(Map<String,EntityDefinition> entities, PRDAction action, String ruleName){
        if(!isNumericValue(entities.get(action.getEntity()).getPropertiesOfAllPopulation().get(action.getProperty()).getType())){
            throw new MissMatchValuesException("property type not numeric for calculation action "
                    + action.getType() + " in rule: " + ruleName);
        }
    }

    private static boolean isNumericValue(PropertyType type){
        return (type == PropertyType.DECIMAL || type == PropertyType.FLOAT);
    }

    private static boolean isCalculationAction(String actionType){
        return (Objects.equals(actionType, ActionType.CALCULATION.name()) ||
                Objects.equals(actionType, ActionType.INCREASE.name()) ||
                Objects.equals(actionType, ActionType.DECREASE.name()));
    }

    private static Object createInitByType(PropertyType type, String value) {
        Object init = new Object();
        switch (type) {
            case DECIMAL:
                init = Integer.parseInt(value);
                break;
            case FLOAT:
                init = Float.parseFloat(value);
                break;
            case BOOLEAN:
                init = Boolean.parseBoolean(value);
                break;
            case STRING:
                init = value;
                break;
        }

        return init;
    }
}
