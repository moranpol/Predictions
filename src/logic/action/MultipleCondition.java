package logic.action;

import logic.entity.EntityInstance;
import logic.enums.Logicals;

import java.util.List;

public class MultipleCondition extends Condition{
    private List<Condition> conditions;
    private Logicals logic;

    @Override
    public void activateAction(EntityInstance mainEntity) {
        //todo
    }
}
