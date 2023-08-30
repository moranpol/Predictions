package details.dtoAction;

public class DtoMultipleCondition {
    private final String logic;
    private final int conditionAmount;

    public DtoMultipleCondition(String logic, int conditionAmount) {
        this.logic = logic;
        this.conditionAmount = conditionAmount;
    }

    public String getLogic() {
        return logic;
    }

    public int getConditionAmount() {
        return conditionAmount;
    }
}
