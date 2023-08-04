package logic.entity;

import logic.property.PropertyDefinition;
import java.util.List;

public class EntityDefinition {
    private final String name;
    private int population;
    private List<PropertyDefinition> propertiesOfAllPopulation;

    public EntityDefinition(String nameValue, int populationValue) {
        this.name = nameValue;
        this.population = populationValue;
        //build list with XML - todo
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("Name:" + this.name + "\nPopulation: " + this.population + "\n Properties:\n");
        for (PropertyDefinition prop : this.propertiesOfAllPopulation) {
            str.append("#" + prop);
        }
        return  str.toString();
    }

    public String getName() {
        return name;
    }

    public List<PropertyDefinition> getPropertiesOfAllPopulation() {
        return propertiesOfAllPopulation;
    }

    public int getPopulation() {
        return population;
    }
}
