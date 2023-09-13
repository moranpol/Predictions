package resultsComponent.executionDetails.buttons;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import resultsComponent.executionDetails.ExecutionDetailsController;

public class RunningController {

    @FXML
    private Button pauseButton;

    @FXML
    private Button resumeButton;

    @FXML
    private Button stopButton;

    private ExecutionDetailsController executionDetailsController;

    @FXML
    void pauseButtonClick(ActionEvent event) {

    }

    @FXML
    void resumeButtonClick(ActionEvent event) {

    }

    @FXML
    void stopButtonClick(ActionEvent event) {
        executionDetailsController.setStopSimulation(true);
    }

    public void setExecutionDetailsController(ExecutionDetailsController executionDetailsController) {
        this.executionDetailsController = executionDetailsController;
    }
}
