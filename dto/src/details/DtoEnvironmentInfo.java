package details;

public class DtoEnvironmentInfo {
    private final String name;
    private final String type;
    private final Double from;
    private final Double to;

    public DtoEnvironmentInfo(String name, String type, Double from, Double to) {
        this.name = name;
        this.type = type;
        this.from = from;
        this.to = to;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Double getFrom() {
        return from;
    }

    public Double getTo() {
        return to;
    }
}
