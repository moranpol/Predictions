package logic;

public class Tick {
    private int stepsRange;
    private final int maxSteps;
    private int stepsCounter;

    public Tick(int stepsRangeValue, int maxStepsValue) {
        this.stepsRange = stepsRangeValue;
        this.maxSteps = maxStepsValue;
        this.stepsCounter = 0;
    }

    public boolean isMaxSteps(){
        return (maxSteps > stepsCounter);
    }

    public void addTick(){
        stepsCounter += stepsRange;
    }
}
