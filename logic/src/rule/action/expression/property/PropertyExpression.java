package rule.action.expression.property;

import entity.EntityInstance;
import enums.PropertyType;
import property.PropertyDefinition;
import rule.action.expression.Expression;

public class PropertyExpression implements Expression {
    private final PropertyDefinition propertyDefinition;
    private EntityInstance entityInstance;
    private final String propertyName;

    public PropertyExpression(String propertyName, PropertyDefinition propertyDefinition) {
        this.propertyName = propertyName;
        this.propertyDefinition = propertyDefinition;
    }

    public void setEntityInstance(EntityInstance entityInstance) {
        this.entityInstance = entityInstance;
    }

    @Override
    public Object getValue() {
        return this.entityInstance.getProperties().get(this.propertyName).getValue();
    }

    @Override
    public PropertyType getType() {
        return this.propertyDefinition.getType();
    }
}
