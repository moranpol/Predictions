package details;

import details.dtoAction.DtoAction;
import menuChoice2.DtoActivation;

import java.util.List;

public class DtoRuleInfo {

    private final String name;
    private final DtoActivation dtoActivation;
    private final List<DtoAction> dtoAction;

    public DtoRuleInfo(String name, DtoActivation dtoActivation, List<DtoAction> dtoAction) {
        this.name = name;
        this.dtoActivation = dtoActivation;
        this.dtoAction = dtoAction;
    }

    public String getName() {
        return name;
    }

    public DtoActivation getDtoActivation() {
        return dtoActivation;
    }

    public List<DtoAction> getDtoActionMap() {
        return dtoAction;
    }
}
