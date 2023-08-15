package rule.action.expression.helper;

import environment.EnvironmentDefinition;
import exceptions.InvalidNameException;
import property.PropertyDefinition;
import property.PropertyInstance;

import java.util.Map;
import java.util.Random;

public abstract class HelperFunction {
    private static EnvironmentDefinition environmentDefinition;


    public static EnvironmentDefinition getEnvironmentDefinition() {
        return environmentDefinition;
    }

    public HelperFunction(EnvironmentDefinition environmentDefinitionVaribel) {
        environmentDefinition = environmentDefinitionVaribel;
    }

    public static Object environment(String environmentName) {
        PropertyDefinition prop = environmentDefinition.getProperties().get(environmentName);
        if(prop == null){
            throw new InvalidNameException("There isn't environment variable: " +(String)environmentName);
        }
        return prop.getValue();
    }

    public static Object random(int number){
        Random random = new Random();
        return random.nextInt(number +1);
    }
}
