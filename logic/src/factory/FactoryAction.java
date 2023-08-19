package factory;

import entity.EntityDefinition;
import enums.*;
import environment.EnvironmentDefinition;
import exceptions.InvalidNameException;
import exceptions.MissMatchValuesException;
import helpers.CheckFunctions;
import jaxb.schema.generated.PRDAction;
import jaxb.schema.generated.PRDCondition;
import rule.action.*;
import rule.action.expression.Expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class FactoryAction {

    public static List<Action> createActionList(List<PRDAction> prdActionList, Map<String, EntityDefinition> entities,
                                                EnvironmentDefinition environmentDefinition){
        List<Action> actionList = new ArrayList<>();
        for (PRDAction action : prdActionList) {
            actionList.add(createAction(action, entities, environmentDefinition));
        }

        return actionList;
    }

    private static Action createAction(PRDAction prdAction, Map<String, EntityDefinition> entities, EnvironmentDefinition environmentDefinition){
        ActionType type = createActionType(prdAction.getType());
        validateAction(prdAction, entities, type);
        Action action = null;
        switch (type){
            case INCREASE:
                action = createIncreaseAction(prdAction, environmentDefinition, entities);
                break;
            case DECREASE:
                action = createDecreaseAction(prdAction, environmentDefinition, entities);
                break;
            case CALCULATION:
                action = createCalculationAction(prdAction, environmentDefinition, entities);
                break;
            case SET:
                action = createSetAction(prdAction, environmentDefinition, entities);
                break;
            case KILL:
                action = createKillAction(prdAction);
                break;
            case CONDITION:
                action = createCondition(prdAction, entities, environmentDefinition);
                break;
        }

        return action;
    }

    private static void validateAction(PRDAction prdAction, Map<String, EntityDefinition> entities, ActionType type){
        if(!entities.containsKey(prdAction.getEntity())) {
            throw new InvalidNameException(prdAction.getEntity() + " entity name not exist.\n" +
                    "    Action name: " + prdAction.getType());
        }
        if(prdAction.getProperty() != null) {
            if (!entities.get(prdAction.getEntity()).getPropertiesOfAllPopulation().containsKey(prdAction.getProperty())) {
                throw new InvalidNameException(prdAction.getProperty() + " property name not exist in " +
                        prdAction.getEntity() + " entity name.\n    Action name: " + prdAction.getType());
            }
            if(CheckFunctions.isNumericAction(type) &&
                    !CheckFunctions.isNumericValue(entities.get(prdAction.getEntity()).getPropertiesOfAllPopulation().get(prdAction.getProperty()).getType())){
                throw new MissMatchValuesException("action " + prdAction.getType() +
                        " is numeric, but property " + prdAction.getProperty() + " type is not numeric.");
            }
        }

    }

    private static ActionType createActionType(String type){
        return Enum.valueOf(ActionType.class, type.toUpperCase());
    }

    private static Increase createIncreaseAction(PRDAction prdAction, EnvironmentDefinition environmentDefinition,
                                                 Map<String, EntityDefinition> entities){
        Expression expression = FactoryExpression.createExpression(prdAction.getBy(), environmentDefinition,
                entities.get(prdAction.getEntity()).getPropertiesOfAllPopulation());
        validateExpressionNumeric(expression, prdAction.getType());

        return new Increase(prdAction.getEntity(), prdAction.getProperty(), expression);
    }

    private static Decrease createDecreaseAction(PRDAction prdAction, EnvironmentDefinition environmentDefinition,
                                                 Map<String, EntityDefinition> entities){
        Expression expression = FactoryExpression.createExpression(prdAction.getBy(), environmentDefinition,
                entities.get(prdAction.getEntity()).getPropertiesOfAllPopulation());
        validateExpressionNumeric(expression, prdAction.getType());

        return new Decrease(prdAction.getEntity(), prdAction.getProperty(), expression);
    }

    private static Calculation createCalculationAction(PRDAction prdAction, EnvironmentDefinition environmentDefinition,
                                                       Map<String, EntityDefinition> entities){
        Arithmetics arithmetics;
        Expression arg1;
        Expression arg2;

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
        validateExpressionNumeric(arg1, prdAction.getType());
        validateExpressionNumeric(arg2, prdAction.getType());

        return new Calculation(prdAction.getEntity(), prdAction.getResultProp(), arg1, arg2, arithmetics);
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
                                             EnvironmentDefinition environmentDefinition){
        Condition condition = createConditionHelper(prdAction.getPRDCondition(), prdAction.getEntity(),
                environmentDefinition, entities);

        if(prdAction.getPRDElse() != null){
            condition.setElseActions(createActionList(prdAction.getPRDElse().getPRDAction(), entities, environmentDefinition));
        }
        condition.setThenActions(createActionList(prdAction.getPRDThen().getPRDAction(), entities, environmentDefinition));

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
        validateSingleCondition(prdCondition, entities);
        return new SingleCondition(prdCondition.getEntity(), prdCondition.getProperty(),
                FactoryExpression.createExpression(prdCondition.getValue(), environmentDefinition, entities.get(prdCondition.getEntity()).getPropertiesOfAllPopulation()),
                createOperator(prdCondition.getOperator()));
    }

    private static void validateSingleCondition(PRDCondition prdCondition, Map<String, EntityDefinition> entities){
        if(!entities.containsKey(prdCondition.getEntity())) {
            throw new InvalidNameException(prdCondition.getEntity() + " entity name not exist.\n" +
                    "    Action name: Condition");
        }
        if (!entities.get(prdCondition.getEntity()).getPropertiesOfAllPopulation().containsKey(prdCondition.getProperty())) {
            throw new InvalidNameException(prdCondition.getProperty() + " property name not exist in " +
                    prdCondition.getEntity() + " entity name.\n    Action name: Condition");
        }
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

    private static void validateExpressionNumeric(Expression expression, String actionName){
        if(!CheckFunctions.isNumericValue(expression.getType())){
            throw new MissMatchValuesException("action " + actionName +
                    " is numeric action but expression type isn't numeric.");
        }
    }
}
