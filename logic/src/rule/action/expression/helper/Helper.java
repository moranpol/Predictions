package rule.action.expression.helper;

import enums.PropertyType;
import property.PropertyDefinition;
import rule.action.expression.Expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Helper implements Expression {
    private final String funcName;
    private final ArrayList<String> variables;
    private final HelperFunction helperFunction;

    public Helper(HelperFunction helperFunction, String... strings) {
        this.funcName = strings[0];
        variables = new ArrayList<>();
        variables.addAll(Arrays.asList(strings).subList(1, strings.length));
        this.helperFunction = helperFunction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Helper helper = (Helper) o;
        return Objects.equals(funcName, helper.funcName) && Objects.equals(variables, helper.variables) && Objects.equals(helperFunction, helper.helperFunction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(funcName, variables, helperFunction);
    }

    @Override
    public Object getValue() {
        switch (this.funcName){
            case "environment":
                return helperFunction.environment(this.variables.get(0));
            case "random":
                return helperFunction.random(Integer.parseInt(this.variables.get(0)));
        }
        return null;
    }

    @Override
    public PropertyType getType() {
        switch (this.funcName){
            case "environment":
                PropertyDefinition prop = helperFunction.getEnvironmentDefinition().getProperties().get(this.variables.get(0));
                return prop.getType();
            case "random":
                return PropertyType.DECIMAL;
        }
        return null;
    }
}
