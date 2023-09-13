package rule.action.expression.value;

import entity.EntityInstance;
import enums.PropertyType;
import rule.action.Context;
import rule.action.expression.Expression;

import java.io.Serializable;
import java.util.Objects;

public class FloatExpression implements Expression, Serializable {
    private final Float value;
    private final String expressionString;

    public FloatExpression(Float value, String expressionString) {
        this.value = value;
        this.expressionString = expressionString;
    }

    @Override
    public Object getValue(Context context) {
        return this.value;
    }

    @Override
    public PropertyType getType() {
        return PropertyType.FLOAT;
    }

    @Override
    public String getString() {
        return expressionString;
    }
}
