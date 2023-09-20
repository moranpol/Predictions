package details;

import details.DtoAction.DtoGridInfo;

import java.util.Map;

public class DtoWorldInfo {
    private final Map<String, DtoEntityInfo> dtoEntityMap;
    private  final Map<String, DtoRuleInfo> dtoRuleMap;
    private final DtoTermination dtoTermination;
    private final Map<String, DtoEnvironmentInfo> dtoEnvironmentMap;
    private final DtoGridInfo dtoGridInfo;

    public DtoWorldInfo(Map<String, DtoEntityInfo> dtoEntityMap, Map<String, DtoRuleInfo> dtoRuleMap, DtoTermination dtoTermination, Map<String, DtoEnvironmentInfo> dtoEnvironmentMap, DtoGridInfo dtoGridInfo) {
        this.dtoEntityMap = dtoEntityMap;
        this.dtoRuleMap = dtoRuleMap;
        this.dtoTermination = dtoTermination;
        this.dtoEnvironmentMap = dtoEnvironmentMap;
        this.dtoGridInfo = dtoGridInfo;
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

    public DtoGridInfo getDtoGridInfo() {
        return dtoGridInfo;
    }
}
