package property;

import enums.PropertyType;

import java.util.Objects;

public class FloatProperty extends PropertyInstance{
    private final Range range;
    private Float value;

    public FloatProperty(String name, Range range, Float value) {
        super(name);
        this.range = range;
        this.value = value;
    }

    @Override
    public void setValue(Object value) {
        if((Float)value < this.range.getFrom()){
            this.value = this.range.getFrom().floatValue();
        } else if ((Float)value  > this.range.getTo()) {
            this.value = this.range.getTo().floatValue();
        }else{
            this.value = (Float)value;
        }
    }

    @Override
    public PropertyType getType(){
        return PropertyType.FLOAT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FloatProperty that = (FloatProperty) o;
        return Float.compare(value, that.value) == 0 && Objects.equals(range, that.range);
    }

    @Override
    public int hashCode() {
        return Objects.hash(range, value);
    }

    public Float getValue() {
        return value;
    }
}
