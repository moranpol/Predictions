package newExecutionComponent.simulationEnvironmentsInputs.inputTypes.numberEnvironment;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import newExecution.dtoEnvironment.DtoEnvironment;
import newExecutionComponent.NewExecutionController;
import newExecutionComponent.StartButtonListener;
import newExecution.dtoEnvironment.DtoEnvironmentInitialize;
import newExecutionComponent.simulationEnvironmentsInputs.inputTypes.EnvironmentInputs;

public class NumberEnvironmentController implements StartButtonListener, EnvironmentInputs {

    @FXML
    private Label range;

    @FXML
    private Spinner<Double> rangeCount;

    @FXML
    private Label valueName;

    private NewExecutionController newExecutionController;

    private Boolean isValueSet = false;

    private Double from;

    private Double to;

    public void initialize(){
        rangeCount.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                isValueSet = true;
            }
        });
    }

    public void setter(NewExecutionController newExecutionController, DtoEnvironment environment){
        setNewExecutionController(newExecutionController);
        setValueName(environment.getName());
        setRange(environment.getFrom(), environment.getTo());
    }

    private void setValueName(String valueName) {
        this.valueName.setText(valueName);
    }

    private void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
    }

    private void setRange(Double from, Double to) {
        this.from = from;
        this.to = to;
        range.setText("Range: " + from + " - " + to);
        SpinnerValueFactory<Double> spinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(from, to, 0);
        rangeCount.setValueFactory(spinnerValueFactory);
    }

    @Override
    public void startOnClicked() {
        if(isValueSet){
            newExecutionController.addToDtoEnvironmentInitializeList(new DtoEnvironmentInitialize(valueName.getText(), rangeCount.getValue()));
        }
    }

    @Override
    public void rerunExecution(DtoEnvironmentInitialize dtoEnvironmentInitialize) {
        rangeCount.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(from, to, (Double) dtoEnvironmentInitialize.getValue()));
    }
}
