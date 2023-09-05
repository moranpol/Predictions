package details;

import details.DtoAction.DtoAction;

import java.util.Map;

public class DtoRuleInfo {

    private final String name;
    private final DtoActivation dtoActivation;
    private final Map<String,DtoAction> dtoActionMap;

    public DtoRuleInfo(String name, DtoActivation dtoActivation, Map<String,DtoAction> dtoActionMap) {
        this.name = name;
        this.dtoActivation = dtoActivation;
        this.dtoActionMap = dtoActionMap;
    }

    public String getName() {
        return name;
    }

    public DtoActivation getDtoActivation() {
        return dtoActivation;
    }

    public Map<String,DtoAction> getDtoActionMap() {
        return dtoActionMap;
    }
}
