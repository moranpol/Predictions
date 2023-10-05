package execution.simulationEnvironmentsInputs.inputTypes.booleanEnvironment;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import newExecution.dtoEnvironment.DtoEnvironment;
import execution.newExecution.NewExecutionController;
import execution.newExecution.SendButtonListener;
import newExecution.dtoEnvironment.DtoEnvironmentInitialize;
import execution.simulationEnvironmentsInputs.inputTypes.EnvironmentInputs;

public class BooleanEnvironmentController implements SendButtonListener, EnvironmentInputs {

    @FXML
    private ComboBox<Object> valueCBox;

    @FXML
    private Label valueName;

    private NewExecutionController newExecutionController;

    private Boolean isValueSet = false;

    public void initialize(){
        valueCBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                isValueSet = true;
            }
        });
    }

    public void setter(NewExecutionController newExecutionController, DtoEnvironment environment){
        setNewExecutionController(newExecutionController);
        Platform.runLater(() -> setValueName(environment.getName()));
    }

    private void setValueName(String valueName) {
        this.valueName.setText(valueName);
    }

    private void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
    }

    @Override
    public void sendOnClicked() {
        if(isValueSet){
            Boolean choice = Boolean.parseBoolean(valueCBox.getValue().toString());
            newExecutionController.addToDtoEnvironmentInitializeList(new DtoEnvironmentInitialize(valueName.getText(), choice));
        }
    }

    @Override
    public void rerunExecution(DtoEnvironmentInitialize dtoEnvironmentInitialize) {
        switch (dtoEnvironmentInitialize.getValue().toString()){
            case "true":
                valueCBox.setValue("True");
                break;
            case "false":
                valueCBox.setValue("False");
                break;
        }
    }
}
