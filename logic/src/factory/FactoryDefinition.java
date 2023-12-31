package factory;

import entity.EntityDefinition;
import enums.PropertyType;
import environment.EnvironmentDefinition;
import exceptions.InvalidNameException;
import exceptions.MissMatchValuesException;
import grid.Grid;
import jaxb.schema.generated.*;
import helpers.ParseFunctions;
import property.PropertyDefinition;
import property.Range;
import rule.Activation;
import rule.Rule;
import rule.action.Action;
import world.WorldDefinition;

import java.util.*;

public abstract class FactoryDefinition {

    public static WorldDefinition createWorldDefinition(PRDWorld prdWorld){
        EnvironmentDefinition environment = createEnvironmentDefinition(prdWorld.getPRDEnvironment());
        Map<String, EntityDefinition> entities = new HashMap<>();
        List<Rule> rules = new ArrayList<>();

        for (PRDEntity entity : prdWorld.getPRDEntities().getPRDEntity()) {
            entities.put(entity.getName(), createEntityDefinition(entity));
        }

        for (PRDRule rule : prdWorld.getPRDRules().getPRDRule()) {
            rules.add(createRule(entities, rule, environment));
        }

        return new WorldDefinition(environment, entities, rules, createGrid(prdWorld.getPRDGrid()), prdWorld.getSleep());
    }

    private static Grid createGrid(PRDWorld.PRDGrid prdGrid){
        if(prdGrid.getRows() < 10 || prdGrid.getRows() > 100){
            throw new MissMatchValuesException("Grid rows must be between 10-100");
        }
        if(prdGrid.getColumns() < 10 || prdGrid.getColumns() > 100){
            throw new MissMatchValuesException("Grid cols must be between 10-100");
        }
        return new Grid(prdGrid.getRows(), prdGrid.getColumns());
    }

    private static EnvironmentDefinition createEnvironmentDefinition(PRDEnvironment prdEnvironment){
        Map<String, PropertyDefinition> environmentVariables = new HashMap<>();
        for (PRDEnvProperty prop : prdEnvironment.getPRDEnvProperty()) {
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
                true, range, null);
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
        return new EntityDefinition(prdEntity.getName(), 0, properties);
    }

    private static PropertyDefinition createEntityPropertyDefinition(PRDProperty prdProperty){
        PropertyType type = createPropertyType(prdProperty.getType());
        Range range = null;
        Object init = null;
        if (prdProperty.getPRDRange() != null){
            range = createRange(prdProperty.getPRDRange());
        }
        if(prdProperty.getPRDValue().getInit() != null){
            init = ParseFunctions.parseInitByType(type, prdProperty.getPRDValue().getInit());
        }

        return new PropertyDefinition(type, prdProperty.getPRDName(), prdProperty.getPRDValue().isRandomInitialize(),
                range, init);
    }

    private static Rule createRule(Map<String, EntityDefinition> entities, PRDRule prdRule, EnvironmentDefinition environmentDefinition){
        try{
            List<Action> actions = FactoryAction.createActionList(prdRule.getPRDActions().getPRDAction(), entities,
                    environmentDefinition);
            return new Rule(prdRule.getName(), actions, createActivation(prdRule.getPRDActivation()));
        } catch (Exception e){
            throw new RuntimeException(e.getMessage() + "\nRule name: " + prdRule.getName());
        }
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
}
