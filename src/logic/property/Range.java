package logic.property;

public class Range {
    private final Number from;
    private final Number to;

    public Range(Number from, Number to) {
        this.from = from;
        this.to = to;
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
