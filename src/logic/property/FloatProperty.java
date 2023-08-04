package logic.property;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class FloatProperty extends PropertyInstance{
    private Range range;
    private float value;

    public FloatProperty(PropertyDefinition prop) {
        super(prop.getName());
        this.range = prop.getRange();
        if(prop.isRandomInit()){
            this.value = ThreadLocalRandom.current().nextInt((int)this.range.getFrom(), (int)(this.range.getTo()) + 1);
        }
        else{
            this.value = (float)prop.getValue() ;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FloatProperty that = (FloatProperty) o;
        return Float.compare(value, that.value) == 0 && Objects.equals(range, that.range);
    }

    @Override
    public int hashCode() {
        return Objects.hash(range, value);
    }
}
