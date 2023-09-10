package rule.action.expression.value;

import entity.EntityInstance;
import enums.PropertyType;
import rule.action.expression.Expression;

import java.io.Serializable;
import java.util.Objects;

public class IntExpression implements Expression, Serializable {

    private final Integer value;
    private final String expressionString;

    public IntExpression(Integer value, String expressionString) {
        this.value = value;
        this.expressionString = expressionString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntExpression intExpression = (IntExpression) o;
        return Objects.equals(value, intExpression.value);
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
        return PropertyType.DECIMAL;
    }

    @Override
    public String getString() {
        return expressionString;
    }
}
