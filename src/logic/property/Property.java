package logic.property;

import logic.enums.PropertyType;

import java.util.Objects;

public class Property {
    private PropertyType type;
    private final String name;
    private final boolean isRandomInit;
    private Range range;
    private Object value;

    //todo- constractors ? builders ?
    public Property(PropertyType type, String name, boolean isRandomInit, float from, float to, Object value) {
        this.type = type;
        this.name = name;
        this.isRandomInit = isRandomInit;
        this.range = new Range(from, to);
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return isRandomInit == property.isRandomInit && type == property.type && Objects.equals(name, property.name) && Objects.equals(range, property.range) && Objects.equals(value, property.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name, isRandomInit, range, value);
    }

    @Override
    public String toString(){
        //todo
        return ("Name: " + this.name + "\nType:" + this.type.name().toLowerCase() + this.range +
                "\nIs random: " + this.isRandomInit);
    }

}
