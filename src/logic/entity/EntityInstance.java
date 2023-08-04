package logic.entity;

import logic.property.PropertyDefinition;
import logic.property.PropertyInstance;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class EntityInstance {
    private String name;
    private Map<String, PropertyInstance> properties;

    public EntityInstance(EntityDefinition entityDetails) {
        this.name = entityDetails.getName();
        this.properties = new HashMap<>();
        for (PropertyDefinition prop : entityDetails.getPropertiesOfAllPopulation()) {
            try {
                Class<?> clazz = PropertyInstance.getPropertyType(prop.getType());
                Constructor<?> constructor = clazz.getDeclaredConstructor(PropertyDefinition.class);
                this.properties.put(prop.getName(), (PropertyInstance)constructor.newInstance(prop));
            } catch (Exception e) {
            }

            /*
            switch (prop.getType()){
                case DECIMAL:
                    this.properties.put(prop.getName(), new IntProperty(prop));
                    break;
                case FLOAT:
                    this.properties.put(prop.getName(), new FloatProperty(prop));
                    break;
                case STRING:
                    this.properties.put(prop.getName(), new StringProperty(prop));
                    break;
                case BOOLEAN:
                    this.properties.put(prop.getName(), new BooleanProperty(prop));
                    break;
            }
            */
        }
    }
}
