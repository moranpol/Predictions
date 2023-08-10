package rule;

import jaxb.schema.generated.PRDActivation;

import java.util.Random;

public class Activation {
    private int ticks;
    private double probability;

    public Activation(PRDActivation activation) {
        this.ticks = 1;
        this.probability = 1;

        if(activation != null){
            if(activation.getTicks() != null) {
                checkTicksValid(activation.getTicks());
                this.ticks = activation.getTicks();
            }
            if (activation.getProbability() != null){
                checkProbabilityValid(activation.getProbability());
                this.probability = activation.getProbability();
            }
        }
    }

    private void checkTicksValid(int tick){
        if(tick <= 0){
            throw new IndexOutOfBoundsException("Ticks cannot be negative or 0");
        }
    }

    private void checkProbabilityValid(double probability){
        if(probability < 0 || probability > 1){
            throw new IndexOutOfBoundsException("Probability range must be 0-1");
        }
    }

    public boolean checkIfActivate(Tick tick){
        Random random = new Random();
        double randomNumber = random.nextDouble();
        return (randomNumber < this.probability && (tick.getStepsCounter() % this.ticks) == 0);
    }


}
