package newExecutionComponent.dtoEnvironment;

public class DtoEnvironmentInitialize {
    private final String name;
    private final Object value;

    public DtoEnvironmentInitialize(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
