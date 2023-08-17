package rule.action.expression.helper;

import environment.EnvironmentDefinition;
import exceptions.InvalidNameException;
import property.PropertyDefinition;

import java.util.Objects;
import java.util.Random;

public class HelperFunction {
    private final EnvironmentDefinition environmentDefinition;

    public HelperFunction(EnvironmentDefinition environmentDefinitionVariables) {
        environmentDefinition = environmentDefinitionVariables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HelperFunction that = (HelperFunction) o;
        return Objects.equals(environmentDefinition, that.environmentDefinition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(environmentDefinition);
    }

    public EnvironmentDefinition getEnvironmentDefinition() {
        return environmentDefinition;
    }

    public Object environment(String environmentName) {
        PropertyDefinition prop = environmentDefinition.getProperties().get(environmentName);
        if(prop == null){
            throw new InvalidNameException("There isn't environment variable: " +(String)environmentName);
        }
        return prop.getValue();
    }

    public Object random(int number){
        Random random = new Random();
        return random.nextInt(number +1);
    }
}
