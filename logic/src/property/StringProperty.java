package property;

import enums.PropertyType;

public class StringProperty extends PropertyInstance{
    private String currValue;
    private String pastValue;

    public StringProperty(String name, String value) {
        super(name);
        currValue = value;
        pastValue = value;
    }

    public String getCurrValue() {
        return currValue;
    }

    @Override
    public Object getPastValue() {
        return pastValue;
    }

    @Override
    public void setCurrValue(Object currValue) {
        pastValue = this.currValue;
        this.currValue = (String) currValue;
    }

    @Override
    public PropertyType getType(){
        return PropertyType.STRING;
    }
}
