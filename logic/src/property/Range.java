package property;

import jaxb.schema.generated.PRDRange;

public class Range {
    private final double from;
    private final double to;

    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString(){
        return ("\nRange: " + this.from + " - " +this.to);
    }

    public double getFrom() {
        return from;
    }

    public double getTo() {
        return to;
    }
}
