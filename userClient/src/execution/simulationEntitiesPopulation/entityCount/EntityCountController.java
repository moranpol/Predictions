package execution.simulationEntitiesPopulation.entityCount;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import newExecution.dtoEntities.DtoEntitiesPopulation;
import execution.newExecution.NewExecutionController;
import execution.newExecution.SendButtonListener;
import execution.simulationEntitiesPopulation.SimulationEntitiesPopulationController;

public class EntityCountController implements SendButtonListener {

    @FXML
    private Spinner<Integer> entityCount;

    @FXML
    private Label entityName;

    private SimulationEntitiesPopulationController entitiesPopulationController;

    private NewExecutionController newExecutionController;

    private Integer maxPopulationSize;

    private Boolean isMyControllerChanged;

    private Boolean isManuallyChanged = false;

    private SpinnerValueFactory<Integer> spinnerValueFactory;

    public void initialize(){
        Platform.runLater(() -> entityCount.setEditable(true));
        isMyControllerChanged = false;
    }

    public void setter(NewExecutionController newExecutionController, String entityName, Integer maxSize, SimulationEntitiesPopulationController entitiesPopulationController){
        setNewExecutionController(newExecutionController);
        setEntitiesPopulationController(entitiesPopulationController);

        Platform.runLater(() -> {
            setEntityName(entityName);
            setMaxSize(maxSize);
        });
    }

    public void setEntityCount(Integer entityCount) {
        spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxPopulationSize, entityCount);
        Platform.runLater(() -> this.entityCount.setValueFactory(spinnerValueFactory));
    }

    private void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
    }

    private void setEntitiesPopulationController(SimulationEntitiesPopulationController entitiesPopulationController) {
        this.entitiesPopulationController = entitiesPopulationController;
    }

    private void setMaxSize(Integer newMaxSize) {
        maxPopulationSize = newMaxSize;
        spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxPopulationSize, 0);
        Platform.runLater(() -> {
            entityCount.setValueFactory(spinnerValueFactory);

            entityCount.valueProperty().addListener((observable, oldValue, newValue) -> {
                isMyControllerChanged = true;

                if (newValue > maxPopulationSize) {
                    newValue = maxPopulationSize;
                    isManuallyChanged = true;
                    entityCount.getValueFactory().setValue(newValue);
                    entitiesPopulationController.currentCounterChanged(oldValue, newValue);
                } else if (newValue < 0) {
                    newValue = 0;
                    isManuallyChanged = true;
                    entityCount.getValueFactory().setValue(newValue);
                    entitiesPopulationController.currentCounterChanged(oldValue, newValue);
                } else if (!isManuallyChanged) {
                    entitiesPopulationController.currentCounterChanged(oldValue, newValue);
                } else {
                    isManuallyChanged = false;
                }
            });
        });
    }

    private void setEntityName(String entityName) {
        this.entityName.setText(entityName);
    }

    public void onChange(int oldCurrValue, int newCurrValue) {
        if (!isMyControllerChanged) {
            maxPopulationSize = maxPopulationSize + (oldCurrValue - newCurrValue);

            spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxPopulationSize, spinnerValueFactory.getValue());
            Platform.runLater(() -> entityCount.setValueFactory(spinnerValueFactory));
        }

        isMyControllerChanged = false;
    }

    @Override
    public void sendOnClicked() {
        newExecutionController.addToDtoEntitiesPopulationList(new DtoEntitiesPopulation(entityName.getText(), spinnerValueFactory.getValue()));
    }
}
