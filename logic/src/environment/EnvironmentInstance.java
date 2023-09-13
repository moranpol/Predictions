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

    public Map<String, PropertyInstance> getProperties() {
        return properties;
    }
}
