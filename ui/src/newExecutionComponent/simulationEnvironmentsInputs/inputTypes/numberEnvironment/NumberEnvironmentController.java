package newExecutionComponent.simulationEnvironmentsInputs.inputTypes.numberEnvironment;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import newExecutionComponent.NewExecutionController;
import newExecutionComponent.StartButtonListener;
import newExecution.dtoEnvironment.DtoEnvironmentInitialize;

public class NumberEnvironmentController implements StartButtonListener {

    @FXML
    private Label range;

    @FXML
    private Spinner<Double> rangeCount;

    @FXML
    private Label valueName;

    private NewExecutionController newExecutionController;

    private Boolean isValueSet = false;

    public void initialize(){
        rangeCount.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
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

    public void setRange(Double from, Double to) {
        range.setText("Range: " + from + " - " + to);
    }

    public void setRangeCount(Double from, Double to){
        SpinnerValueFactory<Double> spinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(from, to, 0);
        rangeCount.setValueFactory(spinnerValueFactory);
    }

    @Override
    public void startOnClicked() {
        if(isValueSet){
            newExecutionController.addToDtoEnvironmentInitializeList(new DtoEnvironmentInitialize(valueName.getText(), rangeCount.getValue()));
        }
    }
}
