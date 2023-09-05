package details.DtoAction;

public class DtoSimpleCondition extends DtoAction {

    private final String property;
    private final String value;
    private final String operator;

    public DtoSimpleCondition(String name, String mainEntityName, String secondaryEntityName, String property,
                              String value, String operator) {
        super(name, mainEntityName, secondaryEntityName);
        this.property = property;
        this.value = value;
        this.operator = operator;

    }

    public String getProperty() {
        return property;
    }

    public String getValue() {
        return value;
    }

    public String getOperator() {
        return operator;
    }

}
