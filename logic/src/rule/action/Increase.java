package rule.action;

import entity.EntityInstance;
import enums.PropertyType;
import exceptions.MissMatchValuesException;
import exceptions.ParseFloatToIntException;
import helpers.ParseFunctions;
import property.PropertyInstance;
import rule.action.expression.Expression;

import java.util.Objects;

public class Increase extends Action{
    private final String propertyName;
    private final Expression by;

    public Increase(String entityName, String propertyName, Expression by, SecondaryEntity secondaryEntity) {
        super(entityName, secondaryEntity);
        this.propertyName = propertyName;
        this.by = by;
    }

    @Override
    public void activateAction(Context context) {
        EntityInstance entityInstance;
        try{
            entityInstance = getEntityInstance(context);
        } catch (Exception e){
            throw new MissMatchValuesException(e.getMessage() + " is not one of the main or second instances.\n" +
                    "    Increase action failed.");
        }

        PropertyInstance propertyInstance = entityInstance.getProperties().get(propertyName);
        if(propertyInstance.getType() == PropertyType.DECIMAL && by.getType() == PropertyType.FLOAT){
            throw new ParseFloatToIntException("Increase action failed.\n    Entity name - " + entityInstance.getName() +
                    "\n    Property name - " + propertyName);
        }

        switch (propertyInstance.getType()){
            case DECIMAL:
                propertyInstance.setValue((Integer)propertyInstance.getValue() + (Integer)by.getValue(entityInstance));
                break;
            case FLOAT:
                Float floatExpression = ParseFunctions.parseNumericTypeToFloat(by.getValue(entityInstance));
                propertyInstance.setValue((Float)propertyInstance.getValue() + floatExpression);
                break;
        }
    }
}
