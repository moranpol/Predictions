package property;

import jaxb.schema.generated.PRDRange;

public class Range {
    private final Double from;
    private final Double to;

    public Range(Double from, Double to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString(){
        return ("\nRange: " + this.from + " - " +this.to);
    }

    public Double getFrom() {
        return from;
    }

    public Double getTo() {
        return to;
    }
}
