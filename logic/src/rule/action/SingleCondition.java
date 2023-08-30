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
        switch (operator){
            case EQUAL:
                return equalCondition(context);
            case NOTEQUAL:
                return notEqualCondition(context);
            case BT:
                return btCondition(context);
            case LT:
                return ltCondition(context);
        }

        return false;
    }

    private Boolean equalCondition(Context context){
        if(CheckFunctions.isNumericValue(property.getType()) && CheckFunctions.isNumericValue(value.getType())){
            return Objects.equals(ParseFunctions.parseNumericTypeToFloat(property.getValue(context.getMainEntityInstance(), context.getSecondEntityInstance())),
                    ParseFunctions.parseNumericTypeToFloat(value.getValue(context.getMainEntityInstance(), context.getSecondEntityInstance())));
        } else if (property.getType() == PropertyType.BOOLEAN && value.getType() == PropertyType.BOOLEAN) {
            return ((Boolean)property.getValue(context.getMainEntityInstance(), context.getSecondEntityInstance()) == (Boolean)value.getValue(context.getMainEntityInstance(), context.getSecondEntityInstance()));
        } else if (property.getType() == PropertyType.STRING && value.getType() == PropertyType.STRING) {
            String propValue = (String)property.getValue(context.getMainEntityInstance(), context.getSecondEntityInstance());
            return (propValue.equals(value.getValue(context.getMainEntityInstance(), context.getSecondEntityInstance()).toString()));
        } else{
            throw new MissMatchValuesException("Condition failed - cannot check if " + property.getType() +
                    " type equals to " + value.getType() + " type.\n" +
                    "    Entity name - " + getMainEntityName() + "\n    Expression - " + property.getValue(context.getMainEntityInstance(), context.getSecondEntityInstance()));
        }
    }

    private Boolean notEqualCondition(Context context){
        return !equalCondition(context);
    }

    private Boolean btCondition(Context context){
        if(!CheckFunctions.isNumericValue(property.getType()) || !CheckFunctions.isNumericValue(value.getType())){
            throw new MissMatchValuesException("Condition failed - cannot check if " + property.getType() +
                    " type bigger than " + value.getType() + " type.\n" +
                    "    Entity name - " + getMainEntityName() + "\n    Expression - " + property.getValue(context.getMainEntityInstance(), context.getSecondEntityInstance()));
        }
        Float floatPropertyVal = ParseFunctions.parseNumericTypeToFloat(property.getValue(context.getMainEntityInstance(), context.getSecondEntityInstance()));
        Float floatExpressionVal = ParseFunctions.parseNumericTypeToFloat(value.getValue(context.getMainEntityInstance(), context.getSecondEntityInstance()));

        return floatPropertyVal > floatExpressionVal;
    }

    private Boolean ltCondition(Context context){
        if(!CheckFunctions.isNumericValue(property.getType()) || !CheckFunctions.isNumericValue(value.getType())){
            throw new MissMatchValuesException("Condition failed - cannot check if " + property.getType() +
                    " type smaller than " + value.getType() + " type.\n" +
                    "    Entity name - " + getMainEntityName() + "\n    Expression - " + property.getValue(context.getMainEntityInstance(), context.getSecondEntityInstance()));
        }
        Float floatPropertyVal = ParseFunctions.parseNumericTypeToFloat(property.getValue(context.getMainEntityInstance(), context.getSecondEntityInstance()));
        Float floatExpressionVal = ParseFunctions.parseNumericTypeToFloat(value.getValue(context.getMainEntityInstance(), context.getSecondEntityInstance()));

        return floatPropertyVal < floatExpressionVal;
    }
}
