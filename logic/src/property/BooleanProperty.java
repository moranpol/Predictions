package property;

import java.util.Objects;
import java.util.Random;

public class BooleanProperty extends PropertyInstance {
    boolean value;

    public Object getValue() {
        return value;
    }

    public BooleanProperty(PropertyDefinition prop) {
        super(prop.getName());
        if(prop.isRandomInit()){
            Random random = new Random();
            this.value = random.nextBoolean();
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
