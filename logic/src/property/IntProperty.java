package property;

import enums.PropertyType;
import helpers.ParseFunctions;

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
        Integer prevValue = this.value;
        value = ParseFunctions.parseNumericTypeToInt(value);
        if((Integer)value < this.range.getFrom()){
            this.value = this.range.getFrom().intValue();
        } else if ((Integer)value > this.range.getTo()) {
            this.value = this.range.getTo().intValue();
        } else{
            this.value = (Integer)value;
        }

        if (this.value.equals(prevValue)){
            setCurrValueCounterByTicks(getCurrValueCounterByTicks() + 1);
        } else{
            setCurrValueCounterByTicks(0);
        }
    }

    public Integer getValue() {
        return value;
    }
}
