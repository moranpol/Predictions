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

    @FXML
    private Button futureButton;

    private ExecutionDetailsController executionDetailsController;

    private DtoSimulationChoice simulationChoice;

    @FXML
    void pauseButtonClick(ActionEvent event) {
        executionDetailsController.getPageController().pauseSimulation(simulationChoice);
        executionDetailsController.showResults(simulationChoice);
        resumeButton.setDisable(false);
        pauseButton.setDisable(true);
        futureButton.setDisable(false);
    }

    @FXML
    void resumeButtonClick(ActionEvent event) {
        executionDetailsController.getPageController().resumeSimulation(simulationChoice);
        executionDetailsController.getResultsController().clearResultsPane();
        resumeButton.setDisable(true);
        pauseButton.setDisable(false);
        futureButton.setDisable(true);
    }

    @FXML
    void FutureButtonClick(ActionEvent event) {
        executionDetailsController.getPageController().futureSimulation(simulationChoice);
        executionDetailsController.showResults(simulationChoice);
        pauseButton.setDisable(true);
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

    public void setter(String simulationMode) {
        futureButton.setDisable(!simulationMode.equals("pause"));
        resumeButton.setDisable(!simulationMode.equals("pause"));
        pauseButton.setDisable(simulationMode.equals("pause"));
    }
}
