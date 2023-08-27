package rule.action;

import enums.PropertyType;
import exceptions.ParseFloatToIntException;
import helpers.ParseFunctions;
import property.PropertyInstance;
import rule.action.expression.Expression;

import java.util.Objects;

public class Increase extends Action{
    private final String propertyName;
    private final Expression by;

    public Increase(String entityName, String propertyName, Expression by) {
        super(entityName);
        this.propertyName = propertyName;
        this.by = by;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Increase increase = (Increase) o;
        return Objects.equals(propertyName, increase.propertyName) && Objects.equals(by, increase.by);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), propertyName, by);
    }

    @Override
    public void activateAction(Context context) {
        PropertyInstance propertyInstance = context.getEntityInstance().getProperties().get(propertyName);
        if(propertyInstance.getType() == PropertyType.DECIMAL && by.getType() == PropertyType.FLOAT){
            throw new ParseFloatToIntException("Increase action failed.\n    Entity name - " + getEntityName() +
                    "\n    Property name - " + propertyName);
        }

        switch (propertyInstance.getType()){
            case DECIMAL:
                propertyInstance.setValue((Integer)propertyInstance.getValue() + (Integer)by.getValue(context.getEntityInstance()));
                break;
            case FLOAT:
                Float floatExpression = ParseFunctions.parseNumericTypeToFloat(by.getValue(context.getEntityInstance()));
                propertyInstance.setValue((Float)propertyInstance.getValue() + floatExpression);
                break;
        }
    }
}
