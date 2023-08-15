package factory;

import entity.EntityDefinition;
import enums.*;
import exceptions.InvalidNameException;
import exceptions.MissMatchValuesException;
import jaxb.schema.generated.PRDAction;
import jaxb.schema.generated.PRDCondition;
import rule.action.*;
import rule.action.expression.Expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class FactoryAction {

    public static List<Action> createActionList(List<PRDAction> prdActionList, Map<String, EntityDefinition> entities, String ruleName){
        List<Action> actionList = new ArrayList<>();
        for (PRDAction action : prdActionList) {
            actionList.add(createAction(action, entities, ruleName));
        }

        return actionList;
    }

    private static Action createAction(PRDAction prdAction, Map<String, EntityDefinition> entities, String ruleName){
        ActionType type = createActionType(prdAction.getType());
        validateAction(prdAction, entities, ruleName, type);
        Action action = null;
        switch (type){
            case INCREASE:
                action = createIncreaseAction(prdAction, ruleName);
                break;
            case DECREASE:
                action = createDecreaseAction(prdAction, ruleName);
                break;
            case CALCULATION:
                action = createCalculationAction(prdAction, ruleName);
                break;
            case SET:
                action = createSetAction(prdAction);
                break;
            case KILL:
                action = createKillAction(prdAction);
                break;
            case CONDITION:
                action = createCondition(prdAction, entities, ruleName);
                break;
        }

        return action;
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
        return Enum.valueOf(ActionType.class, type.toUpperCase());
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
        else{
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

    private static Condition createCondition(PRDAction prdAction, Map<String, EntityDefinition> entities, String ruleName){
        Condition condition = createConditionHelper(prdAction.getPRDCondition());

        if(prdAction.getPRDElse() != null){
            condition.setElseActions(createActionList(prdAction.getPRDElse().getPRDAction(), entities, ruleName));
        }
        condition.setThenActions(createActionList(prdAction.getPRDThen().getPRDAction(), entities, ruleName));

        return condition;
    }

    private static Condition createConditionHelper(PRDCondition prdCondition){
        Condition condition = null;

        switch (prdCondition.getSingularity()){
            case "multiple":
                condition = createMultipleCondition(prdCondition);
                break;
            case "single":
                condition = createSingleCondition(prdCondition);
        }

        return condition;
    }

    private static SingleCondition createSingleCondition(PRDCondition prdCondition) {
        //todo - FactoryExpression.createExpression()
        return new SingleCondition(prdCondition.getEntity(), prdCondition.getProperty(),
                null, createOperator(prdCondition.getOperator()));
    }

    private static Operators createOperator(String operator){
        Operators operatorType = null;
        switch (operator){
            case "=":
                operatorType = Operators.EQUAL;
                break;
            case "!=":
                operatorType = Operators.NOTEQUAL;
                break;
            case "bt":
                operatorType = Operators.BT;
                break;
            case "lt":
                operatorType = Operators.LT;
                break;
        }

        return operatorType;
    }

    private static MultipleCondition createMultipleCondition(PRDCondition prdCondition) {
        List<Condition> conditionList = new ArrayList<>();
        for (PRDCondition condition : prdCondition.getPRDCondition()) {
            conditionList.add(createConditionHelper(condition));
        }

        return new MultipleCondition(null ,conditionList, createLogical(prdCondition.getLogical()));
    }

    private static Logicals createLogical(String logical){
        return Enum.valueOf(Logicals.class, logical.toUpperCase());
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
