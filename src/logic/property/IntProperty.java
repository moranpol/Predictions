package logic.property;

import logic.enums.PropertyType;
import java.util.concurrent.ThreadLocalRandom;

class IntProperty extends Property{
    private int from;
    private int to;
    private int value;

    protected IntProperty(String nameValue, PropertyType typeValue, boolean isRandomInitValue,
                          int fromValue, int toValue, int init) {
        super(nameValue, typeValue, isRandomInitValue);
        this.from = fromValue;
        this.to = toValue;
        this.value = init;
    }

    protected IntProperty(String nameValue, PropertyType typeValue, boolean isRandomInitValue, int init) {
        super(nameValue, typeValue, isRandomInitValue);
        this.from = (int)Double.NEGATIVE_INFINITY;;
        this.to = (int)Double.POSITIVE_INFINITY;;
        this.value = init;
    }

    protected IntProperty(String nameValue, PropertyType typeValue, boolean isRandomInitValue,
                          int fromValue, int toValue) {
        super(nameValue, typeValue, isRandomInitValue);
        this.from = fromValue;
        this.to = toValue;
        this.value = ThreadLocalRandom.current().nextInt(from, to + 1);
    }

    protected IntProperty(String nameValue, PropertyType typeValue, boolean isRandomInitValue){
        super(nameValue, typeValue, isRandomInitValue);
        this.from = (int)Double.NEGATIVE_INFINITY;;
        this.to = (int)Double.POSITIVE_INFINITY;;
        this.value = ThreadLocalRandom.current().nextInt();
    }
}
