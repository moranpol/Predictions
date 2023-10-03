package details.DtoAction;

public class DtoProximity {
    private final String depth;
    private final int actionAmount;

    public DtoProximity(String depth, int actionAmount) {
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
