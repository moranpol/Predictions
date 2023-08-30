package rule.action;

import entity.EntityInstance;
import enums.PropertyType;
import exceptions.MissMatchValuesException;
import exceptions.ParseFloatToIntException;
import helpers.ParseFunctions;
import property.PropertyInstance;
import rule.action.expression.Expression;

import java.util.Objects;

public class Set extends Action{
    private final String propertyName;
    private final Expression value;

    public Set(String entityName, String propertyName, Expression value, SecondaryEntity secondaryEntity) {
        super(entityName, secondaryEntity);
        this.propertyName = propertyName;
        this.value = value;
    }

    public String getExpressionString(){
        return value.getString();
    }
    @Override
    public void activateAction(Context context) {
        EntityInstance entityInstance;
        try{
            entityInstance = getEntityInstance(context);
        } catch (Exception e){
            throw new MissMatchValuesException(e.getMessage() + " is not one of the main or second instances.\n" +
                    "    Set action failed.");
        }
        PropertyInstance propertyInstance = entityInstance.getProperties().get(propertyName);

        if (propertyInstance.getType() == value.getType()) {
            propertyInstance.setValue(value.getValue(entityInstance));
        } else if(propertyInstance.getType() == PropertyType.FLOAT && value.getType() == PropertyType.DECIMAL){
            Float floatExpression = ParseFunctions.parseNumericTypeToFloat(value.getValue(entityInstance));
            propertyInstance.setValue(floatExpression);
        } else if (propertyInstance.getType() == PropertyType.DECIMAL && value.getType() == PropertyType.FLOAT) {
            throw new ParseFloatToIntException("Set action failed.\n    Entity name - " + entityInstance.getName() +
                    "\n    Property name - " + propertyName);
        } else{
            throw new MissMatchValuesException("Expression value type is " + value.getType() + " and property type is "
                    + propertyInstance.getType() + ".\n    Set action failed.\n    Entity name - " + entityInstance.getName() +
                    "\n    Property name - " + propertyName);
        }

    }
}
