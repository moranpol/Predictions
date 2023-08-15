package rule.action.expression.value;

import enums.PropertyType;
import rule.action.expression.Expression;

public class FloatValue implements Expression {

    private Float value;

    public FloatValue(Float value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public PropertyType getType() {
        return PropertyType.FLOAT;
    }
}
