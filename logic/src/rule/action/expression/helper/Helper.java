package rule.action.expression.helper;

import com.sun.xml.internal.bind.v2.runtime.property.Property;
import com.sun.xml.internal.fastinfoset.util.StringArray;
import enums.PropertyType;
import property.PropertyDefinition;
import property.PropertyInstance;
import rule.action.expression.Expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Helper implements Expression {

    private final String funcName;
    private final ArrayList<String> variables;

    public Helper(String... strings) {
        this.funcName = strings[0];
        variables = new ArrayList<String>();
        variables.addAll(Arrays.asList(strings).subList(1, strings.length));
    }

    @Override
    public Object getValue() {
        switch (this.funcName){
            case "environment":
                return HelperFunction.environment(this.variables.get(1));
            case "random":
                return HelperFunction.random(Integer.parseInt(this.variables.get(1)));
        }
        return null;
    }

    @Override
    public PropertyType getType() {
        switch (this.funcName){
            case "environment":
                PropertyDefinition prop = HelperFunction.getEnvironmentDefinition().getProperties().get(this.variables.get(1));
                return prop.getType();
            case "random":
                return PropertyType.DECIMAL;
        }
        return null;
    }
}
