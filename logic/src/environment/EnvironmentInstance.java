package environment;

import property.*;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class EnvironmentInstance {
    private Map<String, PropertyInstance> properties;

    public EnvironmentInstance(Map<String, PropertyInstance> properties) {
        this.properties = properties;
    }

    public Map<String, PropertyInstance> getProperties() {
        return properties;
    }
}
