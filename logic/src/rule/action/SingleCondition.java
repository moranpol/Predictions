package rule.action;

import entity.EntityInstance;
import enums.Operators;
import enums.PropertyType;
import exceptions.MissMatchValuesException;
import exceptions.ParseFailedException;
import helpers.CheckFunctions;
import helpers.ParseFunctions;
import property.PropertyInstance;
import rule.action.expression.Expression;

import java.util.Objects;

public class SingleCondition extends Condition{
    private final String propertyName;
    private final Expression value;
    private final Operators operator;

    public SingleCondition(String entityName, String propertyName, Expression value, Operators operator) {
        super(entityName);
        this.propertyName = propertyName;
        this.value = value;
        this.operator = operator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SingleCondition that = (SingleCondition) o;
        return Objects.equals(propertyName, that.propertyName) && Objects.equals(value, that.value) && operator == that.operator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), propertyName, value, operator);
    }

    @Override
    public Boolean invokeCondition(EntityInstance entity) {
        switch (operator){
            case EQUAL:
                return equalCondition(entity);
            case NOTEQUAL:
                return notEqualCondition(entity);
            case BT:
                return btCondition(entity);
            case LT:
                return ltCondition(entity);
        }

        return false;
    }

    private Boolean equalCondition(EntityInstance entity){
        if(CheckFunctions.isNumericValue(entity.getProperties().get(propertyName).getType()) && CheckFunctions.isNumericValue(value.getType())){
            return Objects.equals(ParseFunctions.parseNumericTypeToFloat(entity.getProperties().get(propertyName).getValue()),
                    ParseFunctions.parseNumericTypeToFloat(value.getValue(entity)));
        } else if (entity.getProperties().get(propertyName).getType() == PropertyType.BOOLEAN && value.getType() == PropertyType.BOOLEAN) {
            return ((Boolean)entity.getProperties().get(propertyName).getValue() == (Boolean)value.getValue(entity));
        } else if (entity.getProperties().get(propertyName).getType() == PropertyType.STRING && value.getType() == PropertyType.STRING) {
            String propValue = (String)entity.getProperties().get(propertyName).getValue();
            return (propValue.equals(value.getValue(entity).toString()));
        } else{
            throw new MissMatchValuesException("Condition failed - cannot check if " + entity.getProperties().get(propertyName).getType() +
                    " type equals to " + value.getType() + " type.\n" +
                    "    Entity name - " + getEntityName() + "\n    Property name - " + propertyName);
        }
    }

    private Boolean notEqualCondition(EntityInstance entity){
        return !equalCondition(entity);
    }

    private Boolean btCondition(EntityInstance entity){
        if(!CheckFunctions.isNumericValue(entity.getProperties().get(propertyName).getType()) || !CheckFunctions.isNumericValue(value.getType())){
            throw new MissMatchValuesException("Condition failed - cannot check if " + entity.getProperties().get(propertyName).getType() +
                    " type bigger than " + value.getType() + " type.\n" +
                    "    Entity name - " + getEntityName() + "\n    Property name - " + propertyName);
        }
        Float floatPropertyVal = ParseFunctions.parseNumericTypeToFloat(entity.getProperties().get(propertyName).getValue());
        Float floatExpressionVal = ParseFunctions.parseNumericTypeToFloat(value.getValue(entity));

        return floatPropertyVal > floatExpressionVal;
    }

    private Boolean ltCondition(EntityInstance entity){
        if(!CheckFunctions.isNumericValue(entity.getProperties().get(propertyName).getType()) || !CheckFunctions.isNumericValue(value.getType())){
            throw new MissMatchValuesException("Condition failed - cannot check if " + entity.getProperties().get(propertyName).getType() +
                    " type smaller than " + value.getType() + " type.\n" +
                    "    Entity name - " + getEntityName() + "\n    Property name - " + propertyName);
        }
        Float floatPropertyVal = ParseFunctions.parseNumericTypeToFloat(entity.getProperties().get(propertyName).getValue());
        Float floatExpressionVal = ParseFunctions.parseNumericTypeToFloat(value.getValue(entity));

        return floatPropertyVal < floatExpressionVal;
    }
}
