package rule.action;

import enums.PropertyType;
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
        PropertyInstance propertyInstance = context.getMainEntityInstance().getProperties().get(propertyName);
        if(propertyInstance.getType() == PropertyType.DECIMAL && by.getType() == PropertyType.FLOAT){
            throw new ParseFloatToIntException("Decrease action failed.\n    Entity name - " + context.getMainEntityInstance().getName() +
                    "\n    Property name - " + propertyName);
        }

        Object expressionValue = by.getValue(context);
        if(expressionValue == null){
            return;
        }

        switch (propertyInstance.getType()){
            case DECIMAL:
                propertyInstance.setCurrValue((Integer)propertyInstance.getCurrValue() - (Integer) expressionValue);
                break;
            case FLOAT:
                Float floatExpression = ParseFunctions.parseNumericTypeToFloat(expressionValue);
                propertyInstance.setCurrValue((Float)propertyInstance.getCurrValue() - floatExpression);
                break;
        }
    }
}
