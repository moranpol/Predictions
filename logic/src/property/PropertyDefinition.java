package property;

import enums.PropertyType;
import jaxb.schema.generated.PRDEnvProperty;
import jaxb.schema.generated.PRDProperty;
import jaxb.schema.generated.PRDValue;

import java.util.Objects;

public class PropertyDefinition {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyDefinition property = (PropertyDefinition) o;
        return isRandomInit == property.isRandomInit && type == property.type && Objects.equals(name, property.name) && Objects.equals(range, property.range) && Objects.equals(init, property.init);
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

    private void updateInitByType(PRDValue value){
        switch (this.type){
            case DECIMAL:
                this.init = Integer.parseInt(value.getInit());
                break;
            case FLOAT:
                this.init = Float.parseFloat(value.getInit());
                break;
            case BOOLEAN:
                this.init = Boolean.parseBoolean(value.getInit());
                break;
            case STRING:
                this.init = value.getInit();
                break;
        }
    }
}