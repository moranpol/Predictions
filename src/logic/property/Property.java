package logic.property;

import java.util.Objects;

public abstract class Property {
    private final String name;
    private boolean isRandomInit;

    protected Property(String nameValue, boolean isRandomInitValue) {
        this.name = nameValue;
        this.isRandomInit = isRandomInitValue;
    }

    @Override
    public String toString(){
        return ("Name: " + this.name + "\nIs random: " + this.isRandomInit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return isRandomInit == property.isRandomInit && Objects.equals(name, property.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isRandomInit);
    }
}
