package resultsComponent.executionDetails.buttons;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import resultsComponent.executionDetails.ExecutionDetailsController;

public class RerunController {

    @FXML
    private Button rerunButton;

    private ExecutionDetailsController executionDetailsController;

    @FXML
    void rerunButtonClick(ActionEvent event) {

    }

    public void setExecutionDetailsController(ExecutionDetailsController executionDetailsController) {
        this.executionDetailsController = executionDetailsController;
    }
}
