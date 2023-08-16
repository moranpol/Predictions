package property;

import enums.PropertyType;

import java.util.Objects;

public class IntProperty extends PropertyInstance{
    private final Range range;
    private Integer value;

    public IntProperty(String name, Range range, Integer value) {
        super(name);
        this.range = range;
        this.value = value;
    }

    @Override
    public PropertyType getType(){
        return PropertyType.DECIMAL;
    }

    @Override
    public void setValue(Object value) {
        if((Integer)value < this.range.getFrom()){
            this.value = this.range.getFrom().intValue();
        } else if ((Integer)value  > this.range.getTo()) {
            this.value = this.range.getTo().intValue();
        }else{
            this.value = (Integer)value;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntProperty that = (IntProperty) o;
        return Objects.equals(value, that.value) && Objects.equals(range, that.range);
    }

    @Override
    public int hashCode() {
        return Objects.hash(range, value);
    }

    public Integer getValue() {
        return value;
    }
}
