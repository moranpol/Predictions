package results.simulationEnded;

import java.util.List;
import java.util.Map;

public class DtoSimulationEndedEntity {
    private final String name;
    private final Map<String, DtoPropertyResults> properties;
    private final List<DtoEntityQuantityGraph> entityQuantityGraph;

    public DtoSimulationEndedEntity(String name, Map<String, DtoPropertyResults> properties, List<DtoEntityQuantityGraph> entityQuantityGraph) {
        this.name = name;
        this.properties = properties;
        this.entityQuantityGraph = entityQuantityGraph;
    }

    public String getName() {
        return name;
    }

    public Map<String, DtoPropertyResults> getProperties() {
        return properties;
    }

    public List<DtoEntityQuantityGraph> getEntityQuantityGraph() {
        return entityQuantityGraph;
    }
}
