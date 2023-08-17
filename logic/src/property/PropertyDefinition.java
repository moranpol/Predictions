package property;

import enums.PropertyType;
import helpers.ParseFunctions;

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
        PropertyDefinition that = (PropertyDefinition) o;
        return type == that.type && Objects.equals(name, that.name) && Objects.equals(isRandomInit, that.isRandomInit) && Objects.equals(range, that.range) && Objects.equals(init, that.init);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name, isRandomInit, range, init);
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
        if(init instanceof String && type != PropertyType.STRING){
            init = ParseFunctions.parseInitByType(type, (String)init);
        }

        this.init = init;
        if(type == PropertyType.DECIMAL) {
            if ((Integer)init < this.range.getFrom()) {
                this.init = this.range.getFrom();
            } else if ((Integer) init > this.range.getTo()) {
                this.init = this.range.getTo();
            }
        } else if (type == PropertyType.FLOAT) {
            if ((Float)init < this.range.getFrom()) {
                this.init = this.range.getFrom();
            } else if ((Float) init > this.range.getTo()) {
                this.init = this.range.getTo();
            }
        }
    }

    public void setRandomInit(Boolean randomInit) {
        isRandomInit = randomInit;
    }
}