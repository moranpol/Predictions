package details;

import details.DtoRange;

public class DtoProperty {
    private final String name;
    private final String type;
    private final DtoRange range;
    private final boolean isRandomInitialized;

    public DtoProperty(String name, String type, DtoRange range, boolean isRandomInitialized) {
        this.name = name;
        this.type = type;
        this.range = range;
        this.isRandomInitialized = isRandomInitialized;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public DtoRange getRange() {
        return range;
    }

    public boolean isRandomInitialized() {
        return isRandomInitialized;
    }
}
