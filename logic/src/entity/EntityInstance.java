package entity;

import property.PropertyDefinition;
import property.PropertyInstance;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class EntityInstance {
    private String name;
    private Map<String, PropertyInstance> properties;

    public EntityInstance(EntityDefinition entityDetails) {
        this.name = entityDetails.getName();
        this.properties = new HashMap<>();
        for (PropertyDefinition prop : entityDetails.getPropertiesOfAllPopulation().values()) {
            this.properties.put(prop.getName(), PropertyInstance.createPropertyInstance(prop));
            // hello moran
        }
    }
}
