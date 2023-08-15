package rule;

import java.util.Random;

public class Activation {
    private final Integer ticks;
    private final Double probability;

    public Activation(Integer ticks, Double probability) {
        this.ticks = ticks;
        this.probability = probability;
    }

    public boolean checkIfActivate(Integer stepsCounter){
        Random random = new Random();
        double randomNumber = random.nextDouble();
        return (randomNumber < this.probability && (stepsCounter % this.ticks) == 0);
    }

    @Override
    public String toString() {
        return "Activation{" +
                "ticks=" + ticks +
                ", probability=" + probability +
                '}';
    }
}
