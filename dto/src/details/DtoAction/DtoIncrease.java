package details.DtoAction;

public class DtoIncrease {

    private String property;
    private String by;


    public DtoIncrease(String property, String by) {
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
