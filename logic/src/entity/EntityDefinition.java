package entity;

import property.PropertyDefinition;

import java.util.Map;
import java.util.Objects;

public class EntityDefinition {
    private final String name;
    private Integer population;
    private Map<String, PropertyDefinition> propertiesOfAllPopulation;

    public EntityDefinition(String name, Integer population, Map<String, PropertyDefinition> propertiesOfAllPopulation) {
        this.name = name;
        this.population = population;
        this.propertiesOfAllPopulation = propertiesOfAllPopulation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityDefinition that = (EntityDefinition) o;
        return Objects.equals(name, that.name) && Objects.equals(population, that.population) && Objects.equals(propertiesOfAllPopulation, that.propertiesOfAllPopulation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, population, propertiesOfAllPopulation);
    }

    public String getName() {
        return name;
    }

    public Map<String, PropertyDefinition> getPropertiesOfAllPopulation() {
        return propertiesOfAllPopulation;
    }

    public int getPopulation() {
        return population;
    }
}
