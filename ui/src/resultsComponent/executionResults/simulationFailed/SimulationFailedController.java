package resultsComponent.executionResults.simulationFailed;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import results.simulations.DtoSimulationInfo;

public class SimulationFailedController {

    @FXML
    private Label simulationError;

    @FXML
    private Label simulationId;

    @FXML
    private Label simulationStartTime;

    public void setter(DtoSimulationInfo dtoSimulationInfo) {
        simulationError.setText(dtoSimulationInfo.getFailedReason());
        simulationId.setText("Simulation id: " + dtoSimulationInfo.getId());
        simulationStartTime.setText("Start time: " + dtoSimulationInfo.getStartTime());
    }
}
