package rule.action.expression.property;

import entity.EntityInstance;
import enums.PropertyType;
import property.PropertyDefinition;
import rule.action.expression.Expression;

import java.io.Serializable;
import java.util.Objects;

public class PropertyExpression implements Expression, Serializable {
    private final PropertyDefinition propertyDefinition;
    private final String propertyName;

    public PropertyExpression(String propertyName, PropertyDefinition propertyDefinition) {
        this.propertyName = propertyName;
        this.propertyDefinition = propertyDefinition;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyExpression that = (PropertyExpression) o;
        return Objects.equals(propertyDefinition, that.propertyDefinition) && Objects.equals(propertyName, that.propertyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(propertyDefinition, propertyName);
    }

    @Override
    public Object getValue(EntityInstance mainEntityInstance, EntityInstance secondEntityInstance) {
        return mainEntityInstance.getProperties().get(this.propertyName).getValue();
    }

    @Override
    public PropertyType getType() {
        return this.propertyDefinition.getType();
    }
}
