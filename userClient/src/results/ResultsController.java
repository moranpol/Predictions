package results;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PageLayout;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import mainPage.MainPageController;
import results.executionDetails.ExecutionDetailsController;
import results.executionList.ExecutionListController;
import results.executionResults.ExecutionResultsController;
import results.executionResults.simulationFailed.SimulationFailedController;
import results.simulationEnded.DtoSimulationEndedDetails;
import results.simulations.DtoSimulationInfo;
import results.startExecutionDetails.StartExecutionDetailsController;

import java.io.IOException;

public class ResultsController {

    @FXML
    private HBox resultsHBox;

    @FXML
    private Pane executionDetailsPane;

    @FXML
    private Pane executionListPane;

    @FXML
    private Pane executionResultPane;

    @FXML
    private Pane executionStartDetailsPane;

    @FXML
    private VBox executionList;

    @FXML
    private ExecutionListController executionListController;

    private MainPageController mainPageController;

    public void setter(MainPageController mainPageController){
        this.mainPageController = mainPageController;
        executionListController.setter(this);    }

    public MainPageController getMainPageController() {
        return mainPageController;
    }

    public void updateScreenBySimulationChoice(Integer simulationId, Integer requestId, String simulationMode){
        Platform.runLater(() -> executionResultPane.getChildren().clear());
        loadExecutionDetailsController(simulationId, requestId, simulationMode);
        loadStartExecutionDetailsController(simulationId, requestId);
        updateExecutionResults(simulationId, requestId, simulationMode);
    }

    public void updateExecutionResults(Integer simulationId, Integer requestId, String simulationMode){
        if(simulationMode.equals("failed")){
            loadSimulationFailedController(simulationId, requestId);
        } else if(simulationMode.equals("ended")) {
            loadExecutionResultController(simulationId, requestId);
        }
    }

    private void loadSimulationFailedController(Integer simulationId, Integer requestId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/results/executionResults/simulationFailed/SimulationFailed.fxml"));
            Parent executionFailed = loader.load();
            Platform.runLater(() -> {
                executionResultPane.getChildren().clear();
                executionResultPane.getChildren().add(executionFailed);
            });

            SimulationFailedController simulationFailedController = loader.getController();
            simulationFailedController.setter(simulationId, requestId);
        } catch (IOException ignored) {
        }
    }

    private void loadExecutionDetailsController(Integer simulationId, Integer requestId, String simulationMode) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/results/executionDetails/ExecutionDetails.fxml"));
            Parent executionDetails = loader.load();
            Platform.runLater(() -> {
                executionDetailsPane.getChildren().clear();
                executionDetailsPane.getChildren().add(executionDetails);
            });

            ExecutionDetailsController executionDetailsController = loader.getController();
            executionDetailsController.setter(simulationId, requestId, simulationMode, this);
        } catch (IOException ignored) {
        }
    }

    public void loadExecutionResultController(Integer simulationId, Integer requestId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/results/executionResults/ExecutionResults.fxml"));
            Parent executionResults = loader.load();
            Platform.runLater(() -> {
                executionResultPane.getChildren().clear();
                executionResultPane.getChildren().add(executionResults);
            });

            ExecutionResultsController executionResultsController = loader.getController();
            executionResultsController.setter(simulationId, requestId);
        } catch (IOException ignored) {
        }
    }

    public void loadStartExecutionDetailsController(Integer simulationId, Integer requestId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/results/startExecutionDetails/StartExecutionDetails.fxml"));
            Parent startExecutionDetails = loader.load();
            Platform.runLater(() -> {
                executionStartDetailsPane.getChildren().clear();
                executionStartDetailsPane.getChildren().add(startExecutionDetails);
            });

            StartExecutionDetailsController startExecutionDetailsController = loader.getController();
            startExecutionDetailsController.setter(requestId, simulationId);
        } catch (IOException ignored) {
        }
    }
}
