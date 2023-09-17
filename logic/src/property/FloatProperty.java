package property;

import enums.PropertyType;

public class FloatProperty extends PropertyInstance{
    private final Range range;
    private Float currValue;
    private Float pastValue;

    public FloatProperty(String name, Range range, Float value) {
        super(name);
        this.range = range;
        currValue = value;
        pastValue = value;
    }

    @Override
    public void setCurrValue(Object currValue) {
        pastValue = this.currValue;
        if((Float) currValue < this.range.getFrom()){
            this.currValue = this.range.getFrom().floatValue();
        } else if ((Float) currValue > this.range.getTo()) {
            this.currValue = this.range.getTo().floatValue();
        }else{
            this.currValue = (Float) currValue;
        }
    }

    @Override
    public PropertyType getType(){
        return PropertyType.FLOAT;
    }

    @Override
    public Float getCurrValue() {
        return currValue;
    }

    @Override
    public Object getPastValue() {
        return pastValue;
    }
}
