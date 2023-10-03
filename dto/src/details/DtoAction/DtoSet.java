package details.DtoAction;

public class DtoSet {
     private String value;
     private String property;

    public DtoSet(String value, String property) {
        this.value = value;
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public String getProperty() {
        return property;
    }
}
