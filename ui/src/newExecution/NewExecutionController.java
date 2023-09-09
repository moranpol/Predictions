package newExecution;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import newExecution.simulationEntitiesPopulation.SimulationEntitiesPopulationController;
import newExecution.simulationEntitiesPopulation.entityCount.EntityCountController;
import newExecution.simulationEnvironmentsInputs.SimulationEnvironmentInputsController;
import pageComponent.PageController;

import java.util.ArrayList;
import java.util.List;

public class NewExecutionController {

    @FXML
    private Button clearButton;

    @FXML
    private Pane simulationEntitiesPopulation;

    @FXML
    private Pane simulationEnvironmentInputs;

    @FXML
    private Button startButton;

    @FXML
    private BorderPane entitiesPopulation;

    @FXML
    private SimulationEntitiesPopulationController entitiesPopulationController;

    @FXML
    private BorderPane environmentInputs;

    @FXML
    private SimulationEnvironmentInputsController environmentInputsController;

    private PageController pageController;

    private List<EntitiesPopulationDto> entitiesPopulationDtoList;

    private List<StartButtonListener> startNewExecutionListeners;

    @FXML
    public void initialize(){
        entitiesPopulationController.setNewExecutionController(this);
        environmentInputsController.setNewExecutionController(this);
        entitiesPopulationDtoList = new ArrayList<>();
        startNewExecutionListeners = new ArrayList<>();
    }

    public List<EntitiesPopulationDto> getEntitiesPopulationDtoList() {
        return entitiesPopulationDtoList;
    }

    public void setPageController(PageController pageController) {
        this.pageController = pageController;
        entitiesPopulationController.setMaxPopulationCount(pageController.getMaxPopulationSize());
        entitiesPopulationController.setEntitiesCount(pageController.getEntityNamesDto());
    }

    @FXML
    void clearButtonClick(ActionEvent event) {

    }

    @FXML
    void startButtonClick(ActionEvent event) {
        for (StartButtonListener listener : startNewExecutionListeners){
            listener.startOnClicked();
        }
    }

    public void addListenerToStartButton(EntityCountController entityCountController) {
        startNewExecutionListeners.add(entityCountController);
    }

    public void addToEntitiesPopulationDtoList(EntitiesPopulationDto entitiesPopulationDto) {
        entitiesPopulationDtoList.add(entitiesPopulationDto);
    }
}

