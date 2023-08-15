package rule.action;

import rule.action.expression.Expression;
import entity.EntityInstance;
import enums.Arithmetics;

import java.util.List;
import java.util.Map;

public class Calculation extends Action{
    private final String propertyName;
    private Expression arg1;
    private Expression arg2;
    private Arithmetics arithmetic;

    public Calculation(String entityName, String propertyName, Expression arg1, Expression arg2, Arithmetics arithmetic) {
        super(entityName);
        this.propertyName = propertyName;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arithmetic = arithmetic;
    }

    @Override
    public void activateAction(Map<String, List<EntityInstance>> entities) {
        //todo
    }
}
