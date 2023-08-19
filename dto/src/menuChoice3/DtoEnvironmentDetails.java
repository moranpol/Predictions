package menuChoice3;

import menuChoice2.DtoRange;

public class DtoEnvironmentDetails {
    private final String name;
    private final String type;
    private final DtoRange range;

    public DtoEnvironmentDetails(String name, String type, DtoRange range) {
        this.name = name;
        this.type = type;
        this.range = range;
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
}
