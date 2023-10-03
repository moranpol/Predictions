package details.DtoAction;

public class DtoSingleCondition {

    private final String property;
    private final String value;
    private final String operator;

    public DtoSingleCondition(String property, String value, String operator) {
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
