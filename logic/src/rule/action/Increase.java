package rule.action;

import entity.EntityInstance;
import enums.PropertyType;
import exceptions.MissMatchValuesException;
import exceptions.ParseFloatToIntException;
import property.PropertyInstance;
import rule.action.expression.Expression;

import java.util.List;
import java.util.Map;

public class Increase extends Action{
    private final String propertyName;
    private final Expression by;

    public Increase(String entityName, String propertyName, Expression by) {
        super(entityName);
        this.propertyName = propertyName;
        this.by = by;
    }

    @Override
    public void activateAction(Context context) {
        setExpressionEntityInstance(by, context.getEntityInstance());
        PropertyInstance propertyInstance = context.getEntityInstance().getProperties().get(propertyName);
        if(propertyInstance.getType() == PropertyType.DECIMAL && by.getType() == PropertyType.FLOAT){
            throw new ParseFloatToIntException("Increase action failed.\nEntity name - " + getEntityName() +
                    "\nProperty name - " + propertyName);
        }

        switch (propertyInstance.getType()){
            case DECIMAL:
                propertyInstance.setValue((Integer)propertyInstance.getValue() + (Integer)by.getValue());
                break;
            case FLOAT:
                propertyInstance.setValue((Float)propertyInstance.getValue() + (Float) by.getValue());
                break;
        }
    }
}
