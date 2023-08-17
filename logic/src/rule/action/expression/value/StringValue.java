package rule.action.expression.value;

import enums.PropertyType;
import rule.action.expression.Expression;

import java.util.Objects;

public class StringValue implements Expression {

    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringValue that = (StringValue) o;
        return Objects.equals(value, that.value);
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
        return PropertyType.STRING;
    }
}
