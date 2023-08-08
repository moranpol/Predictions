package environment;

import generated.PRDEvironment;
import generated.PRDEnvProperty;
import property.PropertyDefinition;

import java.util.ArrayList;
import java.util.List;

public class EnvironmentDefinition {
    private List<PropertyDefinition> properties;

    public EnvironmentDefinition(PRDEvironment environment) {
        this.properties = new ArrayList<>();
        //for (environment.getPRDEnvProperty() prop : this.properties) {

        //}
        ///
    }

    public List<PropertyDefinition> getProperties() {
        return properties;
    }
}
