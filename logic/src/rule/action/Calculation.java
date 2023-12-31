package rule.action;

import enums.Arithmetics;
import exceptions.MissMatchValuesException;
import helpers.ParseFunctions;
import rule.action.expression.Expression;

public class Calculation extends Action{
    private final String propertyName;
    private final Expression arg1;
    private final Expression arg2;
    private final Arithmetics arithmetic;

    public String getArg1String() {
        return arg1.getString();
    }

    public String getArg2String() {
        return arg2.getString();
    }

    public String getArithmeticString() {
        return arithmetic.toString();
    }

    public Calculation(String entityName, String propertyName, Expression arg1, Expression arg2, Arithmetics arithmetic, SecondaryEntity secondaryEntity) {
        super(entityName, secondaryEntity);
        this.propertyName = propertyName;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arithmetic = arithmetic;
    }

    @Override
    public void activateAction(Context context) {
        Object valueArg1 = arg1.getValue(context);
        Object valueArg2 = arg2.getValue(context);

        if(valueArg1 == null || valueArg2 == null){
            return;
        }

        Float floatArg1 = ParseFunctions.parseNumericTypeToFloat(valueArg1);
        Float floatArg2 = ParseFunctions.parseNumericTypeToFloat(valueArg2);
        switch (arithmetic){
            case DIVIDE:
                if(floatArg2 != 0){
                    context.getMainEntityInstance().getProperties().get(propertyName).setCurrValue(floatArg1 / floatArg2);
                }
                else {
                    throw new MissMatchValuesException("Calculation divide action failed - expression arg2 is 0.\n" +
                            "Entity name - " + getMainEntityName() + "\nProperty name - " + propertyName);
                }
                break;
            case MULTIPLY:
                context.getMainEntityInstance().getProperties().get(propertyName).setCurrValue(floatArg1 * floatArg2);
                break;
        }
    }
}
