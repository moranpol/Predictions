package entity;

import grid.Location;
import property.PropertyInstance;

import java.io.Serializable;
import java.util.Map;

public class EntityInstance implements Serializable {
    private final String name;
    private Boolean isDead;
    private final Map<String, PropertyInstance> properties;
    private Location location;

    public EntityInstance(String name, Map<String, PropertyInstance> properties) {
        this.name = name;
        this.isDead = false;
        this.properties = properties;
    }

    public Map<String, PropertyInstance> getProperties() {
        return properties;
    }

    public Boolean isDead() {
        return isDead;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void updateLocation(Integer row, Integer col) {
        location.setRow(row);
        location.setCol(col);
    }

    public void killInstance(){
        isDead = true;
    }
}
