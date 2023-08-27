package rule.action.expression.helper;

import entity.EntityInstance;
import environment.EnvironmentDefinition;
import exceptions.InvalidNameException;
import exceptions.MissMatchValuesException;
import helpers.CheckFunctions;
import helpers.ParseFunctions;
import property.PropertyDefinition;
import rule.action.expression.Expression;

import java.io.Serializable;
import java.util.Random;

public class HelperFunction implements Serializable {
    private final EnvironmentDefinition environmentDefinition;
    private EntityInstance entityInstance;

    public HelperFunction(EnvironmentDefinition environmentDefinitionVariables) {
        environmentDefinition = environmentDefinitionVariables;
    }

    public void setEntityInstance(EntityInstance entityInstance) {
        this.entityInstance = entityInstance;
    }

    public EnvironmentDefinition getEnvironmentDefinition() {
        return environmentDefinition;
    }

    public Object environment(String environmentName) {
        PropertyDefinition prop = environmentDefinition.getProperties().get(environmentName);
        if(prop == null){
            throw new InvalidNameException("environment helper function failed.\n    " +
                    "There isn't environment variable: " + environmentName);
        }
        return prop.getValue();
    }

    public Integer random(Integer number){
        Random random = new Random();
        return random.nextInt(number + 1);
    }
    
    public Object evaluate(String propertyOfEntity){
        String[] parts = propertyOfEntity.split("\\.");
        if(!entityInstance.getName().equals(parts[0])){
            throw new InvalidNameException(parts[0]  + " entity name does not refer to the entity in the context .\n" +
                    "    Helper function name - evaluate");
        }
        if(entityInstance.getProperties().containsKey(parts[1])){
            return entityInstance.getProperties().get(parts[1]).getValue();
        } else {
            throw new InvalidNameException(parts[1] + " property name not exist in " + parts[0]
                    + " entity name.\n    Helper function name - evaluate");
        }
    }

    public Float percent(Expression number, Expression percentage){
        if(!CheckFunctions.isNumericValue(number.getType()) || !CheckFunctions.isNumericValue(percentage.getType())){
            throw new MissMatchValuesException("Percent helper function failed - expression is not a number");
        }

        return (ParseFunctions.parseNumericTypeToFloat(number.getValue(entityInstance)) * ParseFunctions.parseNumericTypeToFloat(percentage.getValue(entityInstance))) / 100;
    }

    public Integer ticks(String propertyOfEntity){
        String[] parts = propertyOfEntity.split("\\.");
        if(!entityInstance.getName().equals(parts[0])){
            throw new InvalidNameException(parts[0]  + " entity name does not refer to the entity in the context .\n" +
                    "    Helper function name - ticks");
        }
        if(entityInstance.getProperties().containsKey(parts[1])){
            return entityInstance.getProperties().get(parts[1]).getCurrValueCounterByTicks();
        } else {
           throw new InvalidNameException(parts[1] + " property name not exist in " + parts[0]
                   + " entity name.\n    Helper function name - ticks");
        }
    }
}
