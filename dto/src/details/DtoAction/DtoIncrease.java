package details.DtoAction;

public class DtoIncrease extends DtoAction {

    private String property;
    private String by;


    public DtoIncrease(String name, String mainEntityName, String secondaryEntityName, String property, String by) {
        super(name, mainEntityName, secondaryEntityName);
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
