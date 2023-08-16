package entity;

import property.PropertyDefinition;
import property.PropertyInstance;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class EntityInstance {
    private Boolean isDead;
    private Map<String, PropertyInstance> properties;

    public EntityInstance(Map<String, PropertyInstance> properties) {
        this.isDead = false;
        this.properties = properties;
    }

    public Map<String, PropertyInstance> getProperties() {
        return properties;
    }

    public Boolean getDead() {
        return isDead;
    }

    public void killInstance(){
        isDead = true;
    }
}
