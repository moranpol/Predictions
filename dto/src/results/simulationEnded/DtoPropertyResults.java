package results.simulationEnded;

import java.util.Map;

public class DtoPropertyResults {
    private final String name;
    private final String type;
    private final Object value;
    private final Map<String, DtoPropertyHistogram> dtoPropertyHistogram;
    private final Float consistency;
    private final Float AverageOfPropertyInPopulation;

    public DtoPropertyResults(String name, String type, Object value, Map<String, DtoPropertyHistogram> dtoPropertyHistogram, Float consistency, Float averageOfPropertyInPopulation) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.dtoPropertyHistogram = dtoPropertyHistogram;
        this.consistency = consistency;
        AverageOfPropertyInPopulation = averageOfPropertyInPopulation;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public Map<String, DtoPropertyHistogram> getDtoPropertyHistogram() {
        return dtoPropertyHistogram;
    }

    public Float getConsistency() {
        return consistency;
    }

    public Float getAverageOfPropertyInPopulation() {
        return AverageOfPropertyInPopulation;
    }
}
