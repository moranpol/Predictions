package requests;

public class DtoTermination {
    private final Integer ticks;
    private final Integer seconds;
    private final Boolean user;

    public DtoTermination(Integer ticks, Integer seconds, Boolean user) {
        this.ticks = ticks;
        this.seconds = seconds;
        this.user = user;
    }

    public Integer getTicks() {
        return ticks;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public Boolean getUser() {
        return user;
    }
}
