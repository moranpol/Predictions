package entity;

import property.PropertyInstance;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class EntityInstance implements Serializable {
    private final String name;
    private Boolean isDead;
    private final Map<String, PropertyInstance> properties;

    public EntityInstance(String name, Map<String, PropertyInstance> properties) {
        this.name = name;
        this.isDead = false;
        this.properties = properties;
    }

    public Map<String, PropertyInstance> getProperties() {
        return properties;
    }

    public Boolean getDead() {
        return isDead;
    }

    public String getName() {
        return name;
    }

    public void killInstance(){
        isDead = true;
    }
}
