package details;

import java.util.List;

public class DtoWorldsList {
    private final List<String> worldsName;

    public DtoWorldsList(List<String> worldsName) {
        this.worldsName = worldsName;
    }

    public List<String> getWorldsName() {
        return worldsName;
    }
}
