package execution.simulationEnvironmentsInputs.inputTypes.stringEnvironment;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import newExecution.dtoEnvironment.DtoEnvironment;
import execution.newExecution.NewExecutionController;
import execution.newExecution.SendButtonListener;
import newExecution.dtoEnvironment.DtoEnvironmentInitialize;
import execution.simulationEnvironmentsInputs.inputTypes.EnvironmentInputs;

public class StringEnvironmentController implements SendButtonListener, EnvironmentInputs {

    @FXML
    private Label valueName;

    @FXML
    private TextField valueTextFiled;

    private NewExecutionController newExecutionController;

    private Boolean isValueSet = false;

    public void initialize(){
        valueTextFiled.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                isValueSet = true;
            }
        });
    }

    public void setter(NewExecutionController newExecutionController, DtoEnvironment environment){
        setNewExecutionController(newExecutionController);
        Platform.runLater(() -> setValueName(environment.getName()));
    }

    private void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
    }

    private void setValueName(String valueName) {
        this.valueName.setText(valueName);
    }

    @Override
    public void sendOnClicked() {
        if(isValueSet){
            newExecutionController.addToDtoEnvironmentInitializeList(new DtoEnvironmentInitialize(valueName.getText(), valueTextFiled.getText()));
        }
    }

    @Override
    public void rerunExecution(DtoEnvironmentInitialize dtoEnvironmentInitialize) {
        valueTextFiled.setText(dtoEnvironmentInitialize.getValue().toString());
    }
}
