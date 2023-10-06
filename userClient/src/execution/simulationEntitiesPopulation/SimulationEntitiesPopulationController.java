package execution.simulationEntitiesPopulation;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import newExecution.dtoEntities.DtoEntitiesPopulation;
import newExecution.dtoEntities.DtoEntityNames;
import execution.newExecution.NewExecutionController;
import execution.simulationEntitiesPopulation.entityCount.EntityCountController;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SimulationEntitiesPopulationController {

    @FXML
    private Label currentPopulationCountLabel;

    @FXML
    private VBox entitiesCount;

    @FXML
    private Label maxPopulationCountLabel;

    private NewExecutionController newExecutionController;

    private List<EntityCountController> populationCountListeners;

    private final Map<String, EntityCountController> entitiesCounterMap = new HashMap<>();

    @FXML
    private Label maxPopulationLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label currentPopulationLabel;

    public void initialize(){
        Platform.runLater(() -> currentPopulationCountLabel.setText("0"));
        populationCountListeners = new LinkedList<>();
    }

    public void setter(NewExecutionController newExecutionController, DtoEntityNames dtoEntityNames, Integer maxPopulationSize){
        this.newExecutionController = newExecutionController;
        setMaxPopulationCount(maxPopulationSize);
        setEntitiesCount(dtoEntityNames, maxPopulationSize);
    }

    private void setEntitiesCount(DtoEntityNames dtoEntityNames, Integer maxPopulationSize){
        Platform.runLater(() -> entitiesCount.getChildren().clear());
        for (String name : dtoEntityNames.getEntityNames()){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/execution/simulationEntitiesPopulation/entityCount/EntityCount.fxml"));
                Parent entityCount = loader.load();
                Platform.runLater(() -> entitiesCount.getChildren().add(entityCount));

                EntityCountController entityCountController = loader.getController();
                entityCountController.setter(newExecutionController, name, maxPopulationSize, this);
                populationCountListeners.add(entityCountController);
                newExecutionController.addListenerToStartButton(entityCountController);
                entitiesCounterMap.put(name, entityCountController);
            } catch (IOException ignored) {
            }
        }
    }

    private void setMaxPopulationCount(Integer maxPopulationSize) {
        Platform.runLater(() -> maxPopulationCountLabel.setText(maxPopulationSize.toString()));
    }

    public void currentCounterChanged(int oldValue, int newValue){
        int oldCurrValue = Integer.parseInt(currentPopulationCountLabel.getText());
        int temp = oldCurrValue - oldValue + newValue;
        Platform.runLater(() -> currentPopulationCountLabel.setText(Integer.toString(temp)));

        for(EntityCountController populationCountListener : populationCountListeners) {
            populationCountListener.onChange(oldCurrValue, temp);
        }
    }

    public void rerunExecutionEntities(List<DtoEntitiesPopulation> dtoEntitiesPopulationList) {
        for(DtoEntitiesPopulation dtoEntityPopulation: dtoEntitiesPopulationList){
            entitiesCounterMap.get(dtoEntityPopulation.getName()).setEntityCount(dtoEntityPopulation.getPopulation());
        }
    }
}

