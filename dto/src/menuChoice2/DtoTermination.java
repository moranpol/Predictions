package menuChoice2;

public class DtoTermination {
    private final Integer ticks;
    private final Integer seconds;

    public DtoTermination(Integer ticks, Integer seconds) {
        this.ticks = ticks;
        this.seconds = seconds;
    }

    public Integer getTicks() {
        return ticks;
    }

    public Integer getSeconds() {
        return seconds;
    }
}
