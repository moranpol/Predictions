package details;

public class DtoEnvironmentInfo {
    private final String name;
    private final String type;
    private final DtoRange range;

    public DtoEnvironmentInfo(String name, String type, DtoRange range) {
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
