package details;

import menuChoice2.DtoProperty;

import java.util.List;
import java.util.Map;

public class DtoEntityInfo {
    private final String name;
    private final Map<String,DtoProperty> propertyDefinitions;

    public DtoEntityInfo(String name, Map<String, DtoProperty> propertyDefinitions) {
        this.name = name;
        this.propertyDefinitions = propertyDefinitions;
    }

    public Map<String, DtoProperty> getPropertyDefinitions() {
        return propertyDefinitions;
    }

    public String getName() {
        return name;
    }


}
