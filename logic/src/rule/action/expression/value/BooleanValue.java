package rule.action.expression.value;

import enums.PropertyType;
import rule.action.expression.Expression;

public class BooleanValue implements Expression {

    private Boolean value;

    public BooleanValue(Boolean value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public PropertyType getType() {
        return PropertyType.BOOLEAN;
    }
}
