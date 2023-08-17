package rule.action.expression.value;

import enums.PropertyType;
import rule.action.expression.Expression;

import java.util.Objects;

public class IntValue implements Expression {

    private Integer value;

    public IntValue(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntValue intValue = (IntValue) o;
        return Objects.equals(value, intValue.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
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
