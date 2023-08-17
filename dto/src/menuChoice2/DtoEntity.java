package menuChoice2;

import java.util.List;

public class DtoEntity {
    private final String name;
    private final Integer population;
    private final List<DtoProperty> propertyDefinitions;

    public DtoEntity(String name, Integer population, List<DtoProperty> propertyDefinitions) {
        this.name = name;
        this.population = population;
        this.propertyDefinitions = propertyDefinitions;
    }

    public String getName() {
        return name;
    }

    public Integer getPopulation() {
        return population;
    }

    public List<DtoProperty> getPropertyDefinitions() {
        return propertyDefinitions;
    }
}
