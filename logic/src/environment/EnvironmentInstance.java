package environment;

import property.*;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class EnvironmentInstance {
    private Map<String, PropertyInstance> properties;

    public EnvironmentInstance(EnvironmentDefinition environmentDetails){
        this.properties = new HashMap<>();
        for (PropertyDefinition prop : environmentDetails.getProperties()) {
            this.properties.put(prop.getName(), PropertyInstance.createPropertyInstance(prop));
        }
    }


}
