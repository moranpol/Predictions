package menuChoice2;

import details.DtoEntityInfo;
import details.DtoRuleInfo;

import java.util.List;

public class DtoWorldInfo1 {
    private final List<DtoEntityInfo> dtoEntityList;
    private  final List<DtoRuleInfo> rules;
    private final DtoTermination termination;

    public DtoWorldInfo1(List<DtoEntityInfo> dtoEntityList, List<DtoRuleInfo> rules, DtoTermination termination) {
        this.dtoEntityList = dtoEntityList;
        this.rules = rules;
        this.termination = termination;
    }

    public List<DtoEntityInfo> getDtoEntityList() {
        return dtoEntityList;
    }

    public List<DtoRuleInfo> getRules() {
        return rules;
    }

    public DtoTermination getTermination() {
        return termination;
    }
}
