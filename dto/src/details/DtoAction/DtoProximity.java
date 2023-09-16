package details.DtoAction;

public class DtoProximity extends DtoAction {
    private final String depth;
    private final int actionAmount;

    public DtoProximity(String name, String mainEntityName, String secondaryEntityName,String depth, int actionAmount) {
        super(name, mainEntityName, secondaryEntityName);
        this.depth = depth;
        this.actionAmount = actionAmount;
    }

    public String getDepth() {
        return depth;
    }

    public Integer getActionAmount() {
        return actionAmount;
    }
}
