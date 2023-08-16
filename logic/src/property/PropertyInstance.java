package property;

import enums.PropertyType;

public abstract class PropertyInstance {
    private final String name;

    public PropertyInstance(String name) {
        this.name = name;
    }

    public abstract Object getValue();

    public abstract void setValue(Object value);

    public String getName() {
        return name;
    }

    public abstract PropertyType getType();
}
