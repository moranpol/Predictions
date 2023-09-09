package newExecution.simulationEntitiesPopulation.entityCount;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import newExecution.EntitiesPopulationDto;
import newExecution.NewExecutionController;
import newExecution.StartButtonListener;
import newExecution.simulationEntitiesPopulation.SimulationEntitiesPopulationController;

public class EntityCountController implements StartButtonListener {

    @FXML
    private Spinner<Integer> entityCount;

    @FXML
    private Label entityName;

    private SimulationEntitiesPopulationController entitiesPopulationController;

    private NewExecutionController newExecutionController;

    private Integer maxPopulationSize;

    private Boolean isMyControllerChanged;

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
            entitiesPopulationController.currentCounterChanged(oldValue, newValue);
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
        newExecutionController.addToEntitiesPopulationDtoList(new EntitiesPopulationDto(entityName.getText(), spinnerValueFactory.getValue()));
    }
}
