package termination;

import java.io.Serializable;

public class Termination implements Serializable {
    private final Integer ticks;
    private final Integer seconds;
    private final Boolean human;

    public Termination(Integer ticks, Integer seconds, Boolean human) {
        this.ticks = ticks;
        this.seconds = seconds;
        this.human = human;
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
