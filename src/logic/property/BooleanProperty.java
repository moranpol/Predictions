package logic.property;

import logic.enums.PropertyType;

import java.util.concurrent.ThreadLocalRandom;

class BooleanProperty extends Property {
    boolean value;

    protected BooleanProperty(String nameValue, PropertyType typeValue, boolean isRandomInitValue) {
        super(nameValue, typeValue, isRandomInitValue);
        int boolValue = ThreadLocalRandom.current().nextInt(0, 2);
        this.value = (boolValue == 1) ? true : false;
    }

    protected BooleanProperty(String nameValue, PropertyType typeValue, boolean isRandomInitValue, boolean init) {
        super(nameValue, typeValue, isRandomInitValue);
        this.value = init;
    }
}
