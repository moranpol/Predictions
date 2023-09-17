package newExecutionComponent.simulationEntitiesPopulation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import newExecution.dtoEntities.DtoEntitiesPopulation;
import newExecution.dtoEntities.DtoEntityNames;
import newExecutionComponent.NewExecutionController;
import newExecutionComponent.simulationEntitiesPopulation.entityCount.EntityCountController;

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

    public void initialize(){
        currentPopulationCountLabel.setText("0");
        populationCountListeners = new LinkedList<>();
    }

    public void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
    }

    public void setEntitiesCount(DtoEntityNames dtoEntityNames){
        entitiesCount.getChildren().clear();
        for (String name : dtoEntityNames.getEntityNames()){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/newExecutionComponent/simulationEntitiesPopulation/entityCount/EntityCount.fxml"));
                Parent entityCount = loader.load();
                EntityCountController entityCountController = loader.getController();

                entityCountController.setMaxSize(Integer.parseInt(maxPopulationCountLabel.getText()));
                entitiesCount.getChildren().add(entityCount);
                entityCountController.setNewExecutionController(newExecutionController);
                entityCountController.setEntityName(name);
                entityCountController.setEntitiesPopulationController(this);
                populationCountListeners.add(entityCountController);
                newExecutionController.addListenerToStartButton(entityCountController);
                entitiesCounterMap.put(name, entityCountController);
            } catch (IOException ignored) {
            }
        }
    }

    public void setMaxPopulationCount(Integer maxPopulationSize) {
        maxPopulationCountLabel.setText(maxPopulationSize.toString());
    }

    public void currentCounterChanged(int oldValue, int newValue){
        int oldCurrValue = Integer.parseInt(currentPopulationCountLabel.getText());
        int temp = oldCurrValue;
        temp = temp - oldValue + newValue;
        currentPopulationCountLabel.setText(Integer.toString(temp));

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

