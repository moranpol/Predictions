package property;

import enums.PropertyType;

import java.util.Objects;

public class StringProperty extends PropertyInstance{
    private String value;

    public StringProperty(String name, String value) {
        super(name);
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StringProperty that = (StringProperty) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = (String)value;
    }

    @Override
    public PropertyType getType(){
        return PropertyType.STRING;
    }
}
