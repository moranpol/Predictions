package entity;

import generated.PRDEntity;
import generated.PRDProperty;
import property.PropertyDefinition;

import java.util.ArrayList;
import java.util.List;

public class EntityDefinition {
    private final String name;
    private int population;
    private List<PropertyDefinition> propertiesOfAllPopulation;

    public EntityDefinition(PRDEntity entity) {
        this.name = entity.getName();
        this.population = entity.getPRDPopulation();
        this.propertiesOfAllPopulation = new ArrayList<>();
        for (PRDProperty prop : entity.getPRDProperties().getPRDProperty()) {
            propertiesOfAllPopulation.add(new PropertyDefinition(prop));
        }
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
