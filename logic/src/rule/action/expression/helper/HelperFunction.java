package rule.action.expression.helper;

import entity.EntityInstance;
import environment.EnvironmentDefinition;
import environment.EnvironmentInstance;
import exceptions.InvalidNameException;
import exceptions.MissMatchValuesException;
import helpers.CheckFunctions;
import helpers.ParseFunctions;
import property.PropertyInstance;
import rule.action.Context;
import rule.action.expression.Expression;

import java.io.Serializable;
import java.util.Random;

public class HelperFunction implements Serializable {
    private final EnvironmentDefinition environmentDefinition;

    public HelperFunction(EnvironmentDefinition environmentDefinitionVariables) {
        environmentDefinition = environmentDefinitionVariables;
    }

    public EnvironmentDefinition getEnvironmentDefinition() {
        return environmentDefinition;
    }

    public Object environment(String environmentName, EnvironmentInstance environment) {
        PropertyInstance prop = environment.getProperties().get(environmentName);
        if(prop == null){
            throw new InvalidNameException("environment helper function failed.\n    " +
                    "There isn't environment variable: " + environmentName);
        }
        return prop.getCurrValue();
    }

    public Integer random(Integer number){
        Random random = new Random();
        return random.nextInt(number + 1);
    }
    
    public Object evaluate(String propertyOfEntity, EntityInstance mainEntity, EntityInstance secondEntity, String secondEntityName){
        String[] parts = propertyOfEntity.split("\\.");
        EntityInstance entityInstance = getEntityInstance(parts[0], mainEntity, secondEntity, secondEntityName);
        if(entityInstance == null){
            return null;
        }
        if(entityInstance.getProperties().containsKey(parts[1])){
            return entityInstance.getProperties().get(parts[1]).getCurrValue();
        } else {
            throw new InvalidNameException(parts[1] + " property name not exist in " + parts[0]
                    + " entity name.\n    Helper function name - evaluate");
        }
    }

    public Float percent(Expression number, Expression percentage, Context context){
        if(!CheckFunctions.isNumericValue(number.getType()) || !CheckFunctions.isNumericValue(percentage.getType())){
            throw new MissMatchValuesException("Percent helper function failed - expression is not a number");
        }

        Object numberObject = number.getValue(context);
        Object percentageObject = percentage.getValue(context);
        if (numberObject == null || percentageObject == null){
            return null;
        }

        return (ParseFunctions.parseNumericTypeToFloat(numberObject) * ParseFunctions.parseNumericTypeToFloat(percentageObject)) / 100;
    }

    public Integer ticks(String propertyOfEntity, EntityInstance mainEntity, EntityInstance secondEntity, String secondEntityName){
        String[] parts = propertyOfEntity.split("\\.");
        EntityInstance entityInstance = getEntityInstance(parts[0],  mainEntity, secondEntity, secondEntityName);
        if(entityInstance == null){
            return null;
        }
        if(entityInstance.getProperties().containsKey(parts[1])){
            return entityInstance.getProperties().get(parts[1]).getCurrValueCounterByTicks();
        } else {
           throw new InvalidNameException(parts[1] + " property name not exist in " + parts[0]
                   + " entity name.\n    Helper function name - ticks");
        }
    }

    private EntityInstance getEntityInstance(String entityName, EntityInstance mainEntity, EntityInstance secondEntity, String secondEntityName){
        if (entityName.equals(mainEntity.getName())) {
            return mainEntity;
        } else if (entityName.equals(secondEntityName)) {
            return secondEntity;
        } else{
            throw new InvalidNameException(entityName  + " entity name does not refer to the entity in the context.\n");
        }
    }
}
