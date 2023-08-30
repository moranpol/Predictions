package details;

import menuChoice2.DtoTermination;

import java.util.Map;

public class DtoWorldInfo {

    private final Map<String, DtoEntityInfo> dtoEntityMap;
    private  final Map<String, DtoRuleInfo> dtoRuleMap;
    private final DtoTermination dtoTermination;
    private final Map<String, DtoEnvironmentInfo> dtoEnvironmentMap;

    public DtoWorldInfo(Map<String, DtoEntityInfo> dtoEntityMap, Map<String, DtoRuleInfo> dtoRuleMap, DtoTermination dtoTermination, Map<String, DtoEnvironmentInfo> dtoEnvironmentMap) {
        this.dtoEntityMap = dtoEntityMap;
        this.dtoRuleMap = dtoRuleMap;
        this.dtoTermination = dtoTermination;
        this.dtoEnvironmentMap = dtoEnvironmentMap;
    }

    public Map<String, DtoEntityInfo> getDtoEntityMap() {
        return dtoEntityMap;
    }

    public Map<String, DtoRuleInfo> getDtoRuleMap() {
        return dtoRuleMap;
    }

    public DtoTermination getDtoTermination() {
        return dtoTermination;
    }

    public Map<String, DtoEnvironmentInfo> getDtoEnvironmentMap() {
        return dtoEnvironmentMap;
    }
}
