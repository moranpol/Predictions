package rule.action;

import entity.EntityInstance;
import enums.PropertyType;
import rule.action.expression.Expression;
import rule.action.expression.property.PropertyExpression;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class Action {
    private final String entityName;

    protected Action(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return Objects.equals(entityName, action.entityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityName);
    }

    public abstract void activateAction(Context context);

    public String getEntityName() {
        return entityName;
    }

    public void setExpressionEntityInstance(Expression expression, EntityInstance entityInstance){
        if(expression instanceof PropertyExpression){
            PropertyExpression propertyExpression = (PropertyExpression)expression;
            propertyExpression.setEntityInstance(entityInstance);
        }
    }
}
