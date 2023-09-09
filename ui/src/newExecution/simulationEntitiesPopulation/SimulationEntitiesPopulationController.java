package newExecution.simulationEntitiesPopulation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import newExecution.EntityNamesDto;
import newExecution.GridDto;
import newExecution.NewExecutionController;
import newExecution.simulationEntitiesPopulation.entityCount.EntityCountController;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SimulationEntitiesPopulationController {

    @FXML
    private Label currentPopulationCountLabel;

    @FXML
    private VBox entitiesCount;

    @FXML
    private Label maxPopulationCountLabel;

    private NewExecutionController newExecutionController;

    private List<EntityCountController> populationCountListeners;

    public void initialize(){
        currentPopulationCountLabel.setText("0");
        populationCountListeners = new LinkedList<>();
    }

    public void setNewExecutionController(NewExecutionController newExecutionController) {
        this.newExecutionController = newExecutionController;
    }

    public void setEntitiesCount(EntityNamesDto entityNamesDto){
        for (String name : entityNamesDto.getEntityNames()){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/newExecution/simulationEntitiesPopulation/entityCount/EntityCount.fxml"));
                Parent entityCount = loader.load();
                EntityCountController entityCountController = loader.getController();

                entityCountController.setMaxSize(Integer.parseInt(maxPopulationCountLabel.getText()));
                entitiesCount.getChildren().add(entityCount);
                entityCountController.setNewExecutionController(newExecutionController);
                entityCountController.setEntityName(name);
                entityCountController.setEntitiesPopulationController(this);
                populationCountListeners.add(entityCountController);
                newExecutionController.addListenerToStartButton(entityCountController);
            } catch (IOException e) {

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
}

