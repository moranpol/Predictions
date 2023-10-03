package details;

public class DtoProperty {
    private final String name;
    private final String type;
    private final boolean isRandomInitialized;
    private final Double from;
    private final Double to;

    public DtoProperty(String name, String type, boolean isRandomInitialized, Double from, Double to) {
        this.name = name;
        this.type = type;
        this.isRandomInitialized = isRandomInitialized;
        this.from = from;
        this.to = to;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isRandomInitialized() {
        return isRandomInitialized;
    }

    public Double getFrom() {
        return from;
    }

    public Double getTo() {
        return to;
    }
}
