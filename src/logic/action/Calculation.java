package logic.action;

import logic.action.expression.Expression;
import logic.entity.EntityInstance;
import logic.enums.Arithmetics;

public class Calculation extends Action{
    private String propertyName;
    private Expression arg1;
    private Expression arg2;
    private Arithmetics arithmetic;

    @Override
    public void activateAction(EntityInstance mainEntity) {
        //todo
    }
}
