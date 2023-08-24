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
        setExpressionEntityInstance(value, entity);
        switch (operator){
            case EQUAL:
                return equalCondition(entity.getProperties().get(propertyName));
            case NOTEQUAL:
                return notEqualCondition(entity.getProperties().get(propertyName));
            case BT:
                return btCondition(entity.getProperties().get(propertyName));
            case LT:
                return ltCondition(entity.getProperties().get(propertyName));
        }

        return false;
    }

    private Boolean equalCondition(PropertyInstance property){
        if(CheckFunctions.isNumericValue(property.getType()) && CheckFunctions.isNumericValue(value.getType())){
            return Objects.equals(ParseFunctions.parseNumericTypeToFloat(property.getValue()),
                    ParseFunctions.parseNumericTypeToFloat(value.getValue()));
        } else if (property.getType() == PropertyType.BOOLEAN && value.getType() == PropertyType.BOOLEAN) {
            return ((Boolean)property.getValue() == (Boolean)value.getValue());
        } else if (property.getType() == PropertyType.STRING && value.getType() == PropertyType.STRING) {
            String propValue = (String)property.getValue();
            return (propValue.equals(value.getValue().toString()));
        } else{
            throw new MissMatchValuesException("Condition failed - cannot check if " + property.getType() +
                    " type equals to " + value.getType() + " type.\n" +
                    "    Entity name - " + getEntityName() + "\n    Property name - " + propertyName);
        }
    }

    private Boolean notEqualCondition(PropertyInstance property){
        return !equalCondition(property);
    }

    private Boolean btCondition(PropertyInstance property){
        if(!CheckFunctions.isNumericValue(property.getType()) || !CheckFunctions.isNumericValue(value.getType())){
            throw new MissMatchValuesException("Condition failed - cannot check if " + property.getType() +
                    " type bigger than " + value.getType() + " type.\n" +
                    "    Entity name - " + getEntityName() + "\n    Property name - " + propertyName);
        }
        Float floatPropertyVal = ParseFunctions.parseNumericTypeToFloat(property.getValue());
        Float floatExpressionVal = ParseFunctions.parseNumericTypeToFloat(value.getValue());

        return floatPropertyVal > floatExpressionVal;
    }

    private Boolean ltCondition(PropertyInstance property){
        if(!CheckFunctions.isNumericValue(property.getType()) || !CheckFunctions.isNumericValue(value.getType())){
            throw new MissMatchValuesException("Condition failed - cannot check if " + property.getType() +
                    " type smaller than " + value.getType() + " type.\n" +
                    "    Entity name - " + getEntityName() + "\n    Property name - " + propertyName);
        }
        Float floatPropertyVal = ParseFunctions.parseNumericTypeToFloat(property.getValue());
        Float floatExpressionVal = ParseFunctions.parseNumericTypeToFloat(value.getValue());

        return floatPropertyVal < floatExpressionVal;
    }
}
