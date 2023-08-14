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

    public EnvironmentDefinition(Map<String, PropertyDefinition> properties) {
        this.properties = properties;
}

    public Map<String, PropertyDefinition> getProperties() {
        return properties;
    }
}
