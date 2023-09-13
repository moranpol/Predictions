package property;

import enums.PropertyType;

public class BooleanProperty extends PropertyInstance {
    private Boolean currValue;
    private Boolean pastValue;


    public BooleanProperty(String name, boolean value) {
        super(name);
        currValue = value;
        pastValue = value;
    }

    @Override
    public PropertyType getType(){
        return PropertyType.BOOLEAN;
    }

    @Override
    public void setCurrValue(Object currValue) {
        pastValue = this.currValue;
        this.currValue = (Boolean) currValue;
    }

    @Override
    public Object getCurrValue() {
        return currValue;
    }

    @Override
    public Object getPastValue() {
        return pastValue;
    }
}
