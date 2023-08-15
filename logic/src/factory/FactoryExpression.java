package factory;

import environment.EnvironmentDefinition;
import property.PropertyDefinition;
import rule.action.expression.Expression;
import rule.action.expression.helper.Helper;
import rule.action.expression.property.PropertyExpression;
import rule.action.expression.value.BooleanValue;
import rule.action.expression.value.FloatValue;
import rule.action.expression.value.IntValue;
import rule.action.expression.value.StringValue;

import java.util.Objects;
import java.util.Optional;

public abstract class FactoryExpression {
    private static Expression createExpression(String stringExpression, EnvironmentDefinition environmentDefinition){
        String[] parts = stringExpression.split("[(),]");
        if(Objects.equals(parts[0], "environment") || Objects.equals(parts[0], "environment")){
            return new Helper(parts);
        }

        PropertyDefinition property = environmentDefinition.getProperties().get(parts[0]);
        if(property != null){
            return new PropertyExpression(parts[0]);
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
                boolean bool =Boolean.parseBoolean(stringExpression);
                return new BooleanValue(bool);
            }catch (NumberFormatException ignore){}
            return new StringValue(stringExpression);
        }
    }
}



