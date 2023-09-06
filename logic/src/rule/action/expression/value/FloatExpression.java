package rule.action.expression.value;

import entity.EntityInstance;
import enums.PropertyType;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FloatExpression that = (FloatExpression) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public Object getValue(EntityInstance mainEntityInstance, EntityInstance secondEntityInstance, String secondEntityName) {
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
