package logic;

import logic.property.Property;

import java.util.List;

public class EntityDefinition {
    private final String name;
    private int population;
    private List<Property> propertiesOfAllPopulation;

    public EntityDefinition(String nameValue, int populationValue) {
        this.name = nameValue;
        this.population = populationValue;
        //build list with XML - todo
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("Name:" + this.name + "\nPopulation: " + this.population + "\n Properties:\n");
        //print property - todo
        return  str.toString();
    }
}
