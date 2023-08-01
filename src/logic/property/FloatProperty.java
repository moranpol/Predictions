package logic.property;

import logic.enums.PropertyType;
import java.util.concurrent.ThreadLocalRandom;

class FloatProperty extends Property{
    private float from;
    private float to;
    private float value;

    protected FloatProperty(String nameValue, PropertyType typeValue, boolean isRandomInitValue,
                            float fromValue, float toValue, float init) {
        super(nameValue, typeValue, isRandomInitValue);
        this.from = fromValue;
        this.to = toValue;
        this.value = init;
    }

    protected FloatProperty(String nameValue, PropertyType typeValue, boolean isRandomInitValue, float init) {
        super(nameValue, typeValue, isRandomInitValue);
        this.from = (float)Double.NEGATIVE_INFINITY;;
        this.to = (float)Double.POSITIVE_INFINITY;;
        this.value = init;
    }

    protected FloatProperty(String nameValue, PropertyType typeValue, boolean isRandomInitValue,
                            float fromValue, float toValue) {
        super(nameValue, typeValue, isRandomInitValue);
        this.from = fromValue;
        this.to = toValue;
        this.value = ThreadLocalRandom.current().nextInt((int)from, (int)(to + 1));
    }

    protected FloatProperty(String nameValue, PropertyType typeValue, boolean isRandomInitValue){
        super(nameValue, typeValue, isRandomInitValue);
        this.from = (float)Double.NEGATIVE_INFINITY;;
        this.to = (float)Double.POSITIVE_INFINITY;;
        this.value = ThreadLocalRandom.current().nextInt();
    }
}
