package rule.action;

import entity.EntityInstance;
import enums.Logicals;

import java.util.List;
import java.util.Map;

public class MultipleCondition extends Condition{
    private List<Condition> conditions;
    private Logicals logic;

    public MultipleCondition(String entityName, List<Condition> conditions, Logicals logic) {
        super(entityName);
        this.conditions = conditions;
        this.logic = logic;
    }

    @Override
    public void activateAction(Map<String, List<EntityInstance>> entities) {
        //todo
    }
}
