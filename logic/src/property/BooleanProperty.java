package property;

import enums.PropertyType;

public class BooleanProperty extends PropertyInstance {
    boolean value;

    public BooleanProperty(String name, boolean value) {
        super(name);
        this.value = value;
    }

    @Override
    public PropertyType getType(){
        return PropertyType.BOOLEAN;
    }

    @Override
    public void setValue(Object value) {
        if (this.value == (Boolean)value){
            setCurrValueCounterByTicks(getCurrValueCounterByTicks() + 1);
        } else{
            setCurrValueCounterByTicks(0);
        }
        this.value = (Boolean)value;
    }

    @Override
    public Object getValue() {
        return value;
    }
}
