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

    @Override
    public Boolean invokeCondition(Context context) {
        EntityInstance entityInstance;
        try{
            entityInstance = getEntityInstance(context);
        } catch (Exception e){
            throw new MissMatchValuesException(e.getMessage() + " is not one of the main or second instances.\n" +
                    "    Condition divide action failed.");
        }

        switch (operator){
            case EQUAL:
                return equalCondition(entityInstance);
            case NOTEQUAL:
                return notEqualCondition(entityInstance);
            case BT:
                return btCondition(entityInstance);
            case LT:
                return ltCondition(entityInstance);
        }

        return false;
    }

    private Boolean equalCondition(EntityInstance entity){
        if(CheckFunctions.isNumericValue(property.getType()) && CheckFunctions.isNumericValue(value.getType())){
            return Objects.equals(ParseFunctions.parseNumericTypeToFloat(property.getValue(entity)),
                    ParseFunctions.parseNumericTypeToFloat(value.getValue(entity)));
        } else if (property.getType() == PropertyType.BOOLEAN && value.getType() == PropertyType.BOOLEAN) {
            return ((Boolean)property.getValue(entity) == (Boolean)value.getValue(entity));
        } else if (property.getType() == PropertyType.STRING && value.getType() == PropertyType.STRING) {
            String propValue = (String)property.getValue(entity);
            return (propValue.equals(value.getValue(entity).toString()));
        } else{
            throw new MissMatchValuesException("Condition failed - cannot check if " + property.getType() +
                    " type equals to " + value.getType() + " type.\n" +
                    "    Entity name - " + getMainEntityName() + "\n    Expression - " + property.getValue(entity));
        }
    }

    private Boolean notEqualCondition(EntityInstance entity){
        return !equalCondition(entity);
    }

    private Boolean btCondition(EntityInstance entity){
        if(!CheckFunctions.isNumericValue(property.getType()) || !CheckFunctions.isNumericValue(value.getType())){
            throw new MissMatchValuesException("Condition failed - cannot check if " + property.getType() +
                    " type bigger than " + value.getType() + " type.\n" +
                    "    Entity name - " + getMainEntityName() + "\n    Expression - " + property.getValue(entity));
        }
        Float floatPropertyVal = ParseFunctions.parseNumericTypeToFloat(property.getValue(entity));
        Float floatExpressionVal = ParseFunctions.parseNumericTypeToFloat(value.getValue(entity));

        return floatPropertyVal > floatExpressionVal;
    }

    private Boolean ltCondition(EntityInstance entity){
        if(!CheckFunctions.isNumericValue(property.getType()) || !CheckFunctions.isNumericValue(value.getType())){
            throw new MissMatchValuesException("Condition failed - cannot check if " + property.getType() +
                    " type smaller than " + value.getType() + " type.\n" +
                    "    Entity name - " + getMainEntityName() + "\n    Expression - " + property.getValue(entity));
        }
        Float floatPropertyVal = ParseFunctions.parseNumericTypeToFloat(property.getValue(entity));
        Float floatExpressionVal = ParseFunctions.parseNumericTypeToFloat(value.getValue(entity));

        return floatPropertyVal < floatExpressionVal;
    }
}
