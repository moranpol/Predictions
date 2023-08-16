package rule.action;

import enums.PropertyType;
import exceptions.MissMatchValuesException;
import exceptions.ParseFloatToIntException;
import property.PropertyInstance;
import rule.action.expression.Expression;

public class Set extends Action{
    private final String propertyName;
    private final Expression value;

    public Set(String entityName, String propertyName, Expression value) {
        super(entityName);
        this.propertyName = propertyName;
        this.value = value;
    }

    @Override
    public void activateAction(Context context) {
        setExpressionEntityInstance(value, context.getEntityInstance());
        PropertyInstance propertyInstance = context.getEntityInstance().getProperties().get(propertyName);

        if (propertyInstance.getType() == value.getType() ||
                (propertyInstance.getType() == PropertyType.FLOAT && value.getType() == PropertyType.DECIMAL)){
            propertyInstance.setValue(value.getValue());
        } else if (propertyInstance.getType() == PropertyType.DECIMAL && value.getType() == PropertyType.FLOAT) {
            throw new ParseFloatToIntException("Set action failed.\nEntity name - " + getEntityName() +
                    "\nProperty name - " + propertyName);
        } else{
            throw new MissMatchValuesException("Expression value type is " + value.getType() + " and property type is "
                    + propertyInstance.getType() + ".\nSet action failed.\nEntity name - " + getEntityName() +
                    "\nProperty name - " + propertyName);
        }
    }
}
