package resultsComponent.executionDetails.buttons;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import results.DtoSimulationChoice;
import resultsComponent.executionDetails.ExecutionDetailsController;

public class RunningController {

    @FXML
    private Button pauseButton;

    @FXML
    private Button resumeButton;

    @FXML
    private Button stopButton;

    private ExecutionDetailsController executionDetailsController;

    private DtoSimulationChoice simulationChoice;

    public void initialize(){
        resumeButton.setDisable(true);
    }

    @FXML
    void pauseButtonClick(ActionEvent event) {
        executionDetailsController.getPageController().pauseSimulation(simulationChoice);
        resumeButton.setDisable(false);
        pauseButton.setDisable(true);
    }

    @FXML
    void resumeButtonClick(ActionEvent event) {
        executionDetailsController.getPageController().resumeSimulation(simulationChoice);
        resumeButton.setDisable(true);
        pauseButton.setDisable(false);
    }

    @FXML
    void stopButtonClick(ActionEvent event) {
        executionDetailsController.setStopSimulation(true);
    }

    public void setSimulationChoice(DtoSimulationChoice simulationChoice) {
        this.simulationChoice = simulationChoice;
    }

    public void setExecutionDetailsController(ExecutionDetailsController executionDetailsController) {
        this.executionDetailsController = executionDetailsController;
    }
}
