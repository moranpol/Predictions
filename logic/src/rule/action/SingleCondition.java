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
        String secondEntityName;
        increaseTotalCountBy1();
        try{
            mainEntityInstance = checkAndGetMainEntityInstance(context);
        } catch (Exception e){
            throw new MissMatchValuesException(e.getMessage() + " is not one of the main or second instances.\n" +
                    "    condition action failed.");
        }

        if(mainEntityInstance == null){
            return null;
        }

        secondEntityInstance = checkAndGetSecondEntityInstance(context, mainEntityInstance);
        secondEntityName = checkAndGetSecondEntityName(context, mainEntityInstance);
        switch (operator){
            case EQUAL:
                return equalCondition(mainEntityInstance, secondEntityInstance, secondEntityName);
            case NOTEQUAL:
                return notEqualCondition(mainEntityInstance, secondEntityInstance, secondEntityName);
            case BT:
                return btCondition(mainEntityInstance, secondEntityInstance, secondEntityName);
            case LT:
                return ltCondition(mainEntityInstance, secondEntityInstance, secondEntityName);
        }

        return false;
    }

    private Boolean equalCondition(EntityInstance mainEntityInstance, EntityInstance secondEntityInstance, String secondEntityName){
        Object propertyExpression = property.getValue(mainEntityInstance, secondEntityInstance, secondEntityName);
        Object valueExpression = value.getValue(mainEntityInstance, secondEntityInstance, secondEntityName);
        if(propertyExpression == null || valueExpression == null){
            return null;
        }

        if(CheckFunctions.isNumericValue(property.getType()) && CheckFunctions.isNumericValue(value.getType())){
            return Objects.equals(ParseFunctions.parseNumericTypeToFloat(propertyExpression), ParseFunctions.parseNumericTypeToFloat(valueExpression));
        } else if (property.getType() == PropertyType.BOOLEAN && value.getType() == PropertyType.BOOLEAN) {
            return (propertyExpression == valueExpression);
        } else if (property.getType() == PropertyType.STRING && value.getType() == PropertyType.STRING) {
            String propValue = propertyExpression.toString();
            return (propValue.equals(valueExpression.toString()));
        } else{
            throw new MissMatchValuesException("Condition failed - cannot check if " + property.getType() +
                    " type equals to " + value.getType() + " type.\n" +
                    "    Entity name - " + getMainEntityName() + "\n    Expression - " + valueExpression);
        }
    }

    private Boolean notEqualCondition(EntityInstance mainEntityInstance, EntityInstance secondEntityInstance, String secondEntityName){
        return !equalCondition(mainEntityInstance, secondEntityInstance, secondEntityName);
    }

    private Boolean btCondition(EntityInstance mainEntityInstance, EntityInstance secondEntityInstance, String secondEntityName){
        Object propertyExpression = property.getValue(mainEntityInstance, secondEntityInstance, secondEntityName);
        Object valueExpression = value.getValue(mainEntityInstance, secondEntityInstance, secondEntityName);
        if(propertyExpression == null || valueExpression == null){
            return null;
        }

        if(!CheckFunctions.isNumericValue(property.getType()) || !CheckFunctions.isNumericValue(value.getType())){
            throw new MissMatchValuesException("Condition failed - cannot check if " + property.getType() +
                    " type bigger than " + value.getType() + " type.\n" +
                    "    Entity name - " + getMainEntityName() + "\n    Expression - " + valueExpression);
        }

        return ParseFunctions.parseNumericTypeToFloat(propertyExpression) > ParseFunctions.parseNumericTypeToFloat(valueExpression);
    }

    private Boolean ltCondition(EntityInstance mainEntityInstance, EntityInstance secondEntityInstance, String secondEntityName){
        Object propertyExpression = property.getValue(mainEntityInstance, secondEntityInstance, secondEntityName);
        Object valueExpression = value.getValue(mainEntityInstance, secondEntityInstance, secondEntityName);
        if(propertyExpression == null || valueExpression == null){
            return null;
        }

        if(!CheckFunctions.isNumericValue(property.getType()) || !CheckFunctions.isNumericValue(value.getType())){
            throw new MissMatchValuesException("Condition failed - cannot check if " + property.getType() +
                    " type smaller than " + value.getType() + " type.\n" +
                    "    Entity name - " + getMainEntityName() + "\n    Expression - " + valueExpression);
        }

        return ParseFunctions.parseNumericTypeToFloat(propertyExpression) < ParseFunctions.parseNumericTypeToFloat(valueExpression);
    }
}
