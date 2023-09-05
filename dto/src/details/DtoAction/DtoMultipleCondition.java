package details.DtoAction;

public class DtoMultipleCondition extends DtoAction {
    private final String logic;
    private final Integer conditionsAmount;

    public DtoMultipleCondition(String name, String mainEntityName, String secondaryEntityName, String logic, Integer conditionsAmount) {
        super(name, mainEntityName, secondaryEntityName);
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
