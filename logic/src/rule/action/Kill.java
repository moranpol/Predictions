package rule.action;

public class Kill extends Action{

    public Kill(String entityName, SecondaryEntity secondaryEntity) {
        super(entityName, secondaryEntity);
    }

    @Override
    public void activateAction(Context context) {
        context.getMainEntityInstance().killInstance();
    }
}
