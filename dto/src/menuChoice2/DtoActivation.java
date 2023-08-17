package menuChoice2;

public class DtoActivation {
    private final Integer ticks;
    private final Double probability ;

    public DtoActivation(Integer ticks, Double probability) {
        this.ticks = ticks;
        this.probability = probability;
    }

    public Integer getTicks() {
        return ticks;
    }

    public Double getProbability() {
        return probability;
    }
}
