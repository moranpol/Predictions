package rule.action.expression;

import entity.EntityInstance;
import enums.PropertyType;

public interface Expression{
    public Object getValue(EntityInstance entityInstance);
    public PropertyType getType();
}
