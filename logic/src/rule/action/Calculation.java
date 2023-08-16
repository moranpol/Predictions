package rule.action;

import entity.EntityInstance;
import enums.Arithmetics;
import exceptions.MissMatchValuesException;
import rule.action.expression.Expression;

import java.util.List;
import java.util.Map;

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
    public void activateAction(Context context) {
        setExpressionEntityInstance(arg1, context.getEntityInstance());
        setExpressionEntityInstance(arg2, context.getEntityInstance());
        switch (arithmetic){
            case DIVIDE:
                if((Float)arg2.getValue() != 0){
                    context.getEntityInstance().getProperties().get(propertyName).
                            setValue((Float)arg1.getValue()/(Float)arg2.getValue());
                }
                else {
                    throw new MissMatchValuesException("Calculation divide action failed - expression arg2 is 0.\n " +
                            "Entity name - " + getEntityName() + "\nProperty name - " + propertyName);
                }
                break;
            case MULTIPLY:
                context.getEntityInstance().getProperties().get(propertyName).
                        setValue((Float)arg1.getValue()*(Float)arg2.getValue());
                break;
        }
    }
}
