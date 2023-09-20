package pageComponent;

import detailsComponent.DetailsFullComponentController;
import header.DtoSimulationQueue;
import headerComponent.HeaderController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import manager.LogicManager;
import newExecution.dtoEntities.DtoEntitiesPopulation;
import newExecution.dtoEntities.DtoEntityNames;
import newExecution.dtoEntities.DtoGrid;
import newExecutionComponent.NewExecutionController;
import newExecution.dtoEnvironment.DtoEnvironment;
import newExecution.dtoEnvironment.DtoEnvironmentInitialize;
import results.simulationEnded.DtoSimulationEndedDetails;
import results.simulations.DtoSimulationInfo;
import results.DtoSimulationChoice;
import resultsComponent.ResultsController;
import resultsComponent.executionDetails.ExecutionDetailsController;

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

    @FXML
    private ScrollPane scrollPane;

    private Double originalDividerPosition;

    private LogicManager logicManager;

    private DetailsFullComponentController detailsFullComponentController;

    private NewExecutionController newExecutionController;

    private ResultsController resultsController;

    private ExecutionDetailsController executionDetailsController;

    private Stage primaryStage;

    private Boolean isAnimation = false;

    @FXML
    public void initialize(){
        logicManager = new LogicManager();
        headerController.setter(this);
        originalDividerPosition = 0.2908;
        setDivider();
    }

    public void setAnimation(Boolean animation) {
        isAnimation = animation;
        if(isAnimation){
            if(detailsFullComponentController != null){
                detailsFullComponentController.getAnimation().startRotationAnimation();
            }
            if(newExecutionController != null){
                newExecutionController.getColorAnimationSecondPage().startColorChange();
            }
        } else {
            if(detailsFullComponentController != null){
                detailsFullComponentController.getAnimation().stopRotationAnimation();
            }
            if(newExecutionController != null){
                newExecutionController.getColorAnimationSecondPage().stopColorChange();
            }
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void setDivider(){
        splitPane.setDividerPositions(originalDividerPosition);

        splitPane.getDividers().get(0).positionProperty().addListener((observable, oldValue, newValue) -> {
            splitPane.setDividerPositions(originalDividerPosition);
        });
    }

    public void setExecutionDetailsController(ExecutionDetailsController executionDetailsController) {
        this.executionDetailsController = executionDetailsController;
    }

    public void setResultsController(ResultsController resultsController) {
        this.resultsController = resultsController;
    }

    public HeaderController getHeaderController() {
        return headerController;
    }

    public LogicManager getLogicManager() {
        return logicManager;
    }

    public void clearPaneBody() {
        paneBody.getChildren().clear();
    }

    public void loadDetailsComponent(){
        if(executionDetailsController != null){
            executionDetailsController.stopThread();
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/DetailsFullComponent.fxml"));
            Parent details = loader.load();
            paneBody.getChildren().clear();
            paneBody.getChildren().add(details);

            detailsFullComponentController = loader.getController();
            detailsFullComponentController.setPageController(this);
            if(isAnimation){
               detailsFullComponentController.getAnimation().startRotationAnimation();
            }
            stopExecutionListThread();
        }
        catch (IOException ignored) {
        }
    }

    public void loadNewExecutionComponent(){
        if(executionDetailsController != null){
            executionDetailsController.stopThread();
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/newExecutionComponent/NewExecution.fxml"));
            Parent newExecution = loader.load();
            paneBody.getChildren().clear();
            paneBody.getChildren().add(newExecution);

            newExecutionController = loader.getController();
            newExecutionController.setPageController(this);
            newExecutionController.setColorAnimationSecondPage();
            if(isAnimation){
                newExecutionController.getColorAnimationSecondPage().startColorChange();
            }
            stopExecutionListThread();
        } catch (IOException ignored) {
        }
    }

    public void stopExecutionListThread(){
        if(resultsController != null && resultsController.getExecutionListController() != null){
            resultsController.getExecutionListController().stopThread();
        }
    }

    public void loadResultsComponent() {
        if(executionDetailsController != null){
            executionDetailsController.stopThread();
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resultsComponent/Results.fxml"));
            Parent results = loader.load();
            paneBody.getChildren().clear();
            paneBody.getChildren().add(results);

            resultsController = loader.getController();
            resultsController.setter(this);
        } catch (IOException ignored) {
        }
    }

    public DtoEntityNames getDtoEntityNames(){
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
        loadResultsComponent();
        logicManager.startSimulation(dtoEnvironmentInitializeList, dtoEntitiesPopulationList);
    }

    public DtoSimulationEndedDetails getDtoSimulationEndedDetails(DtoSimulationChoice simulationChoice){
        return logicManager.createDtoSimulationEndedDetails(simulationChoice);
    }

    public List<DtoSimulationInfo> getDtoSimulationInfoList(){
        return logicManager.createDtoSimulationInfoList();
    }

    public DtoSimulationInfo getDtoSimulationInfo(DtoSimulationChoice simulationChoice){
        return logicManager.createDtoSimulationInfo(simulationChoice);
    }

    public void stopSimulation(DtoSimulationChoice simulationChoice) {
        logicManager.stopSimulation(simulationChoice);
    }

    public void pauseSimulation(DtoSimulationChoice simulationChoice){
        logicManager.pauseSimulation(simulationChoice);
    }

    public void resumeSimulation(DtoSimulationChoice simulationChoice){
        logicManager.resumeSimulation(simulationChoice);
    }

    public void getDtoRerunExecution(DtoSimulationChoice simulationChoice){
        newExecutionController.rerunExecution(logicManager.createDtoRerunExecution(simulationChoice));
    }

    public DtoSimulationQueue getDtoSimulationQueue(){
        return logicManager.createDtoSimulationQueue();
    }

    public void setPageColor(String selectedColor) {
        Scene scene = primaryStage.getScene();
        scene.getStylesheets().clear();
        switch (selectedColor){
            case "Light-blue":
                scene.getStylesheets().add("/pageComponent/style/bluePage.css");
                break;
            case"Pink":
                scene.getStylesheets().add("/pageComponent/style/pinkPage.css");
                break;
            case "Default":
                scene.getStylesheets().add("/pageComponent/style/defaultPage.css");
                break;
        }
    }

    public void futureSimulation(DtoSimulationChoice simulationChoice) {
        logicManager.futureSimulation(simulationChoice);
    }
}

