package rule.action;

public class Kill extends Action{

    public Kill(String entityName) {
        super(entityName);
    }

    @Override
    public void activateAction(Context context) {
        context.getEntityInstance().killInstance();
    }
}
