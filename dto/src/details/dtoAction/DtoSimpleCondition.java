package details.dtoAction;

public class DtoSimpleCondition {

    private final String property;
    private final String value;
    private final String operator;

    public DtoSimpleCondition(String property, String value, String operator) {
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
