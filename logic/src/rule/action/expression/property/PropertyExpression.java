package rule.action.expression.property;

import entity.EntityInstance;
import enums.PropertyType;
import rule.action.expression.Expression;

public class PropertyExpression implements Expression {

    private EntityInstance entityInstance;
    private final String propertyName;


    public void setEntityInstance(EntityInstance entityInstance) {
        this.entityInstance = entityInstance;
    }

    public PropertyExpression(String propertyName) {
        this.propertyName = propertyName;
    }


    // need to do set before using
    @Override
    public Object getValue() {
        return this.entityInstance.getProperties().get(this.propertyName).getValue();
    }

    // need to do set before using
    @Override
    public PropertyType getType() {
        return this.entityInstance.getProperties().get(this.propertyName).getType();
    }
}
