package logic;

import logic.property.Property;

import java.util.List;
import java.util.Map;

/////////////////////////////////////////////////////////////////////////////////////////ToDo!

class Entity {
    private final String name;
    private int population;
    private int numPropertiesOfOneEntity;
    private List<Map<String, Property>> propertiesOfAllPopulation;

    public Entity(String nameValue, int populationValue, int numOfProperties) {
        this.name = nameValue;
        this.population = populationValue;
        this.numPropertiesOfOneEntity = numOfProperties;
        //build list with XML
    }
}
