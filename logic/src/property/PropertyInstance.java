package property;

public abstract class PropertyInstance {
    private final String name;

    public abstract Object getValue();

    public PropertyInstance(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static PropertyInstance createPropertyInstance(PropertyDefinition property) {
        PropertyInstance newProperty = null;
        switch (property.getType()) {
            case DECIMAL:
                newProperty = new IntProperty(property);
                break;
            case FLOAT:
                newProperty = new FloatProperty(property);
                break;
            case STRING:
                newProperty = new StringProperty(property);
                break;
            case BOOLEAN:
                newProperty = new BooleanProperty(property);
                break;
        }
        return newProperty;
    }
}
