package results.simulationEnded;

public class DtoEntityQuantityGraph {
    private final Integer tick;
    private final Integer entityAmount;

    public DtoEntityQuantityGraph(Integer tick, Integer entityAmount) {
        this.tick = tick;
        this.entityAmount = entityAmount;
    }

    public Integer getTick() {
        return tick;
    }

    public Integer getEntityAmount() {
        return entityAmount;
    }
}
