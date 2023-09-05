package details.DtoAction;

public class DtoSet extends DtoAction {
     private String value;
     private String property;

    public DtoSet(String name, String mainEntityName, String secondaryEntityName, String value, String property) {
        super(name, mainEntityName, secondaryEntityName);
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
