package rule.action;

import entity.EntityInstance;
import enums.Arithmetics;

import java.util.List;
import java.util.Map;

public class Calculation extends Action{
    private final String propertyName;
    private Expression arg1;
    private Expression arg2;
    private Arithmetics arithmetic;

    public Calculation(String entityName) {
        //todo
        super(entityName);
        propertyName = null;
    }

    @Override
    public void activateAction(Map<String, List<EntityInstance>> entities) {
        //todo
    }
}
