package factory;

import entity.EntityDefinition;
import environment.EnvironmentDefinition;
import property.PropertyDefinition;
import rule.action.expression.Expression;
import rule.action.expression.helper.Helper;
import rule.action.expression.helper.HelperFunction;
import rule.action.expression.property.PropertyExpression;
import rule.action.expression.value.BooleanExpression;
import rule.action.expression.value.FloatExpression;
import rule.action.expression.value.IntExpression;
import rule.action.expression.value.StringExpression;

import java.util.Map;

public abstract class FactoryExpression {
    public static Expression createExpression(String stringExpression, EnvironmentDefinition environmentDefinition,
                                              Map<String, PropertyDefinition> propertyDefinitionMap,
                                              Map<String, EntityDefinition> entityDefinitionMap){
        int indexOfBracket = stringExpression.indexOf("(");
        if (indexOfBracket != -1) {
            return createHelper(stringExpression, environmentDefinition, entityDefinitionMap, indexOfBracket);
        }

        PropertyDefinition property = propertyDefinitionMap.get(stringExpression);
        if(property != null){
            return new PropertyExpression(stringExpression, property);
        }
        else {
            try {
                int num = Integer.parseInt(stringExpression);
                return new IntExpression(num, stringExpression);
            } catch (NumberFormatException ignore) {
            }
            try {
                Float num = Float.parseFloat(stringExpression);
                return new FloatExpression(num, stringExpression);
            } catch (NumberFormatException ignore) {
            }
            if (stringExpression.equals("true")) {
                return new BooleanExpression(true, stringExpression);
            } else if (stringExpression.equals("false")) {
                return new BooleanExpression(false, stringExpression);
            } else {
                return new StringExpression(stringExpression, stringExpression);
            }
        }
    }

    private static HelperFunction createHelperFunction(EnvironmentDefinition environmentDefinition) {
        return new HelperFunction(environmentDefinition);
    }

    private static Helper createHelper(String stringExpression, EnvironmentDefinition environmentDefinition,
                                       Map<String, EntityDefinition> entityDefinitionMap, int indexOfBracket){
        String[] parts = new String[3];
        String funcName = stringExpression.substring(0, indexOfBracket);
        String arguments = stringExpression.substring(indexOfBracket + 1, stringExpression.length() - 1);
        parts[0] = funcName;
        parts[1] = arguments;

        if(funcName.equals("percent")){
            String[] argumentParts = arguments.split(",");
            parts[1] = argumentParts[0];
            parts[2] = argumentParts[1];
        }
        return new Helper(createHelperFunction(environmentDefinition), entityDefinitionMap, stringExpression, parts);
    }
}



