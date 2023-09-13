package newExecutionComponent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import newExecution.dtoEntities.DtoEntitiesPopulation;
import newExecution.dtoEnvironment.DtoEnvironmentInitialize;
import newExecutionComponent.simulationEntitiesPopulation.SimulationEntitiesPopulationController;
import newExecutionComponent.simulationEnvironmentsInputs.SimulationEnvironmentInputsController;
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

    private List<DtoEntitiesPopulation> dtoEntitiesPopulationList;

    private List<StartButtonListener> startNewExecutionListeners;

    private List<DtoEnvironmentInitialize> dtoEnvironmentInitializeList;

    @FXML
    public void initialize(){
        dtoEntitiesPopulationList = new ArrayList<>();
        startNewExecutionListeners = new ArrayList<>();
        dtoEnvironmentInitializeList = new ArrayList<>();
    }

    public void setPageController(PageController pageController) {
        this.pageController = pageController;
        setEnvironmentAndEntities();
    }

    public void setEnvironmentAndEntities(){
        entitiesPopulationController.setNewExecutionController(this);
        environmentInputsController.setNewExecutionController(this);
        entitiesPopulationController.setMaxPopulationCount(pageController.getMaxPopulationSize());
        entitiesPopulationController.setEntitiesCount(pageController.getDtoEntityNames());
        environmentInputsController.setEnvironmentInputs(pageController.getDtoEnvironment());
    }

    @FXML
    void clearButtonClick(ActionEvent event) {
        startNewExecutionListeners.clear();
        dtoEnvironmentInitializeList.clear();
        dtoEntitiesPopulationList.clear();
        entitiesPopulationController.initialize();
        setEnvironmentAndEntities();
    }

    @FXML
    void startButtonClick(ActionEvent event) {
        for (StartButtonListener listener : startNewExecutionListeners){
            listener.startOnClicked();
        }
        pageController.startSimulation(dtoEnvironmentInitializeList, dtoEntitiesPopulationList);
    }

    public void addListenerToStartButton(StartButtonListener listener) {
        startNewExecutionListeners.add(listener);
    }

    public void addToDtoEntitiesPopulationList(DtoEntitiesPopulation dtoEntitiesPopulation) {
        dtoEntitiesPopulationList.add(dtoEntitiesPopulation);
    }

    public void addToDtoEnvironmentInitializeList(DtoEnvironmentInitialize dtoEnvironmentInitialize){
        dtoEnvironmentInitializeList.add(dtoEnvironmentInitialize);
    }
}

