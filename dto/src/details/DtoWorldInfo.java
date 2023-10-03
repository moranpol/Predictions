package details;

import java.util.List;
import java.util.Map;

public class DtoWorldInfo {
    private final Map<String, DtoEntityInfo> dtoEntityMap;
    private  final Map<String, DtoRuleInfo> dtoRuleMap;
    private final List<DtoEnvironmentInfo> dtoEnvironmentList;
    private final DtoGridInfo dtoGridInfo;
    private final String worldName;

    public DtoWorldInfo(Map<String, DtoEntityInfo> dtoEntityMap, Map<String, DtoRuleInfo> dtoRuleMap, List<DtoEnvironmentInfo> dtoEnvironmentList, DtoGridInfo dtoGridInfo, String worldName) {
        this.dtoEntityMap = dtoEntityMap;
        this.dtoRuleMap = dtoRuleMap;
        this.dtoEnvironmentList = dtoEnvironmentList;
        this.dtoGridInfo = dtoGridInfo;
        this.worldName = worldName;
    }

    public Map<String, DtoEntityInfo> getDtoEntityMap() {
        return dtoEntityMap;
    }

    public Map<String, DtoRuleInfo> getDtoRuleMap() {
        return dtoRuleMap;
    }

    public List<DtoEnvironmentInfo> getDtoEnvironmentList() {
        return dtoEnvironmentList;
    }

    public DtoGridInfo getDtoGridInfo() {
        return dtoGridInfo;
    }

    public String getWorldName() {
        return worldName;
    }
}
