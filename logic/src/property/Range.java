package property;

import jaxb.schema.generated.PRDRange;

public class Range {
    private final Number from;
    private final Number to;

    public Range(PRDRange range) {
        this.from = range.getFrom();
        this.to = range.getTo();
    }

    @Override
    public String toString(){
        return ("\nRange: " + this.from + " - " +this.to);
    }

    public Number getFrom() {
        return from;
    }

    public Number getTo() {
        return to;
    }
}
