package rule.action.expression.value;

import entity.EntityInstance;
import enums.PropertyType;
import rule.action.expression.Expression;

import java.io.Serializable;
import java.util.Objects;

public class BooleanExpression implements Expression, Serializable {

    private Boolean value;
    private final String expressionString;

    public BooleanExpression(Boolean value, String expressionString) {
        this.value = value;
        this.expressionString = expressionString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooleanExpression that = (BooleanExpression) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public Object getValue(EntityInstance entityInstance) {
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
