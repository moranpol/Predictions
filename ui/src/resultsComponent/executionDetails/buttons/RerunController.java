package resultsComponent.executionDetails.buttons;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import results.DtoSimulationChoice;
import resultsComponent.executionDetails.ExecutionDetailsController;

public class RerunController {

    @FXML
    private Button rerunButton;

    private ExecutionDetailsController executionDetailsController;

    private DtoSimulationChoice dtoSimulationChoice;

    @FXML
    void rerunButtonClick(ActionEvent event) {
        executionDetailsController.getPageController().loadNewExecutionComponent();
        executionDetailsController.getPageController().getDtoRerunExecution(dtoSimulationChoice);
    }

    public void setExecutionDetailsController(ExecutionDetailsController executionDetailsController) {
        this.executionDetailsController = executionDetailsController;
    }

    public void setDtoSimulationChoice(DtoSimulationChoice dtoSimulationChoice) {
        this.dtoSimulationChoice = dtoSimulationChoice;
    }
}
