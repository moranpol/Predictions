package property;

import enums.PropertyType;
import generated.PRDProperty;
import java.util.Objects;

public class PropertyDefinition {
    private PropertyType type;
    private final String name;
    private final boolean isRandomInit;
    private final Range range;
    private Object init;

    //todo
    //if range null and random init return exception
    //type - check if int, float, string, bool
    //if not random init and not value return exception
    //type is like value
    public PropertyDefinition(PRDProperty property) {
        this.name = property.getPRDName();
        this.type = Enum.valueOf(PropertyType.class, property.getType());
        this.isRandomInit = property.getPRDValue().isRandomInitialize();
        this.range = new Range(property.getPRDRange());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyDefinition property = (PropertyDefinition) o;
        return isRandomInit == property.isRandomInit && type == property.type && Objects.equals(name, property.name) && Objects.equals(range, property.range) && Objects.equals(value, property.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name, isRandomInit, range, init);
    }

    @Override
    public String toString(){
        return ("Name: " + this.name + "\nType:" + this.type.name().toLowerCase() + this.range +
                "\nIs random: " + this.isRandomInit + "\n");
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
}
