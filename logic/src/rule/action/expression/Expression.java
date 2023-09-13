package rule.action.expression;

import enums.PropertyType;
import rule.action.Context;

public interface Expression{
    Object getValue(Context context);
    PropertyType getType();
    String getString();
}
