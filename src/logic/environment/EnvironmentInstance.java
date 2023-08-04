package logic.environment;

import logic.property.*;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class EnvironmentInstance {
    private Map<String, PropertyInstance> properties;

    public EnvironmentInstance(EnvironmentDefinition environmentDetails){
        this.properties = new HashMap<>();
        for (PropertyDefinition prop : environmentDetails.getProperties()) {
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
