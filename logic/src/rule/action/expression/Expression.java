package rule.action.expression;

import entity.EntityInstance;
import enums.PropertyType;

public interface Expression{
    public Object getValue(EntityInstance mainEntityInstance, EntityInstance secondEntityInstance);
    public PropertyType getType();
}
