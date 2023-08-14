package entity;

import exceptions.InvalidNameException;
import jaxb.schema.generated.PRDEntity;
import jaxb.schema.generated.PRDProperty;
import property.PropertyDefinition;

import java.util.HashMap;
import java.util.Map;

public class EntityDefinition {
    private final String name;
    private int population;
    private Map<String, PropertyDefinition> propertiesOfAllPopulation;

    public EntityDefinition(PRDEntity entity){
        this.name = entity.getName();
        this.population = entity.getPRDPopulation();
        this.propertiesOfAllPopulation = new HashMap<>();
        for (PRDProperty prop : entity.getPRDProperties().getPRDProperty()) {
            if(!propertiesOfAllPopulation.containsKey(prop.getPRDName())){
                propertiesOfAllPopulation.put(prop.getPRDName(), new PropertyDefinition(prop));
            }
            else{
                throw new InvalidNameException("property " + prop.getPRDName() + " name in "
                        + this.name + " entity already exist");
            }
        }
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
