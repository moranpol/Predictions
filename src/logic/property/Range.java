package logic.property;

class Range {
    private final Number from;
    private final Number to;

    protected Range(Number from, Number to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString(){
        return ("\nRange: " + this.from + " - " +this.to);
    }
}
