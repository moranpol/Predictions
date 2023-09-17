package header;

public class DtoSimulationQueue {
    private final Integer endedCounter;
    private final Integer queueCounter;
    private final Integer runningCounter;

    public DtoSimulationQueue(Integer endedCounter, Integer queueCounter, Integer runningCounter) {
        this.endedCounter = endedCounter;
        this.queueCounter = queueCounter;
        this.runningCounter = runningCounter;
    }

    public Integer getEndedCounter() {
        return endedCounter;
    }

    public Integer getQueueCounter() {
        return queueCounter;
    }

    public Integer getRunningCounter() {
        return runningCounter;
    }
}
