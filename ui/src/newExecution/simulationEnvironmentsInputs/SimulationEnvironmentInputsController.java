package newExecution.simulationEnvironmentsInputs;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import newExecution.NewExecutionController;

public class SimulationEnvironmentInputsController {

    @FXML
    public VBox environmentInputsVBox;

    private NewExecutionController newExecutionController;

    public void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
    }
}