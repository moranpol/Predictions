package logic.property;

import logic.enums.PropertyType;

public abstract class PropertyInstance {
    private String name;

    public PropertyInstance(String name) {
        this.name = name;
    }

    public static Class<?> getPropertyType(PropertyType propType) {
        Class<?> clazz = null;
        switch (propType) {
            case DECIMAL:
                clazz = IntProperty.class;
                break;
            case FLOAT:
                clazz = FloatProperty.class;
                break;
            case STRING:
                clazz = StringProperty.class;
                break;
            case BOOLEAN:
                clazz = BooleanProperty.class;
                break;
        }
        return clazz;
    }
}
