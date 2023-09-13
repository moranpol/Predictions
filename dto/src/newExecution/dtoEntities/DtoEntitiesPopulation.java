package newExecution.dtoEntities;

public class DtoEntitiesPopulation {
    private final String name;
    private final Integer population;

    public DtoEntitiesPopulation(String name, Integer population) {
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
