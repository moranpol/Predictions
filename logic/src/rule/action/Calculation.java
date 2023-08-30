package rule.action;

import entity.EntityInstance;
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
        EntityInstance entityInstance;
        try{
            entityInstance = getEntityInstance(context);
        } catch (Exception e){
            throw new MissMatchValuesException(e.getMessage() + " is not one of the main or second instances.\n" +
                    "    Calculation action failed.");
        }

        Float floatArg1 = ParseFunctions.parseNumericTypeToFloat(arg1.getValue(context.getMainEntityInstance(), context.getSecondEntityInstance()));
        Float floatArg2 = ParseFunctions.parseNumericTypeToFloat(arg2.getValue(context.getMainEntityInstance(), context.getSecondEntityInstance()));
        switch (arithmetic){
            case DIVIDE:
                if(floatArg2 != 0){
                    context.getMainEntityInstance().getProperties().get(propertyName).setValue(floatArg1 / floatArg2);
                }
                else {
                    throw new MissMatchValuesException("Calculation divide action failed - expression arg2 is 0.\n " +
                            "    Entity name - " + getMainEntityName() + "\n    Property name - " + propertyName);
                }
                break;
            case MULTIPLY:
                context.getMainEntityInstance().getProperties().get(propertyName).setValue(floatArg1 * floatArg2);
                break;
        }
    }
}
