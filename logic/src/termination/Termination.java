package termination;

public class Termination {
    private final Integer ticks;
    private final Integer seconds;

    public Termination(Integer ticks, Integer seconds) {
        this.ticks = ticks;
        this.seconds = seconds;
    }

    public Integer getTicks() {
        return ticks;
    }

    public Integer getSeconds() {
        return seconds;
    }

    @Override
    public String toString() {
        return "Termination{" +
                "ticks=" + ticks +
                ", seconds=" + seconds +
                '}';
    }
}
