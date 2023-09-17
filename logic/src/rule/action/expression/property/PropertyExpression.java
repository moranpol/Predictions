package rule.action.expression.property;

import enums.PropertyType;
import property.PropertyDefinition;
import rule.action.Context;
import rule.action.expression.Expression;

import java.io.Serializable;

public class PropertyExpression implements Expression, Serializable {
    private final PropertyDefinition propertyDefinition;
    private final String propertyName;

    public PropertyExpression(String propertyName, PropertyDefinition propertyDefinition) {
        this.propertyName = propertyName;
        this.propertyDefinition = propertyDefinition;
    }

    @Override
    public Object getValue(Context context) {
        return context.getMainEntityInstance().getProperties().get(this.propertyName).getCurrValue();
    }

    @Override
    public PropertyType getType() {
        return this.propertyDefinition.getType();
    }

    @Override
    public String getString() {
        return propertyName;
    }
}
