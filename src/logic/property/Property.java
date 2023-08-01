package logic.property;

import logic.enums.PropertyType;

public abstract class Property {
    private final String name;
    private PropertyType type;
    private boolean isRandomInit;

    protected Property(String nameValue, PropertyType typeValue, boolean isRandomInitValue) {
        this.name = nameValue;
        this.type = typeValue;
        this.isRandomInit = isRandomInitValue;
    }
}
