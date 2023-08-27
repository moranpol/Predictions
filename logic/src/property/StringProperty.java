package property;

import enums.PropertyType;

import java.util.Objects;

public class StringProperty extends PropertyInstance{
    private String value;

    public StringProperty(String name, String value) {
        super(name);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        if (this.value.equals((String)value)){
            setCurrValueCounterByTicks(getCurrValueCounterByTicks() + 1);
        } else{
            setCurrValueCounterByTicks(1);
        }

        this.value = (String)value;
    }

    @Override
    public PropertyType getType(){
        return PropertyType.STRING;
    }
}
