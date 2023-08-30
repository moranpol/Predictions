package property;

import enums.PropertyType;

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
        Float prevValue = this.value;
        if((Float)value < this.range.getFrom()){
            this.value = this.range.getFrom().floatValue();
        } else if ((Float)value  > this.range.getTo()) {
            this.value = this.range.getTo().floatValue();
        }else{
            this.value = (Float)value;
        }

        if (this.value.equals(prevValue)){
            setCurrValueCounterByTicks(getCurrValueCounterByTicks() + 1);
        } else{
            setCurrValueCounterByTicks(0);
        }
    }

    @Override
    public PropertyType getType(){
        return PropertyType.FLOAT;
    }

    public Float getValue() {
        return value;
    }
}
