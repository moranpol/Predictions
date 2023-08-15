package property;

import enums.PropertyType;

import java.util.Objects;
import java.util.Random;

public class FloatProperty extends PropertyInstance{
    private final Range range;
    private Float value;

    public Range getRange() {
        return range;
    }
    public Float getValue() {
        return value;
    }

    public FloatProperty(PropertyDefinition prop) {
        super(prop.getName());
        this.range = prop.getRange();
        if(prop.isRandomInit()){
            Random random = new Random();
            this.value = this.range.getFrom().floatValue() + random.nextFloat() *
                    (this.range.getTo().floatValue() - this.range.getFrom().floatValue());
        }
        else{
            this.value = (float)prop.getValue() ;
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
}
