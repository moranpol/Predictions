package newExecutionComponent.simulationEnvironmentsInputs.inputTypes.booleanEnvironment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import newExecutionComponent.NewExecutionController;
import newExecutionComponent.StartButtonListener;
import newExecutionComponent.dtoEnvironment.DtoEnvironmentInitialize;

public class BooleanEnvironmentController implements StartButtonListener {

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

    public void setValueName(String valueName) {
        this.valueName.setText(valueName);
    }

    public void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
    }

    @Override
    public void startOnClicked() {
        if(isValueSet){
            Boolean choice = Boolean.parseBoolean(valueCBox.getValue().toString());
            newExecutionController.addToDtoEnvironmentInitializeList(new DtoEnvironmentInitialize(valueName.getText(), choice));
        }
    }

}
