package logic.property;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

class IntProperty extends Property{
    private int from;
    private int to;
    private int value;

    protected IntProperty(String nameValue, boolean isRandomInitValue, int fromValue, int toValue, int init) {
        super(nameValue, isRandomInitValue);
        this.from = fromValue;
        this.to = toValue;
        this.value = init;
    }

    protected IntProperty(String nameValue, boolean isRandomInitValue, int init) {
        super(nameValue, isRandomInitValue);
        this.from = (int)Double.NEGATIVE_INFINITY;;
        this.to = (int)Double.POSITIVE_INFINITY;;
        this.value = init;
    }

    protected IntProperty(String nameValue, boolean isRandomInitValue, int fromValue, int toValue) {
        super(nameValue, isRandomInitValue);
        this.from = fromValue;
        this.to = toValue;
        this.value = ThreadLocalRandom.current().nextInt(from, to + 1);
    }

    protected IntProperty(String nameValue, boolean isRandomInitValue){
        super(nameValue, isRandomInitValue);
        this.from = (int)Double.NEGATIVE_INFINITY;;
        this.to = (int)Double.POSITIVE_INFINITY;;
        this.value = ThreadLocalRandom.current().nextInt();
    }

    @Override
    public String toString() {
        String str = super.toString() + "\nType: Integer";
        if(this.from != (int)Double.NEGATIVE_INFINITY){
            str += "\nRange: " +this.from + "-" + this.to;
        }
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        IntProperty that = (IntProperty) o;
        return from == that.from && to == that.to && value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), from, to, value);
    }
}
