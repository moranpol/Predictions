package results.simulationEnded;

public class DtoPropertyHistogram {
    private final String valueOfProperty;
    private final Integer amount;

    public DtoPropertyHistogram(String valueOfProperty, Integer amount) {
        this.valueOfProperty = valueOfProperty;
        this.amount = amount;
    }

    public String getValueOfProperty() {
        return valueOfProperty;
    }

    public Integer getAmount() {
        return amount;
    }
}
