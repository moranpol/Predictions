package rule.action.expression.value;

import enums.PropertyType;
import rule.action.expression.Expression;

public class IntValue implements Expression {

    private Integer value;

    public IntValue(Integer value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public PropertyType getType() {
        return PropertyType.DECIMAL;
    }
}
