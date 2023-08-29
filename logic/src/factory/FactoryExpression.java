package factory;

import entity.EntityDefinition;
import environment.EnvironmentDefinition;
import helpers.CheckFunctions;
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
        String[] parts = stringExpression.split("[(),]");
        if(CheckFunctions.isHelperFunction(parts[0])){
            return new Helper(createHelperFunction(environmentDefinition), entityDefinitionMap, parts);
        }

        PropertyDefinition property = propertyDefinitionMap.get(parts[0]);
        if(property != null){
            return new PropertyExpression(parts[0], property);
        }
        else {
            try {
                int num = Integer.parseInt(stringExpression);
                return new IntExpression(num);
            } catch (NumberFormatException ignore) {
            }
            try {
                Float num = Float.parseFloat(stringExpression);
                return new FloatExpression(num);
            } catch (NumberFormatException ignore) {
            }
            if (stringExpression.equals("true")) {
                return new BooleanExpression(true);
            } else if (stringExpression.equals("false")) {
                return new BooleanExpression(false);
            } else {
                return new StringExpression(stringExpression);
            }
        }
    }

    private static HelperFunction createHelperFunction(EnvironmentDefinition environmentDefinition) {
        return new HelperFunction(environmentDefinition);
    }
}



