package environment;

import property.*;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class EnvironmentInstance implements Serializable {
    private final Map<String, PropertyInstance> properties;

    public EnvironmentInstance(Map<String, PropertyInstance> properties) {
        this.properties = properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnvironmentInstance that = (EnvironmentInstance) o;
        return Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(properties);
    }

    public Map<String, PropertyInstance> getProperties() {
        return properties;
    }
}
