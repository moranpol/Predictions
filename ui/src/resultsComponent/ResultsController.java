package resultsComponent;

import com.sun.javafx.css.StyleClassSet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pageComponent.PageController;
import results.simulationEnded.DtoSimulationEndedDetails;
import results.DtoSimulationChoice;
import results.simulations.DtoSimulationInfo;
import resultsComponent.executionDetails.ExecutionDetailsController;
import resultsComponent.executionList.ExecutionListController;
import resultsComponent.executionResults.ExecutionResultsController;
import resultsComponent.executionResults.simulationFailed.SimulationFailedController;

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
    private VBox executionList;

    @FXML
    private ExecutionListController executionListController;

    private ExecutionDetailsController executionDetailsController;

    private PageController pageController;

    public void setter(PageController pageController){
        setPageController(pageController);
        setExecutionListController();
    }

    public ExecutionListController getExecutionListController() {
        return executionListController;
    }

    private void setPageController(PageController pageController) {
        this.pageController = pageController;
    }

    private void setExecutionListController() {
        executionListController.setter(this, pageController);
    }

    public ExecutionDetailsController getExecutionDetailsController() {
        return executionDetailsController;
    }

    public void updateScreenBySimulationChoice(Integer id, boolean isRunning){
        DtoSimulationChoice simulationChoice = new DtoSimulationChoice(id);
        executionResultPane.getChildren().clear();
        loadExecutionDetailsController(simulationChoice);
        if(!isRunning){
            DtoSimulationInfo dtoSimulationInfo = pageController.getDtoSimulationInfo(simulationChoice);
            if(dtoSimulationInfo.getSimulationMode().equals("failed")){
                loadSimulationFailedController(dtoSimulationInfo);
            } else {
                DtoSimulationEndedDetails dtoSimulationEndedDetails = pageController.getDtoSimulationEndedDetails(simulationChoice);
                loadExecutionResultController(dtoSimulationEndedDetails);
            }
        }
    }

    public void clearResultsPane(){
        executionResultPane.getChildren().clear();
    }

    private void loadSimulationFailedController(DtoSimulationInfo dtoSimulationInfo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resultsComponent/executionResults/simulationFailed/SimulationFailed.fxml"));
            Parent executionFailed = loader.load();
            executionResultPane.getChildren().clear();
            executionResultPane.getChildren().add(executionFailed);

            SimulationFailedController simulationFailedController = loader.getController();
            simulationFailedController.setter(dtoSimulationInfo);
        } catch (IOException ignored) {
        }
    }

    private void loadExecutionDetailsController(DtoSimulationChoice simulationChoice) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resultsComponent/executionDetails/ExecutionDetails.fxml"));
            Parent executionDetails = loader.load();
            executionDetailsPane.getChildren().clear();
            executionDetailsPane.getChildren().add(executionDetails);

            executionDetailsController = loader.getController();
            executionDetailsController.setter(simulationChoice, pageController, executionListController, this);
        } catch (IOException ignored) {
        }
    }

    public void loadExecutionResultController(DtoSimulationEndedDetails simulationEndedDetails) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resultsComponent/executionResults/ExecutionResults.fxml"));
            Parent executionResults = loader.load();
            executionResultPane.getChildren().clear();
            executionResultPane.getChildren().add(executionResults);

            ExecutionResultsController executionResultsController = loader.getController();
            executionResultsController.setDtoSimulationEndedDetails(simulationEndedDetails);
            executionResultsController.setSimulationDetails();
        } catch (IOException ignored) {
        }
    }
}
