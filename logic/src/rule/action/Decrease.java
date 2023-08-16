package rule.action;

import enums.PropertyType;
import exceptions.MissMatchValuesException;
import exceptions.ParseFloatToIntException;
import property.PropertyInstance;
import rule.action.expression.Expression;

public class Decrease extends Action{
    private final String propertyName;
    private final Expression by;

    public Decrease(String entityName, String propertyName, Expression by) {
        super(entityName);
        this.propertyName = propertyName;
        this.by = by;
    }

    @Override
    public void activateAction(Context context) {
        setExpressionEntityInstance(by, context.getEntityInstance());
        PropertyInstance propertyInstance = context.getEntityInstance().getProperties().get(propertyName);
        if(propertyInstance.getType() == PropertyType.DECIMAL && by.getType() == PropertyType.FLOAT){
            throw new ParseFloatToIntException("Decrease action failed.\nEntity name - " + getEntityName() +
                    "\nProperty name - " + propertyName);
        }

        switch (propertyInstance.getType()){
            case DECIMAL:
                propertyInstance.setValue((Integer)propertyInstance.getValue() - (Integer)by.getValue());
                break;
            case FLOAT:
                propertyInstance.setValue((Float)propertyInstance.getValue() - (Float) by.getValue());
                break;
        }
    }
}
