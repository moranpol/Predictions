package entity;

import property.PropertyDefinition;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EntityDefinition implements Serializable {
    private final String name;
    private Integer population;
    private final Map<String, PropertyDefinition> propertiesOfAllPopulation;

    public EntityDefinition(String name, Integer population, Map<String, PropertyDefinition> propertiesOfAllPopulation) {
        this.name = name;
        this.population = population;
        this.propertiesOfAllPopulation = propertiesOfAllPopulation;
    }

    public EntityDefinition(EntityDefinition otherEntityDefinition) {
        this.name = otherEntityDefinition.getName();
        this.population = otherEntityDefinition.getPopulation();
        this.propertiesOfAllPopulation = new HashMap<>();
        for(PropertyDefinition prop : otherEntityDefinition.getPropertiesOfAllPopulation().values()){
            this.propertiesOfAllPopulation.put(prop.getName(), new PropertyDefinition(prop));
        }
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

    public void setPopulation(Integer population) {
        this.population = population;
    }
}
