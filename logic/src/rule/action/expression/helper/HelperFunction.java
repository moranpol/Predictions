package rule.action.expression.helper;

import exceptions.InvalidNameException;
import property.PropertyInstance;

import java.util.Map;
import java.util.Random;

public abstract class HelperFunction {
    private static Map<String, PropertyInstance> properties;

    public HelperFunction(Map<String, PropertyInstance> propertiesVaribel) {
        properties = propertiesVaribel;
    }

    public static Object environment(String environmentName) {
        PropertyInstance prop = properties.get((String)environmentName);
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
