package rule.action.expression.helper;

import entity.EntityDefinition;
import entity.EntityInstance;
import enums.PropertyType;
import exceptions.ParseFailedException;
import factory.FactoryExpression;
import property.PropertyDefinition;
import rule.action.expression.Expression;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Helper implements Expression, Serializable {
    private final String funcName;
    private final ArrayList<String> variables;
    private final HelperFunction helperFunction;
    private final Map<String, EntityDefinition> entities;
    private final String expressionString;

    public Helper(HelperFunction helperFunction, Map<String, EntityDefinition> entities, String expressionString, String... strings) {
        this.expressionString = expressionString;
        this.funcName = strings[0];
        variables = new ArrayList<>();
        variables.addAll(Arrays.asList(strings).subList(1, strings.length));
        this.helperFunction = helperFunction;
        this.entities = entities;
    }

    @Override
    public Object getValue(EntityInstance mainEntityInstance, EntityInstance secondEntityInstance) {
        helperFunction.setMainEntityInstance(mainEntityInstance);
        helperFunction.setSecondEntityInstance(secondEntityInstance);
        switch (this.funcName){
            case "environment":
                return helperFunction.environment(variables.get(0));
            case "random":
                try{
                    return helperFunction.random(Integer.parseInt(variables.get(0)));
                } catch (NumberFormatException e){
                    throw new ParseFailedException("random helper function failed", PropertyType.DECIMAL);
                }
            case "evaluate":
                return helperFunction.evaluate(variables.get(0));
            case "percent":
                EntityInstance entity = getEntityInstance(mainEntityInstance, secondEntityInstance, variables.get(0));
                Expression expression1 = FactoryExpression.createExpression(variables.get(0), helperFunction.getEnvironmentDefinition(),
                        entities.get(entity.getName()).getPropertiesOfAllPopulation(), entities);
                entity = getEntityInstance(mainEntityInstance, secondEntityInstance, variables.get(1));
                Expression expression2 = FactoryExpression.createExpression(variables.get(1), helperFunction.getEnvironmentDefinition(),
                        entities.get(entity.getName()).getPropertiesOfAllPopulation(), entities);
                return helperFunction.percent(expression1, expression2);
            case "ticks":
                return helperFunction.ticks(variables.get(0));
        }
        return null;
    }

    @Override
    public PropertyType getType() {
        switch (this.funcName){
            case "environment":
                PropertyDefinition prop = helperFunction.getEnvironmentDefinition().getProperties().get(variables.get(0));
                return prop.getType();
            case "random":
            case "ticks":
                return PropertyType.DECIMAL;
            case "evaluate":
                String[] parts = variables.get(0).split("\\.");
                return entities.get(parts[0]).getPropertiesOfAllPopulation().get(parts[1]).getType();
            case "percent":
                return PropertyType.FLOAT;
        }
        return null;
    }
  
  private EntityInstance getEntityInstance(EntityInstance mainEntityInstance, EntityInstance secondEntityInstance, String stringExpression){
        String[] parts = stringExpression.split("[()]");
        if(parts[0].equals("ticks") || parts[0].equals("evaluate")){
            String[] partsByDot = parts[1].split("\\.");
            if(partsByDot[0].equals(mainEntityInstance.getName())){
                return mainEntityInstance;
            } else if (secondEntityInstance != null && partsByDot[0].equals(secondEntityInstance.getName())) {
                return secondEntityInstance;
            }
        }
        return mainEntityInstance;
  }
  
    @Override
    public String getString() {
        return expressionString;
    }
}
