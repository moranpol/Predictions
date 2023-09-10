package newExecutionComponent.simulationEnvironmentsInputs.inputTypes.stringEnvironment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import newExecutionComponent.NewExecutionController;
import newExecutionComponent.StartButtonListener;
import newExecutionComponent.dtoEnvironment.DtoEnvironmentInitialize;

public class StringEnvironmentController implements StartButtonListener {

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

    public void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
    }

    public void setValueName(String valueName) {
        this.valueName.setText(valueName);
    }

    @Override
    public void startOnClicked() {
        if(isValueSet){
            newExecutionController.addToDtoEnvironmentInitializeList(new DtoEnvironmentInitialize(valueName.getText(), valueTextFiled.getText()));
        }
    }
}
