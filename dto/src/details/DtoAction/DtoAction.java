package details.DtoAction;

public class DtoAction {
    private final String name;
    private final String mainEntityName;
    private final String secondaryEntityName;
    private final DtoCalculation dtoCalculation;
    private final DtoDecrease dtoDecrease;
    private final DtoIncrease dtoIncrease;
    private final DtoMultipleCondition dtoMultipleCondition;
    private final DtoProximity dtoProximity;
    private final DtoReplace dtoReplace;
    private final DtoSet dtoSet;
    private final DtoSingleCondition dtoSingleCondition;

    public DtoAction(String name, String mainEntityName, String secondaryEntityName, DtoCalculation dtoCalculation, DtoDecrease dtoDecrease, DtoIncrease dtoIncrease,
                     DtoMultipleCondition dtoMultipleCondition, DtoProximity dtoProximity, DtoReplace dtoReplace, DtoSet dtoSet, DtoSingleCondition dtoSingleCondition) {
        this.name = name;
        this.mainEntityName = mainEntityName;
        this.secondaryEntityName = secondaryEntityName;
        this.dtoCalculation = dtoCalculation;
        this.dtoDecrease = dtoDecrease;
        this.dtoIncrease = dtoIncrease;
        this.dtoMultipleCondition = dtoMultipleCondition;
        this.dtoProximity = dtoProximity;
        this.dtoReplace = dtoReplace;
        this.dtoSet = dtoSet;
        this.dtoSingleCondition = dtoSingleCondition;
    }

    public String getName() {
        return name;
    }

    public String getMainEntityName() {
        return mainEntityName;
    }

    public String getSecondaryEntityName() {
        return secondaryEntityName;
    }

    public DtoCalculation getDtoCalculation() {
        return dtoCalculation;
    }

    public DtoDecrease getDtoDecrease() {
        return dtoDecrease;
    }

    public DtoIncrease getDtoIncrease() {
        return dtoIncrease;
    }

    public DtoMultipleCondition getDtoMultipleCondition() {
        return dtoMultipleCondition;
    }

    public DtoProximity getDtoProximity() {
        return dtoProximity;
    }

    public DtoReplace getDtoReplace() {
        return dtoReplace;
    }

    public DtoSet getDtoSet() {
        return dtoSet;
    }

    public DtoSingleCondition getDtoSimpleCondition() {
        return dtoSingleCondition;
    }
}
