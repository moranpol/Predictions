package rule.action;

import enums.PropertyType;
import exceptions.ParseFloatToIntException;
import helpers.ParseFunctions;
import property.PropertyInstance;
import rule.action.expression.Expression;

import java.util.Objects;

public class Decrease extends Action{
    private final String propertyName;
    private final Expression by;

    public Decrease(String entityName, String propertyName, Expression by) {
        super(entityName);
        this.propertyName = propertyName;
        this.by = by;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Decrease decrease = (Decrease) o;
        return Objects.equals(propertyName, decrease.propertyName) && Objects.equals(by, decrease.by);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), propertyName, by);
    }

    @Override
    public void activateAction(Context context) {
        setExpressionEntityInstance(by, context.getEntityInstance());
        PropertyInstance propertyInstance = context.getEntityInstance().getProperties().get(propertyName);
        if(propertyInstance.getType() == PropertyType.DECIMAL && by.getType() == PropertyType.FLOAT){
            throw new ParseFloatToIntException("Decrease action failed.\n    Entity name - " + getEntityName() +
                    "\n    Property name - " + propertyName);
        }

        switch (propertyInstance.getType()){
            case DECIMAL:
                propertyInstance.setValue((Integer)propertyInstance.getValue() - (Integer)by.getValue());
                break;
            case FLOAT:
                Float floatExpression = ParseFunctions.parseNumericTypeToFloat(by.getValue());
                propertyInstance.setValue((Float)propertyInstance.getValue() - floatExpression);
                break;
        }
    }
}
