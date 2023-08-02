package logic;

import logic.property.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/////////////////////////////////////////////////////////////////////////////////////////ToDo!

class Entity {
    private final String name;
    private int population;
    private int numPropertiesOfOneEntity;
    private List<Map<String, Property>> propertiesOfAllPopulation;
    private List<Property> propertiesOfEntity; //pointer to the first properties in propertiesOfAllPopulation

    public Entity(String nameValue, int populationValue, int numOfProperties) {
        this.name = nameValue;
        this.population = populationValue;
        this.numPropertiesOfOneEntity = numOfProperties;
        //build list with XML
        this.propertiesOfEntity = new ArrayList<>();
        this.propertiesOfEntity.addAll(propertiesOfAllPopulation.get(0).values());
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("Name:" + this.name + "\nPopulation: " + this.population + "\n Properties:\n");
        for (Property prop : propertiesOfEntity) {
            str.append("#" +prop.toString());
        }
        return str.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return population == entity.population && numPropertiesOfOneEntity == entity.numPropertiesOfOneEntity && Objects.equals(name, entity.name) && Objects.equals(propertiesOfAllPopulation, entity.propertiesOfAllPopulation) && Objects.equals(propertiesOfEntity, entity.propertiesOfEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, population, numPropertiesOfOneEntity, propertiesOfAllPopulation, propertiesOfEntity);
    }
}
