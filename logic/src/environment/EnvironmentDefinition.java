package environment;

import property.PropertyDefinition;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EnvironmentDefinition implements Serializable {
    private final Map<String, PropertyDefinition> properties;

    public EnvironmentDefinition(Map<String, PropertyDefinition> properties) {
        this.properties = properties;
    }

    public EnvironmentDefinition(EnvironmentDefinition otherEnvironmentDefinition) {
        this.properties = new HashMap<>();
        for (PropertyDefinition prop : otherEnvironmentDefinition.getProperties().values()){
            this.properties.put(prop.getName(), new PropertyDefinition(prop));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnvironmentDefinition that = (EnvironmentDefinition) o;
        return Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(properties);
    }

    public Map<String, PropertyDefinition> getProperties() {
        return properties;
    }
}
