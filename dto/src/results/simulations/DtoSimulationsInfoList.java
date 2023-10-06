package results.simulations;

import java.util.List;

public class DtoSimulationsInfoList {
    private final List<DtoSimulationInfo> simulationInfoList;

    public DtoSimulationsInfoList(List<DtoSimulationInfo> simulationInfoList) {
        this.simulationInfoList = simulationInfoList;
    }

    public List<DtoSimulationInfo> getSimulationInfoList() {
        return simulationInfoList;
    }
}
