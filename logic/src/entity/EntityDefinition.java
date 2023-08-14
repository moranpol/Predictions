package entity;

import exceptions.InvalidNameException;
import jaxb.schema.generated.PRDEntity;
import jaxb.schema.generated.PRDProperty;
import property.PropertyDefinition;

import java.util.HashMap;
import java.util.Map;

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
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("Name:" + this.name + "\nPopulation: " + this.population + "\n Properties:\n");
        for (PropertyDefinition prop : this.propertiesOfAllPopulation.values()) {
            str.append("#" + prop);
        }
        return  str.toString();
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
