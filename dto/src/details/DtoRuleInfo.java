package details;

import details.DtoAction.DtoAction;

import java.util.List;

public class DtoRuleInfo {

    private final String name;
    private final DtoActivation dtoActivation;
    private final List<DtoAction> dtoActions;

    public DtoRuleInfo(String name, DtoActivation dtoActivation, List<DtoAction> dtoActions) {
        this.name = name;
        this.dtoActivation = dtoActivation;
        this.dtoActions = dtoActions;
    }

    public String getName() {
        return name;
    }

    public DtoActivation getDtoActivation() {
        return dtoActivation;
    }

    public List<DtoAction> getDtoActions() {
        return dtoActions;
    }
}
