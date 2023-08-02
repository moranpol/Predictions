package logic.property;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

class FloatProperty extends Property{
    private float from;
    private float to;
    private float value;

    protected FloatProperty(String nameValue, boolean isRandomInitValue, float fromValue, float toValue, float init) {
        super(nameValue, isRandomInitValue);
        this.from = fromValue;
        this.to = toValue;
        this.value = init;
    }

    protected FloatProperty(String nameValue, boolean isRandomInitValue, float init) {
        super(nameValue,isRandomInitValue);
        this.from = (float)Double.NEGATIVE_INFINITY;;
        this.to = (float)Double.POSITIVE_INFINITY;;
        this.value = init;
    }

    protected FloatProperty(String nameValue, boolean isRandomInitValue, float fromValue, float toValue) {
        super(nameValue, isRandomInitValue);
        this.from = fromValue;
        this.to = toValue;
        this.value = ThreadLocalRandom.current().nextInt((int)from, (int)(to + 1));
    }

    protected FloatProperty(String nameValue, boolean isRandomInitValue){
        super(nameValue, isRandomInitValue);
        this.from = (float)Double.NEGATIVE_INFINITY;;
        this.to = (float)Double.POSITIVE_INFINITY;;
        this.value = ThreadLocalRandom.current().nextInt();
    }

    @Override
    public String toString() {
        String str = super.toString() + "\nType: Float";
        if(this.from != (float)Double.NEGATIVE_INFINITY){
            str += "\nRange: " +this.from + "-" + this.to;
        }
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FloatProperty that = (FloatProperty) o;
        return Float.compare(from, that.from) == 0 && Float.compare(to, that.to) == 0 && Float.compare(value, that.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), from, to, value);
    }
}
