package factory;

import entity.EntityDefinition;
import enums.PropertyType;
import environment.EnvironmentDefinition;
import exceptions.InvalidNameException;
import jaxb.schema.generated.*;
import property.PropertyDefinition;
import property.Range;
import rule.Activation;
import rule.Rule;
import rule.action.Action;
import termination.Termination;
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

        for (PRDRule rule : prdWorld.getPRDRules().getPRDRule()) {
            rules.add(createRule(entities, rule));
        }

        return new WorldDefinition(environment, entities, rules, createTermination(prdWorld.getPRDTermination()));
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
        return Enum.valueOf(PropertyType.class, type.toUpperCase());
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

    private static Rule createRule(Map<String, EntityDefinition> entities, PRDRule prdRule){
        List<Action> actions = FactoryAction.createActionList(prdRule.getPRDActions().getPRDAction(), entities, prdRule.getName());
        return new Rule(prdRule.getName(), actions, createActivation(prdRule.getPRDActivation()));
    }

    private static Activation createActivation(PRDActivation prdActivation){
        Integer ticks = 1;
        Double probability = 1.0;
        if(prdActivation != null){
            if(prdActivation.getTicks() != null) {
                ticks = prdActivation.getTicks();
            }
            if (prdActivation.getProbability() != null){
                probability = prdActivation.getProbability();
            }
        }

        return new Activation(ticks, probability);
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

    private static Termination createTermination(PRDTermination prdTermination){
        Integer seconds = null;
        Integer ticks = null;

        for (Object termination : prdTermination.getPRDByTicksOrPRDBySecond()) {
            if (isTerminationBySeconds(termination)) {
                PRDBySecond prdBySecond = (PRDBySecond)termination;
                seconds = prdBySecond.getCount();
            } else if (isTerminationByTicks(prdTermination.getPRDByTicksOrPRDBySecond().get(0))) {
                PRDByTicks prdByTicks = (PRDByTicks)termination;
                ticks = prdByTicks.getCount();
            }
        }

        return new Termination(ticks, seconds);
    }

    private static Boolean isTerminationByTicks(Object termination){
        return (termination.getClass() == PRDByTicks.class);
    }

    private static Boolean isTerminationBySeconds(Object termination){
        return (termination.getClass() == PRDBySecond.class);
    }

}
