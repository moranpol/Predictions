package property;

import enums.PropertyType;

import java.io.Serializable;

public class PropertyDefinition implements Serializable {
    private final PropertyType type;
    private final String name;
    private Boolean isRandomInit;
    private final Range range;
    private Object init;

    public PropertyDefinition(PropertyType type, String name, Boolean isRandomInit, Range range, Object init) {
        this.type = type;
        this.name = name;
        this.isRandomInit = isRandomInit;
        this.range = range;
        this.init = init;
    }

    public String getName() {
        return name;
    }

    public PropertyType getType() {
        return type;
    }

    public boolean isRandomInit() {
        return isRandomInit;
    }

    public Range getRange() {
        return range;
    }

    public Object getValue() {
        return init;
    }

    public void setInit(Object init) {
        this.init = init;
    }

    public void setRandomInit(Boolean randomInit) {
        isRandomInit = randomInit;
    }
}