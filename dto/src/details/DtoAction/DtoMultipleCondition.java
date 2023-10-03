package details.DtoAction;

public class DtoMultipleCondition {
    private final String logic;
    private final Integer conditionsAmount;

    public DtoMultipleCondition(String logic, Integer conditionsAmount) {
        this.logic = logic;
        this.conditionsAmount = conditionsAmount;
    }

    public String getLogic() {
        return logic;
    }

    public Integer getConditionsAmount() {
        return conditionsAmount;
    }

}
