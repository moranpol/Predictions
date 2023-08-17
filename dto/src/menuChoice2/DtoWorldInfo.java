package menuChoice2;

import java.util.List;

public class DtoWorldInfo {
    private final List<DtoEntity> dtoEntityList;
    private  final List<DtoRule> rules;
    private final DtoTermination termination;

    public DtoWorldInfo(List<DtoEntity> dtoEntityList, List<DtoRule> rules, DtoTermination termination) {
        this.dtoEntityList = dtoEntityList;
        this.rules = rules;
        this.termination = termination;
    }

    public List<DtoEntity> getDtoEntityList() {
        return dtoEntityList;
    }

    public List<DtoRule> getRules() {
        return rules;
    }

    public DtoTermination getTermination() {
        return termination;
    }
}
