package factory;

import entity.EntityDefinition;
import enums.*;
import environment.EnvironmentDefinition;
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

    public static List<Action> createActionList(List<PRDAction> prdActionList, Map<String, EntityDefinition> entities,
                                                String ruleName, EnvironmentDefinition environmentDefinition){
        List<Action> actionList = new ArrayList<>();
        for (PRDAction action : prdActionList) {
            actionList.add(createAction(action, entities, ruleName, environmentDefinition));
        }

        return actionList;
    }

    private static Action createAction(PRDAction prdAction, Map<String, EntityDefinition> entities, String ruleName,
                                       EnvironmentDefinition environmentDefinition){
        ActionType type = createActionType(prdAction.getType());
        validateAction(prdAction, entities, ruleName, type);
        Action action = null;
        switch (type){
            case INCREASE:
                action = createIncreaseAction(prdAction, ruleName, environmentDefinition, entities);
                break;
            case DECREASE:
                action = createDecreaseAction(prdAction, ruleName, environmentDefinition, entities);
                break;
            case CALCULATION:
                action = createCalculationAction(prdAction, ruleName, environmentDefinition, entities);
                break;
            case SET:
                action = createSetAction(prdAction, environmentDefinition, entities);
                break;
            case KILL:
                action = createKillAction(prdAction);
                break;
            case CONDITION:
                action = createCondition(prdAction, entities, ruleName, environmentDefinition);
                break;
        }

        return action;
    }

    private static void validateAction(PRDAction prdAction, Map<String, EntityDefinition> entities, String ruleName,
                                       ActionType type){
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

    private static Increase createIncreaseAction(PRDAction prdAction, String ruleName,EnvironmentDefinition environmentDefinition,
                                                 Map<String, EntityDefinition> entities){
        Expression expression = FactoryExpression.createExpression(prdAction.getBy(), environmentDefinition,
                entities.get(prdAction.getEntity()).getPropertiesOfAllPopulation());
        validateExpressionNumeric(expression, prdAction.getType(), ruleName);

        return new Increase(prdAction.getEntity(), prdAction.getProperty(), expression);
    }

    private static Decrease createDecreaseAction(PRDAction prdAction, String ruleName, EnvironmentDefinition environmentDefinition,
                                                 Map<String, EntityDefinition> entities){
        Expression expression = FactoryExpression.createExpression(prdAction.getBy(), environmentDefinition,
                entities.get(prdAction.getEntity()).getPropertiesOfAllPopulation());
        validateExpressionNumeric(expression, prdAction.getType(), ruleName);

        return new Decrease(prdAction.getEntity(), prdAction.getProperty(), expression);
    }

    private static Calculation createCalculationAction(PRDAction prdAction, String ruleName, EnvironmentDefinition environmentDefinition,
                                                       Map<String, EntityDefinition> entities){
        Arithmetics arithmetics = null;
        Expression arg1 = null;
        Expression arg2 = null;

        if(prdAction.getPRDMultiply() != null){
            arithmetics = Arithmetics.MULTIPLY;
            arg1 = FactoryExpression.createExpression(prdAction.getPRDMultiply().getArg1(), environmentDefinition,
                    entities.get(prdAction.getEntity()).getPropertiesOfAllPopulation());
            arg2 = FactoryExpression.createExpression(prdAction.getPRDMultiply().getArg2(), environmentDefinition,
                    entities.get(prdAction.getEntity()).getPropertiesOfAllPopulation());
        }
        else{
            arithmetics = Arithmetics.DIVIDE;
            arg1 = FactoryExpression.createExpression(prdAction.getPRDDivide().getArg1(), environmentDefinition,
                    entities.get(prdAction.getEntity()).getPropertiesOfAllPopulation());
            arg2 = FactoryExpression.createExpression(prdAction.getPRDDivide().getArg2(), environmentDefinition,
                    entities.get(prdAction.getEntity()).getPropertiesOfAllPopulation());
        }
        validateExpressionNumeric(arg1, prdAction.getType(), ruleName);
        validateExpressionNumeric(arg2, prdAction.getType(), ruleName);

        return new Calculation(prdAction.getEntity(), prdAction.getProperty(), arg1, arg2, arithmetics);
    }

    private static Kill createKillAction(PRDAction prdAction){
        return new Kill(prdAction.getEntity());
    }

    private static Set createSetAction(PRDAction prdAction, EnvironmentDefinition environmentDefinition,
                                       Map<String, EntityDefinition> entities){
        return new Set(prdAction.getEntity(), prdAction.getProperty(),
                FactoryExpression.createExpression(prdAction.getValue(), environmentDefinition,
                        entities.get(prdAction.getEntity()).getPropertiesOfAllPopulation()));
    }

    private static Condition createCondition(PRDAction prdAction, Map<String, EntityDefinition> entities,
                                             String ruleName, EnvironmentDefinition environmentDefinition){
        Condition condition = createConditionHelper(prdAction.getPRDCondition(), prdAction.getEntity(),
                environmentDefinition, entities);

        if(prdAction.getPRDElse() != null){
            condition.setElseActions(createActionList(prdAction.getPRDElse().getPRDAction(), entities, ruleName, environmentDefinition));
        }
        condition.setThenActions(createActionList(prdAction.getPRDThen().getPRDAction(), entities, ruleName, environmentDefinition));

        return condition;
    }

    private static Condition createConditionHelper(PRDCondition prdCondition, String entityName, EnvironmentDefinition environmentDefinition,
                                                   Map<String, EntityDefinition> entities){
        Condition condition = null;

        switch (prdCondition.getSingularity()){
            case "multiple":
                condition = createMultipleCondition(prdCondition, entityName, environmentDefinition, entities);
                break;
            case "single":
                condition = createSingleCondition(prdCondition, environmentDefinition, entities);
        }

        return condition;
    }

    private static SingleCondition createSingleCondition(PRDCondition prdCondition, EnvironmentDefinition environmentDefinition,
                                                         Map<String, EntityDefinition> entities) {
        return new SingleCondition(prdCondition.getEntity(), prdCondition.getProperty(),
                FactoryExpression.createExpression(prdCondition.getValue(), environmentDefinition, entities.get(prdCondition.getEntity()).getPropertiesOfAllPopulation()),
                createOperator(prdCondition.getOperator()));
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

    private static MultipleCondition createMultipleCondition(PRDCondition prdCondition, String entityName, EnvironmentDefinition environmentDefinition,
                                                             Map<String, EntityDefinition> entities) {
        List<Condition> conditionList = new ArrayList<>();
        for (PRDCondition condition : prdCondition.getPRDCondition()) {
            conditionList.add(createConditionHelper(condition, entityName, environmentDefinition, entities));
        }

        return new MultipleCondition(entityName, conditionList, createLogical(prdCondition.getLogical()));
    }

    private static Logicals createLogical(String logical){
        return Enum.valueOf(Logicals.class, logical.toUpperCase());
    }

    private static void validateExpressionNumeric(Expression expression, String actionName, String ruleName){
        if(!isNumericValue(expression.getType())){
            throw new MissMatchValuesException("action " + actionName + " in " + ruleName +
                    " is numeric action but expression type is not numeric.");
        }
    }

    private static Boolean isNumericValue(PropertyType type){
        return (type == PropertyType.DECIMAL || type == PropertyType.FLOAT);
    }

    private static Boolean isNumericAction(ActionType type){
        return (type == ActionType.INCREASE || type == ActionType.DECREASE || type == ActionType.CALCULATION);
    }
}
