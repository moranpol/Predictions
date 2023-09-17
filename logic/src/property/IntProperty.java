package property;

import enums.PropertyType;
import helpers.ParseFunctions;

public class IntProperty extends PropertyInstance{
    private final Range range;
    private Integer currValue;
    private Integer pastValue;

    public IntProperty(String name, Range range, Integer value) {
        super(name);
        this.range = range;
        currValue = value;
        pastValue = value;
    }

    @Override
    public PropertyType getType(){
        return PropertyType.DECIMAL;
    }

    @Override
    public void setCurrValue(Object currValue) {
        pastValue = this.currValue;
        currValue = ParseFunctions.parseNumericTypeToInt(currValue);
        if((Integer) currValue < this.range.getFrom()){
            this.currValue = this.range.getFrom().intValue();
        } else if ((Integer) currValue > this.range.getTo()) {
            this.currValue = this.range.getTo().intValue();
        } else{
            this.currValue = (Integer) currValue;
        }
    }

    public Integer getCurrValue() {
        return currValue;
    }

    @Override
    public Object getPastValue() {
        return pastValue;
    }
}
