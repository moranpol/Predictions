package rule.action;

import entity.EntityInstance;
import enums.Operators;
import enums.PropertyType;
import exceptions.MissMatchValuesException;
import helpers.CheckFunctions;
import helpers.ParseFunctions;
import rule.action.expression.Expression;

import java.util.Objects;

public class SingleCondition extends Condition{
    private final Expression property;
    private final Expression value;
    private final Operators operator;

    public SingleCondition(String entityName, Expression property, Expression value, Operators operator, SecondaryEntity secondaryEntity) {
        super(entityName, secondaryEntity);
        this.property = property;
        this.value = value;
        this.operator = operator;
    }

    public String getPropertyString() {
        return property.getString();
    }

    public String getValueString() {
        return value.getString();
    }

    public String getOperatorString() {
        return operator.toString();
    }

    @Override
    public Boolean invokeCondition(Context context) {
        EntityInstance mainEntityInstance;
        EntityInstance secondEntityInstance;
        try{
            mainEntityInstance = getEntityInstance(context);
        } catch (Exception e){
            throw new MissMatchValuesException(e.getMessage() + " is not one of the main or second instances.\n" +
                    "    condition action failed.");
        }
        if(!mainEntityInstance.getName().equals(context.getMainEntityInstance().getName())){
            secondEntityInstance = context.getMainEntityInstance();
        }else {
            secondEntityInstance = context.getSecondEntityInstance();
        }

        switch (operator){
            case EQUAL:
                return equalCondition(mainEntityInstance, secondEntityInstance);
            case NOTEQUAL:
                return notEqualCondition(mainEntityInstance, secondEntityInstance);
            case BT:
                return btCondition(mainEntityInstance, secondEntityInstance);
            case LT:
                return ltCondition(mainEntityInstance, secondEntityInstance);
        }

        return false;
    }

    private Boolean equalCondition(EntityInstance mainEntityInstance, EntityInstance secondEntityInstance){
        if(CheckFunctions.isNumericValue(property.getType()) && CheckFunctions.isNumericValue(value.getType())){
            return Objects.equals(ParseFunctions.parseNumericTypeToFloat(property.getValue(mainEntityInstance, secondEntityInstance)),
                    ParseFunctions.parseNumericTypeToFloat(value.getValue(mainEntityInstance, secondEntityInstance)));
        } else if (property.getType() == PropertyType.BOOLEAN && value.getType() == PropertyType.BOOLEAN) {
            return ((Boolean)property.getValue(mainEntityInstance, secondEntityInstance) == (Boolean)value.getValue(mainEntityInstance, secondEntityInstance));
        } else if (property.getType() == PropertyType.STRING && value.getType() == PropertyType.STRING) {
            String propValue = (String)property.getValue(mainEntityInstance, secondEntityInstance);
            return (propValue.equals(value.getValue(mainEntityInstance, secondEntityInstance).toString()));
        } else{
            throw new MissMatchValuesException("Condition failed - cannot check if " + property.getType() +
                    " type equals to " + value.getType() + " type.\n" +
                    "    Entity name - " + getMainEntityName() + "\n    Expression - " + property.getValue(mainEntityInstance, secondEntityInstance));
        }
    }

    private Boolean notEqualCondition(EntityInstance mainEntityInstance, EntityInstance secondEntityInstance){
        return !equalCondition(mainEntityInstance, secondEntityInstance);
    }

    private Boolean btCondition(EntityInstance mainEntityInstance, EntityInstance secondEntityInstance){
        if(!CheckFunctions.isNumericValue(property.getType()) || !CheckFunctions.isNumericValue(value.getType())){
            throw new MissMatchValuesException("Condition failed - cannot check if " + property.getType() +
                    " type bigger than " + value.getType() + " type.\n" +
                    "    Entity name - " + getMainEntityName() + "\n    Expression - " + property.getValue(mainEntityInstance, secondEntityInstance));
        }
        Float floatPropertyVal = ParseFunctions.parseNumericTypeToFloat(property.getValue(mainEntityInstance, secondEntityInstance));
        Float floatExpressionVal = ParseFunctions.parseNumericTypeToFloat(value.getValue(mainEntityInstance, secondEntityInstance));

        return floatPropertyVal > floatExpressionVal;
    }

    private Boolean ltCondition(EntityInstance mainEntityInstance, EntityInstance secondEntityInstance){
        if(!CheckFunctions.isNumericValue(property.getType()) || !CheckFunctions.isNumericValue(value.getType())){
            throw new MissMatchValuesException("Condition failed - cannot check if " + property.getType() +
                    " type smaller than " + value.getType() + " type.\n" +
                    "    Entity name - " + getMainEntityName() + "\n    Expression - " + property.getValue(mainEntityInstance, secondEntityInstance));
        }
        Float floatPropertyVal = ParseFunctions.parseNumericTypeToFloat(property.getValue(mainEntityInstance, secondEntityInstance));
        Float floatExpressionVal = ParseFunctions.parseNumericTypeToFloat(value.getValue(mainEntityInstance, secondEntityInstance));

        return floatPropertyVal < floatExpressionVal;
    }
}
