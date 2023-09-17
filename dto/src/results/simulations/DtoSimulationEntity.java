package results.simulations;

public class DtoSimulationEntity {
    private final String name;
    private final Integer population;

    public DtoSimulationEntity(String name, Integer population) {
        this.name = name;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public Integer getPopulation() {
        return population;
    }
}
