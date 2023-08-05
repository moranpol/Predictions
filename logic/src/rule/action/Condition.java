package rule.action;

import java.util.List;

public abstract class Condition extends Action{
    private List<Action> thenActions;
    private List<Action> elseActions;

    public Condition(String entityName) {
        //todo
        super(entityName);
    }
}
