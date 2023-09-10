package pageComponent;
import detailsComponent.DetailsFullComponentController;
import headerComponent.HeaderController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import manager.LogicManager;
import newExecutionComponent.dtoEntities.DtoEntitiesPopulation;
import newExecutionComponent.dtoEntities.DtoEntityNames;
import newExecutionComponent.dtoEntities.DtoGrid;
import newExecutionComponent.NewExecutionController;
import newExecutionComponent.dtoEnvironment.DtoEnvironment;
import newExecutionComponent.dtoEnvironment.DtoEnvironmentInitialize;
import resultsComponent.ResultsController;

import java.io.IOException;
import java.util.List;

public class PageController {

    @FXML
    private GridPane header;

    @FXML
    private HeaderController headerController;

    @FXML
    private Pane paneBody;

    @FXML
    private SplitPane splitPane;

    private Double originalDividerPosition;

    private LogicManager logicManager;

    private DetailsFullComponentController detailsFullComponentController;

    private NewExecutionController newExecutionController;

    private ResultsController resultsController;

    @FXML
    public void initialize(){
        headerController.setPageController(this);
        logicManager = new LogicManager();
        originalDividerPosition = 0.2908; // Store your original value here
        setDivider();
    }

    private void setDivider(){
        splitPane.setDividerPositions(originalDividerPosition);

        splitPane.getDividers().get(0).positionProperty().addListener((observable, oldValue, newValue) -> {
            splitPane.setDividerPositions(originalDividerPosition);
        });
    }

    public LogicManager getLogicManager() {
        return logicManager;
    }

    public void loadDetailsComponent(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/detailsFullComponent.fxml"));
            Parent details = loader.load();
            paneBody.getChildren().clear();
            paneBody.getChildren().add(details);

            detailsFullComponentController = loader.getController();
            detailsFullComponentController.setPageController(this);
        }
        catch (IOException ignored) {
        }
    }

    public void loadNewExecutionComponent(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/newExecutionComponent/NewExecution.fxml"));
            Parent newExecution = loader.load();
            paneBody.getChildren().clear();
            paneBody.getChildren().add(newExecution);

            newExecutionController = loader.getController();
            newExecutionController.setPageController(this);
        } catch (IOException ignored) {
        }
    }

    public void loadResultComponent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resultsComponent/Results.fxml"));
            Parent results = loader.load();
            paneBody.getChildren().clear();
            paneBody.getChildren().add(results);

            resultsController = loader.getController();
            resultsController.setPageController(this);
        } catch (IOException ignored) {
        }
    }

    public DtoEntityNames getEntityNamesDto(){
        return logicManager.createEntityNameDto();
    }

    public Integer getMaxPopulationSize(){
        DtoGrid dtoGrid = logicManager.createGridDto();
        return dtoGrid.getRows() * dtoGrid.getCols();
    }

    public List<DtoEnvironment> getDtoEnvironment(){
        return logicManager.createDtoEnvironment();
    }

    public void startSimulation(List<DtoEnvironmentInitialize> dtoEnvironmentInitializeList, List<DtoEntitiesPopulation> dtoEntitiesPopulationList){
        loadResultComponent();
        logicManager.startSimulation(dtoEnvironmentInitializeList, dtoEntitiesPopulationList);
    }
}

