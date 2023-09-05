package termination;

import java.io.Serializable;
import java.util.Objects;

public class Termination implements Serializable {
    private final Integer ticks;
    private final Integer seconds;
    private final Boolean human;

    public Termination(Integer ticks, Integer seconds, Boolean human) {
        this.ticks = ticks;
        this.seconds = seconds;
        this.human = human;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Termination that = (Termination) o;
        return Objects.equals(ticks, that.ticks) && Objects.equals(seconds, that.seconds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticks, seconds);
    }

    public Integer getTicks() {
        return ticks;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public Boolean getHuman() {
        return human;
    }
}
