package details.dtoAction;

public class DtoAction {
    private final String name;
    private final String mainEntityName;
    private final String secondaryEntityName;
    private final String stringExpression;
    private final DtoCalculation dtocalculation;
    private final DtoSimpleCondition dtoSimpleCondition;
    private final DtoMultipleCondition dtoMultipleCondition;
    private final DtoProximity dtoProximity;

    public DtoAction(String name, String mainEntityName, String secondaryEntityName, String stringExpression, DtoCalculation dtocalculation,
                     DtoSimpleCondition dtoSimpleCondition, DtoMultipleCondition dtoMultipleCondition, DtoProximity dtoProximity) {
        this.name = name;
        this.mainEntityName = mainEntityName;
        this.secondaryEntityName = secondaryEntityName;
        this.stringExpression = stringExpression;
        this.dtocalculation = dtocalculation;
        this.dtoSimpleCondition = dtoSimpleCondition;
        this.dtoMultipleCondition = dtoMultipleCondition;
        this.dtoProximity = dtoProximity;
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

    public String getStringExpression() {
        return stringExpression;
    }

    public DtoCalculation getDtocalculation() {
        return dtocalculation;
    }

    public DtoSimpleCondition getDtoSimpleCondition() {
        return dtoSimpleCondition;
    }

    public DtoMultipleCondition getDtoMultipleCondition() {
        return dtoMultipleCondition;
    }

    public DtoProximity getDtoProximity() {
        return dtoProximity;
    }
}
