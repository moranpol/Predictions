package details;

import java.util.List;

public class DtoEntityInfo {
    private final String name;
    private final List<DtoProperty> propertyDefinitions;

    public DtoEntityInfo(String name, List<DtoProperty> propertyDefinitions) {
        this.name = name;
        this.propertyDefinitions = propertyDefinitions;
    }

    public List<DtoProperty> getPropertyDefinitions() {
        return propertyDefinitions;
    }

    public String getName() {
        return name;
    }


}
