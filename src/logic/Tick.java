package logic;

public class Tick {
    private int stepsRange;
    private int maxSteps;
    private int stepsCounter;

    public Tick(int stepsRangeValue, int maxStepsValue) {
        this.stepsRange = stepsRangeValue;
        this.maxSteps = maxStepsValue;
        this.stepsCounter = 0;
    }

    protected boolean isMaxSteps(){
        return (maxSteps > stepsCounter);
    }

    protected void addTick(){
        stepsCounter += stepsRange;
    }
}
