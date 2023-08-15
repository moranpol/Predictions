package rule.action.expression;

import enums.PropertyType;

public interface Expression {
    public Object getValue();
    public PropertyType getType();
}
