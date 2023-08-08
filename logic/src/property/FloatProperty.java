package property;

import java.util.Objects;
import java.util.Random;

public class FloatProperty extends PropertyInstance{
    private final Range range;
    private float value;

    public FloatProperty(PropertyDefinition prop) {
        super(prop.getName());
        this.range = prop.getRange();
        if(prop.isRandomInit()){
            Random random = new Random();
            this.value = (float)this.range.getFrom() + random.nextFloat() * ((float)this.range.getTo() - (float)this.range.getFrom());
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
