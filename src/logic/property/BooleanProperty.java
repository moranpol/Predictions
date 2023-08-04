package logic.property;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class BooleanProperty extends PropertyInstance {
    boolean value;

    public BooleanProperty(PropertyDefinition prop) {
        super(prop.getName());
        if(prop.isRandomInit()){
            int boolValue = ThreadLocalRandom.current().nextInt(0, 2);
            this.value = (boolValue == 1) ? true : false;
        }
        else{
            this.value = (boolean)prop.getValue();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooleanProperty that = (BooleanProperty) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
