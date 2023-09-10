package rule.action;

import entity.EntityInstance;
import enums.PropertyType;
import exceptions.MissMatchValuesException;
import exceptions.ParseFloatToIntException;
import helpers.ParseFunctions;
import property.PropertyInstance;
import rule.action.expression.Expression;

public class Increase extends Action{
    private final String propertyName;
    private final Expression by;

    public Increase(String entityName, String propertyName, Expression by, SecondaryEntity secondaryEntity) {
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
        PropertyInstance propertyInstance = context.getMainEntityInstance().getProperties().get(propertyName);
        if(propertyInstance.getType() == PropertyType.DECIMAL && by.getType() == PropertyType.FLOAT){
            throw new ParseFloatToIntException("Increase action failed.\n    Entity name - " + context.getMainEntityInstance().getName() +
                    "\n    Property name - " + propertyName);
        }

        Object expressionValue = by.getValue(context.getMainEntityInstance(), context.getSecondEntityInstance(), context.getSecondEntityName());
        if(expressionValue == null){
            return;
        }

        switch (propertyInstance.getType()){
            case DECIMAL:
                propertyInstance.setValue((Integer)propertyInstance.getValue() + (Integer)expressionValue);
                break;
            case FLOAT:
                Float floatExpression = ParseFunctions.parseNumericTypeToFloat(expressionValue);
                propertyInstance.setValue((Float)propertyInstance.getValue() + floatExpression);
                break;
        }
    }
}
