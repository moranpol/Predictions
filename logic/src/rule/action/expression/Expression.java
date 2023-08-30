package rule.action.expression;

import entity.EntityInstance;
import enums.PropertyType;

public interface Expression{
    Object getValue(EntityInstance mainEntityInstance, EntityInstance secondEntityInstance);
    PropertyType getType();
    String getString();
}
