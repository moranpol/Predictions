package rule.action;

import entity.EntityInstance;
import enums.Operators;
import enums.PropertyType;
import exceptions.MissMatchValuesException;
import factory.FactoryAction;
import factory.FactoryDefinition;
import property.PropertyInstance;
import rule.action.expression.Expression;

import java.util.List;
import java.util.Map;

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
        Integer propertyVal;
        Integer ExpressionVal;
        if(property.getType() == PropertyType.DECIMAL && value.getType() == PropertyType.DECIMAL){
            return (Integer)property.getValue() == (Integer)value.getValue();
        } else if (property.getType() == PropertyType.DECIMAL && value.getType() == PropertyType.FLOAT) {
            propertyVal = (Integer)property.getValue();
            return propertyVal.floatValue() == (Float)value.getValue();
        } else if (property.getType() == PropertyType.FLOAT && value.getType() == PropertyType.DECIMAL) {
            ExpressionVal = (Integer)value.getValue();
            return (Float)property.getValue() == ExpressionVal.floatValue();
        } else if (property.getType() == PropertyType.FLOAT && value.getType() == PropertyType.FLOAT) {
            return (Float)property.getValue() == (Float)value.getValue();
        } else if (property.getType() == PropertyType.BOOLEAN && value.getType() == PropertyType.BOOLEAN) {
            return ((Boolean)property.getValue() == (Boolean)value.getValue());
        } else if (property.getType() == PropertyType.STRING && value.getType() == PropertyType.STRING) {
            String propValue = (String)property.getValue();
            return (propValue.equals((String)value.getValue()));
        }
        return false;
    }

    private Boolean notEqualCondition(PropertyInstance property){
        return !equalCondition(property);
    }

    private Boolean btCondition(PropertyInstance property){
        if(!isNumericValue(property.getType()) && !isNumericValue(value.getType())){
            throw new MissMatchValuesException("Condition failed - cannot check if " + property.getType() +
                    " type bigger than " + value.getType() + " type.\n" +
                    "Entity name - " + getEntityName() + "\nProperty name - " + propertyName);
        }

        Float floatPropertyVal = parseFloat(property.getValue(), property.getType());
        Float floatExpressionVal = parseFloat(value.getValue(), value.getType());

        return floatPropertyVal > floatExpressionVal;
    }

    private Boolean ltCondition(PropertyInstance property){
        Integer propertyVal;
        Integer ExpressionVal;
        if(property.getType() == PropertyType.DECIMAL && value.getType() == PropertyType.DECIMAL){
            return (Integer)property.getValue() < (Integer)value.getValue();
        } else if (property.getType() == PropertyType.DECIMAL && value.getType() == PropertyType.FLOAT) {
            propertyVal = (Integer)property.getValue();
            return propertyVal.floatValue() < (Float)value.getValue();
        } else if (property.getType() == PropertyType.FLOAT && value.getType() == PropertyType.DECIMAL) {
            ExpressionVal = (Integer)value.getValue();
            return (Float)property.getValue() < ExpressionVal.floatValue();
        } else if (property.getType() == PropertyType.FLOAT && value.getType() == PropertyType.FLOAT) {
            return (Float)property.getValue() < (Float)value.getValue();
        } else{
            throw new MissMatchValuesException("Condition failed - cannot check if " + property.getType() +
                    " type smaller than " + value.getType() + " type.\n" +
                    "Entity name - " + getEntityName() + "\nProperty name - " + propertyName);
        }
    }

    private Boolean isNumericValue(PropertyType type){
        return (type == PropertyType.DECIMAL || type == PropertyType.FLOAT);
    }

}
