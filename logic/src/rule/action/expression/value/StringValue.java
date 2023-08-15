package rule.action.expression.value;

import enums.PropertyType;
import rule.action.expression.Expression;

public class StringValue implements Expression {

    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public PropertyType getType() {
        return PropertyType.STRING;
    }
}
