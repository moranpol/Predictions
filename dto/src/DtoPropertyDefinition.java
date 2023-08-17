import enums.PropertyType;
import property.Range;

public class DtoPropertyDefinition {

    private final String name;
    private PropertyType type;
    private DtoRange range;
    private boolean isRandomInitialized;

    public DtoPropertyDefinition(String name, PropertyType type, Range range, boolean isRandomInitialized) {
        this.name = name;
        this.type = type;
        this.range = new DtoRange(range.getFrom(), range.getTo());
        this.isRandomInitialized = isRandomInitialized;
    }

    public String getName() {
        return name;
    }

    public PropertyType getType() {
        return type;
    }

    public DtoRange getRange() {
        return range;
    }

    public boolean isRandomInitialized() {
        return isRandomInitialized;
    }
}
