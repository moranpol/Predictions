package rule.action.expression.value;

import entity.EntityInstance;
import enums.PropertyType;
import rule.action.expression.Expression;

import java.io.Serializable;
import java.util.Objects;

public class StringExpression implements Expression, Serializable {

    private final String value;
    private final String expressionString;

    public StringExpression(String value, String expressionString) {
        this.value = value;
        this.expressionString = expressionString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringExpression that = (StringExpression) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public Object getValue(EntityInstance mainEntityInstance, EntityInstance secondEntityInstance) {
        return this.value;
    }

    @Override
    public PropertyType getType() {
        return PropertyType.STRING;
    }

    @Override
    public String getString() {
        return expressionString;
    }
}
