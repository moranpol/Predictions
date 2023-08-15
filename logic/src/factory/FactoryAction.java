package factory;

import entity.EntityDefinition;
import enums.ActionType;
import enums.Arithmetics;
import enums.PropertyType;
import exceptions.InvalidNameException;
import exceptions.MissMatchValuesException;
import jaxb.schema.generated.PRDAction;
import rule.action.*;
import rule.action.expression.Expression;

import java.util.Map;
import java.util.Objects;

public abstract class FactoryAction {

    public static Action createAction(PRDAction prdAction, Map<String, EntityDefinition> entities, String ruleName){
        ActionType type = createActionType(prdAction.getType());
        validateAction(prdAction, entities, ruleName, type);
        Action action = null;

        switch (type){
            case INCREASE:

        }


        return null;
    }

    private static void validateAction(PRDAction prdAction, Map<String, EntityDefinition> entities, String ruleName, ActionType type){
        if(!entities.containsKey(prdAction.getEntity())) {
            throw new InvalidNameException(prdAction.getEntity() + " entity name not exist - action name: "
                    + prdAction.getType() + " in rule: " + ruleName);
        }
        if(prdAction.getProperty() != null) {
            if (!entities.get(prdAction.getEntity()).getPropertiesOfAllPopulation().containsKey(prdAction.getProperty())) {
                throw new InvalidNameException(prdAction.getProperty() + " property name not exist in " +
                        prdAction.getEntity() + " entity name - action name: " + prdAction.getType() +
                        " in rule: " + ruleName);
            }
            if(isNumericAction(type) &&
                    !isNumericValue(entities.get(prdAction.getEntity()).getPropertiesOfAllPopulation().get(prdAction.getProperty()).getType())){
                throw new MissMatchValuesException("action " + prdAction.getType() + " in " + ruleName +
                        " is numeric action but property " + prdAction.getProperty() + " type is not numeric.");
            }
        }

    }

    private static ActionType createActionType(String type){
        return Enum.valueOf(ActionType.class, type);
    }

    private static Increase createIncreaseAction(PRDAction prdAction, String ruleName){
        Expression expression = null;
        //expression = FactoryExpression.createExpression();
        validateExpressionNumeric(expression, prdAction.getType(), ruleName);

        //todo
        return new Increase(prdAction.getEntity(), prdAction.getProperty(), expression);
    }

    private static Decrease createDecreaseAction(PRDAction prdAction, String ruleName){
        Expression expression = null;
        //expression = FactoryExpression.createExpression();
        validateExpressionNumeric(expression, prdAction.getType(), ruleName);
        //todo
        return new Decrease(prdAction.getEntity(), prdAction.getProperty(), expression);
    }

    private static Calculation createCalculationAction(PRDAction prdAction, String ruleName){
        Arithmetics arithmetics = null;
        Expression arg1 = null;
        Expression arg2 = null;

        //todo
        if(prdAction.getPRDMultiply() != null){
            arithmetics = Arithmetics.MULTIPLY;
            //arg1 = FactoryExpression.createExpression();
            //arg2 = FactoryExpression.createExpression();

        }
        else if(prdAction.getPRDMultiply() != null){
            arithmetics = Arithmetics.DIVIDE;
            //arg1 = FactoryExpression.createExpression();
            //arg2 = FactoryExpression.createExpression();
        }

        validateExpressionNumeric(arg1, prdAction.getType(), ruleName);
        validateExpressionNumeric(arg2, prdAction.getType(), ruleName);

        return new Calculation(prdAction.getEntity(), prdAction.getProperty(), arg1, arg2, arithmetics);
    }

    private static Kill createKillAction(PRDAction prdAction){
        return new Kill(prdAction.getEntity());
    }

    private static Set createSetAction(PRDAction prdAction){
        //todo - FactoryExpression.createExpression()
        return new Set(prdAction.getEntity(), prdAction.getProperty(), null);
    }

    private static Condition createCondition(PRDAction prdAction){

    }

    private static void validateExpressionNumeric(Expression expression, String actionName, String ruleName){
        //if(!isNumericValue(expression.getType()){
        //    throw new MissMatchValuesException("action " + actionName + " in " + ruleName +
        //            " is numeric action but expression type is not numeric.");
        //}
    }

    private static boolean isNumericValue(PropertyType type){
        return (type == PropertyType.DECIMAL || type == PropertyType.FLOAT);
    }

    private static boolean isNumericAction(ActionType type){
        return (type == ActionType.INCREASE || type == ActionType.DECREASE || type == ActionType.CALCULATION);
    }
}
