package logic.property;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

class BooleanProperty extends Property {
    boolean value;

    protected BooleanProperty(String nameValue, boolean isRandomInitValue) {
        super(nameValue, isRandomInitValue);
        int boolValue = ThreadLocalRandom.current().nextInt(0, 2);
        this.value = (boolValue == 1) ? true : false;
    }

    protected BooleanProperty(String nameValue, boolean isRandomInitValue, boolean init) {
        super(nameValue, isRandomInitValue);
        this.value = init;
    }

    @Override
    public String toString() {
        String str = super.toString() + "\nType: Boolean";
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BooleanProperty that = (BooleanProperty) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
}
