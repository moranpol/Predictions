package rule.action.expression.value;

import enums.PropertyType;
import rule.action.Context;
import rule.action.expression.Expression;

import java.io.Serializable;

public class BooleanExpression implements Expression, Serializable {
    private final Boolean value;
    private final String expressionString;

    public BooleanExpression(Boolean value, String expressionString) {
        this.value = value;
        this.expressionString = expressionString;
    }

    @Override
    public Object getValue(Context context) {
        return this.value;
    }

    @Override
    public PropertyType getType() {
        return PropertyType.BOOLEAN;
    }

    @Override
    public String getString() {
        return expressionString;
    }
}
