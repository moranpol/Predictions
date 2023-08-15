package factory;

import property.*;

import java.util.HashMap;
import java.util.Map;

public abstract class FactoryPropertyInstance {
    public static Map<String, PropertyInstance> createPropertyInstanceMap(Map<String, PropertyDefinition> properties){
        Map<String, PropertyInstance> propertiesInstance = new HashMap<>();
        for (PropertyDefinition prop : properties.values()) {
            propertiesInstance.put(prop.getName(), createPropertyInstance(prop));
        }

        return propertiesInstance;
    }

    private static PropertyInstance createPropertyInstance(PropertyDefinition property) {
        PropertyInstance newProperty = null;
        //todo
        switch (property.getType()) {
            case DECIMAL:
                newProperty = new IntProperty(property);
                break;
            case FLOAT:
                newProperty = new FloatProperty(property);
                break;
            case STRING:
                newProperty = new StringProperty(property);
                break;
            case BOOLEAN:
                newProperty = new BooleanProperty(property);
                break;
        }

        return newProperty;
    }
}
