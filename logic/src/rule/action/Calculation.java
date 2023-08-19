package rule.action;

import enums.Arithmetics;
import exceptions.MissMatchValuesException;
import helpers.ParseFunctions;
import rule.action.expression.Expression;

import java.util.Objects;

public class Calculation extends Action{
    private final String propertyName;
    private final Expression arg1;
    private final Expression arg2;
    private final Arithmetics arithmetic;

    public Calculation(String entityName, String propertyName, Expression arg1, Expression arg2, Arithmetics arithmetic) {
        super(entityName);
        this.propertyName = propertyName;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arithmetic = arithmetic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Calculation that = (Calculation) o;
        return Objects.equals(propertyName, that.propertyName) && Objects.equals(arg1, that.arg1) && Objects.equals(arg2, that.arg2) && arithmetic == that.arithmetic;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), propertyName, arg1, arg2, arithmetic);
    }

    @Override
    public void activateAction(Context context) {
        setExpressionEntityInstance(arg1, context.getEntityInstance());
        setExpressionEntityInstance(arg2, context.getEntityInstance());
        Float floatArg1 = ParseFunctions.parseNumericTypeToFloat(arg1.getValue());
        Float floatArg2 = ParseFunctions.parseNumericTypeToFloat(arg2.getValue());
        switch (arithmetic){
            case DIVIDE:
                if(floatArg2 != 0){
                    context.getEntityInstance().getProperties().get(propertyName).setValue(floatArg1 / floatArg2);
                }
                else {
                    throw new MissMatchValuesException("Calculation divide action failed - expression arg2 is 0.\n " +
                            "    Entity name - " + getEntityName() + "\n    Property name - " + propertyName);
                }
                break;
            case MULTIPLY:
                context.getEntityInstance().getProperties().get(propertyName).setValue(floatArg1 * floatArg2);
                break;
        }
    }
}
