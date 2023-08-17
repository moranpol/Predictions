package property;

import enums.PropertyType;

import java.util.Objects;

public class BooleanProperty extends PropertyInstance {
    boolean value;

    public BooleanProperty(String name, boolean value) {
        super(name);
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooleanProperty that = (BooleanProperty) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public PropertyType getType(){
        return PropertyType.BOOLEAN;
    }

    @Override
    public void setValue(Object value) {
        this.value = (Boolean)value;
    }

    @Override
    public Object getValue() {
        return value;
    }
}
