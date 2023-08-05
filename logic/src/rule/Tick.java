package rule;

public class Tick {
    private final int maxSteps;
    private int stepsCounter;

    public Tick(int maxStepsValue) {
        this.maxSteps = maxStepsValue;
        this.stepsCounter = 0;
    }

    public int getStepsCounter() {
        return stepsCounter;
    }

    public boolean isMaxSteps(){
        return (maxSteps > stepsCounter);
    }

    public void addTick(){
        stepsCounter++;
    }
}
