package details.DtoAction;

public class DtoDecrease {

    private String property;
    private String by;

    public DtoDecrease(String property, String by) {
        this.property = property;
        this.by = by;
    }

    public String getProperty() {
        return property;
    }

    public String getBy() {
        return by;
    }
}
