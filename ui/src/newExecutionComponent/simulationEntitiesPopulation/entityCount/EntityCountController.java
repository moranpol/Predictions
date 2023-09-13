package newExecutionComponent.simulationEntitiesPopulation.entityCount;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import newExecution.dtoEntities.DtoEntitiesPopulation;
import newExecutionComponent.NewExecutionController;
import newExecutionComponent.StartButtonListener;
import newExecutionComponent.simulationEntitiesPopulation.SimulationEntitiesPopulationController;

public class EntityCountController implements StartButtonListener {

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
        entityCount.setEditable(true);
        isMyControllerChanged = false;
    }

    public void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
    }

    public void setEntitiesPopulationController(SimulationEntitiesPopulationController entitiesPopulationController) {
        this.entitiesPopulationController = entitiesPopulationController;
    }

    public void setMaxSize(Integer newMaxSize) {
        maxPopulationSize = newMaxSize;
        spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxPopulationSize, 0);
        entityCount.setValueFactory(spinnerValueFactory);

        entityCount.valueProperty().addListener((observable, oldValue, newValue) -> {
            isMyControllerChanged = true;

            if(newValue > maxPopulationSize){
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
            } else{
                isManuallyChanged = false;
            }
        });
    }

    public void setEntityName(String entityName) {
        this.entityName.setText(entityName);
    }

    public void onChange(int oldCurrValue, int newCurrValue) {
        if (!isMyControllerChanged) {
            maxPopulationSize = maxPopulationSize + (oldCurrValue - newCurrValue);

            spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxPopulationSize, spinnerValueFactory.getValue());
            entityCount.setValueFactory(spinnerValueFactory);
        }
        isMyControllerChanged = false;
    }

    @Override
    public void startOnClicked() {
        newExecutionController.addToDtoEntitiesPopulationList(new DtoEntitiesPopulation(entityName.getText(), spinnerValueFactory.getValue()));
    }
}
