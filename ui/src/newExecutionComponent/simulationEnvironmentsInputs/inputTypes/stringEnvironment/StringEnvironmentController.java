package newExecutionComponent.simulationEnvironmentsInputs.inputTypes.stringEnvironment;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import newExecution.dtoEnvironment.DtoEnvironment;
import newExecutionComponent.NewExecutionController;
import newExecutionComponent.StartButtonListener;
import newExecution.dtoEnvironment.DtoEnvironmentInitialize;
import newExecutionComponent.simulationEnvironmentsInputs.inputTypes.EnvironmentInputs;

public class StringEnvironmentController implements StartButtonListener, EnvironmentInputs {

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
        setValueName(environment.getName());
    }

    private void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
    }

    private void setValueName(String valueName) {
        this.valueName.setText(valueName);
    }

    @Override
    public void startOnClicked() {
        if(isValueSet){
            newExecutionController.addToDtoEnvironmentInitializeList(new DtoEnvironmentInitialize(valueName.getText(), valueTextFiled.getText()));
        }
    }

    @Override
    public void rerunExecution(DtoEnvironmentInitialize dtoEnvironmentInitialize) {
        valueTextFiled.setText(dtoEnvironmentInitialize.getValue().toString());
    }
}
