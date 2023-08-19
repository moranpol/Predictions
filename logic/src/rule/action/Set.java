package rule.action;

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

    public Set(String entityName, String propertyName, Expression value) {
        super(entityName);
        this.propertyName = propertyName;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Set set = (Set) o;
        return Objects.equals(propertyName, set.propertyName) && Objects.equals(value, set.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), propertyName, value);
    }

    @Override
    public void activateAction(Context context) {
        setExpressionEntityInstance(value, context.getEntityInstance());
        PropertyInstance propertyInstance = context.getEntityInstance().getProperties().get(propertyName);

        if (propertyInstance.getType() == value.getType()) {
            propertyInstance.setValue(value.getValue());
        } else if(propertyInstance.getType() == PropertyType.FLOAT && value.getType() == PropertyType.DECIMAL){
            Float floatExpression = ParseFunctions.parseNumericTypeToFloat(value.getValue());
            propertyInstance.setValue(floatExpression);
        } else if (propertyInstance.getType() == PropertyType.DECIMAL && value.getType() == PropertyType.FLOAT) {
            throw new ParseFloatToIntException("Set action failed.\n    Entity name - " + getEntityName() +
                    "\n    Property name - " + propertyName);
        } else{
            throw new MissMatchValuesException("Expression value type is " + value.getType() + " and property type is "
                    + propertyInstance.getType() + ".\n    Set action failed.\n    Entity name - " + getEntityName() +
                    "\n    Property name - " + propertyName);
        }

    }
}
