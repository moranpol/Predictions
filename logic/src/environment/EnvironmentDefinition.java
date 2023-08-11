package environment;

import exceptions.InvalidNameException;
import jaxb.schema.generated.PRDEnvProperty;
import jaxb.schema.generated.PRDEvironment;
import property.PropertyDefinition;
import world.WorldDefinition;

import java.util.HashMap;
import java.util.Map;

public class EnvironmentDefinition {
    private Map<String, PropertyDefinition> properties;

    public EnvironmentDefinition(PRDEvironment environment){
        this.properties = new HashMap<>();
        for (PRDEnvProperty prop : environment.getPRDEnvProperty()) {
            if(!properties.containsKey(prop.getPRDName())){
                properties.put(prop.getPRDName(), new PropertyDefinition(prop));
            }
            else{
                throw new InvalidNameException("environment " + prop.getPRDName() + " name already exist");
            }
        }
    }

    public Map<String, PropertyDefinition> getProperties() {
        return properties;
    }
}
