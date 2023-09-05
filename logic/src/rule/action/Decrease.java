package rule.action;

import entity.EntityInstance;
import enums.PropertyType;
import exceptions.MissMatchValuesException;
import exceptions.ParseFloatToIntException;
import helpers.ParseFunctions;
import property.PropertyInstance;
import rule.action.expression.Expression;

public class Decrease extends Action{
    private final String propertyName;
    private final Expression by;

    public Decrease(String entityName, String propertyName, Expression by, SecondaryEntity secondaryEntity) {
        super(entityName, secondaryEntity);
        this.propertyName = propertyName;
        this.by = by;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getExpressionString() {
        return by.getString();
    }

    @Override
    public void activateAction(Context context) {
        EntityInstance entityInstance;
        try{
            entityInstance = getEntityInstance(context);
        } catch (Exception e){
            throw new MissMatchValuesException(e.getMessage() + " is not one of the main or second instances.\n" +
                    "    Decrease action failed.");
        }
        PropertyInstance propertyInstance = entityInstance.getProperties().get(propertyName);
        if(propertyInstance.getType() == PropertyType.DECIMAL && by.getType() == PropertyType.FLOAT){
            throw new ParseFloatToIntException("Decrease action failed.\n    Entity name - " + entityInstance.getName() +
                    "\n    Property name - " + propertyName);
        }

        switch (propertyInstance.getType()){
            case DECIMAL:
                propertyInstance.setValue((Integer)propertyInstance.getValue() - (Integer)by.getValue(context.getMainEntityInstance(), context.getSecondEntityInstance()));
                break;
            case FLOAT:
                Float floatExpression = ParseFunctions.parseNumericTypeToFloat(by.getValue(context.getMainEntityInstance(), context.getSecondEntityInstance()));
                propertyInstance.setValue((Float)propertyInstance.getValue() - floatExpression);
                break;
        }
    }
}
