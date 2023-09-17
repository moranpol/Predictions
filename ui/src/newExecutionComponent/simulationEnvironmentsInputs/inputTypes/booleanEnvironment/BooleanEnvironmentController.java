package newExecutionComponent.simulationEnvironmentsInputs.inputTypes.booleanEnvironment;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import newExecution.dtoEnvironment.DtoEnvironment;
import newExecutionComponent.NewExecutionController;
import newExecutionComponent.StartButtonListener;
import newExecution.dtoEnvironment.DtoEnvironmentInitialize;
import newExecutionComponent.simulationEnvironmentsInputs.inputTypes.EnvironmentInputs;

public class BooleanEnvironmentController implements StartButtonListener, EnvironmentInputs {

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
        setValueName(environment.getName());
    }

    private void setValueName(String valueName) {
        this.valueName.setText(valueName);
    }

    private void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
    }

    @Override
    public void startOnClicked() {
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
