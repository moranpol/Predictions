package rule.action.expression;

import entity.EntityInstance;
import enums.PropertyType;

public interface Expression{
    Object getValue(EntityInstance mainEntityInstance, EntityInstance secondEntityInstance, String secondEntityName);
    PropertyType getType();
    String getString();
}
