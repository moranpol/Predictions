package property;

import enums.PropertyType;

import java.util.Objects;

public abstract class PropertyInstance {
    private final String name;

    public PropertyInstance(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyInstance that = (PropertyInstance) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public abstract Object getValue();

    public abstract void setValue(Object value);

    public String getName() {
        return name;
    }

    public abstract PropertyType getType();
}
