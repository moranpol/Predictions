package rule.action;

import enums.PropertyType;
import exceptions.MissMatchValuesException;
import exceptions.ParseFloatToIntException;
import helpers.ParseFunctions;
import property.PropertyInstance;
import rule.action.expression.Expression;

public class Set extends Action{
    private final String propertyName;
    private final Expression value;

    public Set(String entityName, String propertyName, Expression value, SecondaryEntity secondaryEntity) {
        super(entityName, secondaryEntity);
        this.propertyName = propertyName;
        this.value = value;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getExpressionString(){
        return value.getString();
    }
    @Override
    public void activateAction(Context context) {
        PropertyInstance propertyInstance = context.getMainEntityInstance().getProperties().get(propertyName);
        Object expressionValue = value.getValue(context.getMainEntityInstance(), context.getSecondEntityInstance(), context.getSecondEntityName());
        if (expressionValue == null){
            return;
        }

        if (propertyInstance.getType() == value.getType()) {
            propertyInstance.setValue(expressionValue);
        } else if(propertyInstance.getType() == PropertyType.FLOAT && value.getType() == PropertyType.DECIMAL){
            Float floatExpression = ParseFunctions.parseNumericTypeToFloat(expressionValue);
            propertyInstance.setValue(floatExpression);
        } else if (propertyInstance.getType() == PropertyType.DECIMAL && value.getType() == PropertyType.FLOAT) {
            throw new ParseFloatToIntException("Set action failed.\n    Entity name - " + context.getMainEntityInstance().getName() +
                    "\n    Property name - " + propertyName);
        } else{
            throw new MissMatchValuesException("Expression value type is " + value.getType() + " and property type is "
                    + propertyInstance.getType() + ".\n    Set action failed.\n    Entity name - " + context.getMainEntityInstance().getName() +
                    "\n    Property name - " + propertyName);
        }
    }
}
