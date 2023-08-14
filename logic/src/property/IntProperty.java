package property;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class IntProperty extends PropertyInstance{
    private final Range range;
    private Integer value;

    public IntProperty(PropertyDefinition prop) {
        super(prop.getName());
        this.range = prop.getRange();
        if(prop.isRandomInit()){
            this.value = ThreadLocalRandom.current().nextInt(this.range.getFrom().intValue(),
                    (this.range.getTo().intValue()) + 1);
        }
        else{
            this.value = (int)prop.getValue() ;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntProperty that = (IntProperty) o;
        return value == that.value && Objects.equals(range, that.range);
    }

    @Override
    public int hashCode() {
        return Objects.hash(range, value);
    }
}
