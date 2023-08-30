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
        if(type != ActionType.PROXIMITY && type != ActionType.REPLACE){
            validateAction(prdAction, entities, type);
        }
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
                action = createKillAction(prdAction, environmentDefinition, entities);
                break;
            case CONDITION:
                action = createCondition(prdAction, entities, environmentDefinition);
                break;
            case REPLACE:
                action = createReplace(prdAction, entities, environmentDefinition);
                break;
            case  PROXIMITY:
                action = createProximity(prdAction, entities, environmentDefinition);
                break;
        }

        return action;
    }

    private static Proximity createProximity(PRDAction prdAction, Map<String, EntityDefinition> entities, EnvironmentDefinition environmentDefinition) {
        if(!entities.containsKey(prdAction.getPRDBetween().getSourceEntity())) {
            throw new InvalidNameException(prdAction.getPRDBetween().getSourceEntity() + " source entity name not exist.\n" +
                    "    Action name: Proximity");
        }
        if(!entities.containsKey(prdAction.getPRDBetween().getTargetEntity())) {
            throw new InvalidNameException(prdAction.getPRDBetween().getTargetEntity() + " target entity name not exist.\n" +
                    "    Action name: Proximity");
        }
        return new Proximity(prdAction.getPRDBetween().getSourceEntity(), createSecondaryEntity(prdAction.getPRDSecondaryEntity(), entities, environmentDefinition),
                prdAction.getPRDBetween().getTargetEntity(),FactoryExpression.createExpression(prdAction.getPRDEnvDepth().getOf(), environmentDefinition,
                entities.get(prdAction.getPRDBetween().getSourceEntity()).getPropertiesOfAllPopulation(), entities),
                createActionList(prdAction.getPRDActions().getPRDAction(), entities, environmentDefinition));
    }

    private static Replace createReplace(PRDAction prdAction, Map<String, EntityDefinition> entities, EnvironmentDefinition environmentDefinition) {
        if(!entities.containsKey(prdAction.getKill())) {
            throw new InvalidNameException(prdAction.getKill() + " kill entity name not exist.\n" +
                    "    Action name: Replace");
        }
        if(!entities.containsKey(prdAction.getCreate())) {
            throw new InvalidNameException(prdAction.getCreate() + " create entity name not exist.\n" +
                    "    Action name: Replace");
        }
       return new Replace(prdAction.getKill(), prdAction.getCreate(), createReplaceMode(prdAction.getMode()),
               createSecondaryEntity(prdAction.getPRDSecondaryEntity(), entities, environmentDefinition));
    }

    private static ReplaceMode createReplaceMode(String mode) {
        return Enum.valueOf(ReplaceMode.class, mode.toUpperCase());
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
                entities.get(prdAction.getEntity()).getPropertiesOfAllPopulation(), entities);
        validateExpressionNumeric(expression, prdAction.getType());

        return new Increase(prdAction.getEntity(), prdAction.getProperty(), expression,
                createSecondaryEntity(prdAction.getPRDSecondaryEntity(), entities, environmentDefinition));
    }

    private static Decrease createDecreaseAction(PRDAction prdAction, EnvironmentDefinition environmentDefinition,
                                                 Map<String, EntityDefinition> entities){
        Expression expression = FactoryExpression.createExpression(prdAction.getBy(), environmentDefinition,
                entities.get(prdAction.getEntity()).getPropertiesOfAllPopulation(), entities);
        validateExpressionNumeric(expression, prdAction.getType());

        return new Decrease(prdAction.getEntity(), prdAction.getProperty(), expression,
                createSecondaryEntity(prdAction.getPRDSecondaryEntity(), entities, environmentDefinition));
    }

    private static Calculation createCalculationAction(PRDAction prdAction, EnvironmentDefinition environmentDefinition,
                                                       Map<String, EntityDefinition> entities){
        Arithmetics arithmetics;
        Expression arg1;
        Expression arg2;

        if(prdAction.getPRDMultiply() != null){
            arithmetics = Arithmetics.MULTIPLY;
            arg1 = FactoryExpression.createExpression(prdAction.getPRDMultiply().getArg1(), environmentDefinition,
                    entities.get(prdAction.getEntity()).getPropertiesOfAllPopulation(), entities);
            arg2 = FactoryExpression.createExpression(prdAction.getPRDMultiply().getArg2(), environmentDefinition,
                    entities.get(prdAction.getEntity()).getPropertiesOfAllPopulation(), entities);
        }
        else{
            arithmetics = Arithmetics.DIVIDE;
            arg1 = FactoryExpression.createExpression(prdAction.getPRDDivide().getArg1(), environmentDefinition,
                    entities.get(prdAction.getEntity()).getPropertiesOfAllPopulation(), entities);
            arg2 = FactoryExpression.createExpression(prdAction.getPRDDivide().getArg2(), environmentDefinition,
                    entities.get(prdAction.getEntity()).getPropertiesOfAllPopulation(), entities);
        }
        validateExpressionNumeric(arg1, prdAction.getType());
        validateExpressionNumeric(arg2, prdAction.getType());

        return new Calculation(prdAction.getEntity(), prdAction.getResultProp(), arg1, arg2, arithmetics,
                createSecondaryEntity(prdAction.getPRDSecondaryEntity(), entities, environmentDefinition));
    }

    private static Kill createKillAction(PRDAction prdAction, EnvironmentDefinition environmentDefinition,
                                         Map<String, EntityDefinition> entities){
        return new Kill(prdAction.getEntity(), createSecondaryEntity(prdAction.getPRDSecondaryEntity(), entities, environmentDefinition));
    }

    private static Set createSetAction(PRDAction prdAction, EnvironmentDefinition environmentDefinition,
                                       Map<String, EntityDefinition> entities){
        return new Set(prdAction.getEntity(), prdAction.getProperty(),
                FactoryExpression.createExpression(prdAction.getValue(), environmentDefinition,
                        entities.get(prdAction.getEntity()).getPropertiesOfAllPopulation(), entities),
                createSecondaryEntity(prdAction.getPRDSecondaryEntity(), entities, environmentDefinition));
    }

    private static Condition createCondition(PRDAction prdAction, Map<String, EntityDefinition> entities,
                                             EnvironmentDefinition environmentDefinition){
        Condition condition = createConditionHelper(prdAction.getPRDCondition(), prdAction.getEntity(),
                environmentDefinition, entities);
        condition.setSecondaryEntity(createSecondaryEntity(prdAction.getPRDSecondaryEntity(), entities, environmentDefinition));

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
        return new SingleCondition(prdCondition.getEntity(),
                FactoryExpression.createExpression(prdCondition.getProperty(), environmentDefinition, entities.get(prdCondition.getEntity()).getPropertiesOfAllPopulation(), entities),
                FactoryExpression.createExpression(prdCondition.getValue(), environmentDefinition, entities.get(prdCondition.getEntity()).getPropertiesOfAllPopulation(), entities),
                createOperator(prdCondition.getOperator()),
                createSecondaryEntity(null, entities, environmentDefinition));
    }

    private static void validateSingleCondition(PRDCondition prdCondition, Map<String, EntityDefinition> entities){
        if(!entities.containsKey(prdCondition.getEntity())) {
            throw new InvalidNameException(prdCondition.getEntity() + " entity name not exist.\n" +
                    "    Action name: Condition");
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

        return new MultipleCondition(entityName, conditionList, createLogical(prdCondition.getLogical()), null);
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

    private static SecondaryEntity createSecondaryEntity(PRDAction.PRDSecondaryEntity prdSecondaryEntity, Map<String, EntityDefinition> entities,
                                                         EnvironmentDefinition environmentDefinition){
        if (prdSecondaryEntity != null){
            if(prdSecondaryEntity.getPRDSelection().getPRDCondition() != null){
                return new SecondaryEntity(prdSecondaryEntity.getEntity(), prdSecondaryEntity.getPRDSelection().getCount(),
                        createConditionHelper(prdSecondaryEntity.getPRDSelection().getPRDCondition(),
                        prdSecondaryEntity.getPRDSelection().getPRDCondition().getEntity(), environmentDefinition, entities));
            } else{
               return new SecondaryEntity(prdSecondaryEntity.getEntity(), prdSecondaryEntity.getPRDSelection().getCount(), null) ;
            }
        } else{
            return null;
        }
    }
}
