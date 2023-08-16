package factory;

import environment.EnvironmentDefinition;
import property.PropertyDefinition;
import rule.action.expression.Expression;
import rule.action.expression.helper.Helper;
import rule.action.expression.helper.HelperFunction;
import rule.action.expression.property.PropertyExpression;
import rule.action.expression.value.BooleanValue;
import rule.action.expression.value.FloatValue;
import rule.action.expression.value.IntValue;
import rule.action.expression.value.StringValue;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public abstract class FactoryExpression {
    public static Expression createExpression(String stringExpression, EnvironmentDefinition environmentDefinition,
                                              Map<String, PropertyDefinition> propertyDefinitionMap){
        String[] parts = stringExpression.split("[(),]");
        if(Objects.equals(parts[0], "environment") || Objects.equals(parts[0], "random")){
            return new Helper(createHelperFunction(environmentDefinition), parts);
        }

        PropertyDefinition property = propertyDefinitionMap.get(parts[0]);
        if(property != null){
            return new PropertyExpression(parts[0], property);
        }
        else{
            try {
                int num = Integer.parseInt(stringExpression);
                return new IntValue(num);
            }catch (NumberFormatException ignore){}
            try {
                Float num = Float.parseFloat(stringExpression);
                return new FloatValue(num);
            }catch (NumberFormatException ignore){}
            try {
                boolean bool = Boolean.parseBoolean(stringExpression);
                return new BooleanValue(bool);
            }catch (NumberFormatException ignore){}
                return new StringValue(stringExpression);
        }
    }

    private static HelperFunction createHelperFunction(EnvironmentDefinition environmentDefinition) {
        return new HelperFunction(environmentDefinition);
    }
}



