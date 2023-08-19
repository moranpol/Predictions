package rule;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

public class Activation implements Serializable {
    private final Integer ticks;
    private final Double probability;

    public Activation(Integer ticks, Double probability) {
        this.ticks = ticks;
        this.probability = probability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activation that = (Activation) o;
        return Objects.equals(ticks, that.ticks) && Objects.equals(probability, that.probability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticks, probability);
    }

    public boolean checkIfActivate(Integer stepsCounter){
        Random random = new Random();
        double randomNumber = random.nextDouble();
        return (randomNumber < this.probability && (stepsCounter % this.ticks) == 0);
    }

    public Integer getTicks() {
        return ticks;
    }

    public Double getProbability() {
        return probability;
    }
}
