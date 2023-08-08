package environment;

import property.PropertyDefinition;
import java.util.List;

public class EnvironmentDefinition {
    private List<PropertyDefinition> properties;

    public EnvironmentDefinition(List<PropertyDefinition> properties) {
        //todo - get from PRD-ENVIRONMENT
        this.properties = properties;
    }

    public List<PropertyDefinition> getProperties() {
        return properties;
    }
}
