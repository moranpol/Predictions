package newExecution;

public class EntitiesPopulationDto {
    private final String name;
    private final Integer population;

    public EntitiesPopulationDto(String name, Integer population) {
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
