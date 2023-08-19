package entity;

import property.PropertyInstance;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class EntityInstance implements Serializable {
    private Boolean isDead;
    private final Map<String, PropertyInstance> properties;

    public EntityInstance(Map<String, PropertyInstance> properties) {
        this.isDead = false;
        this.properties = properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityInstance that = (EntityInstance) o;
        return Objects.equals(isDead, that.isDead) && Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isDead, properties);
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
