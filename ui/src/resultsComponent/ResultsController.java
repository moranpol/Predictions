package resultsComponent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pageComponent.PageController;
import results.simulationEnded.DtoSimulationEndedDetails;
import results.simulations.DtoSimulationInfo;
import results.DtoSimulationChoice;
import resultsComponent.executionDetails.ExecutionDetailsController;
import resultsComponent.executionList.ExecutionListController;
import resultsComponent.executionResults.ExecutionResultsController;

import java.io.IOException;

public class ResultsController {

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

    private ExecutionResultsController executionResultsController;

    private ExecutionDetailsController executionDetailsController;

    private PageController pageController;

    public void initialize(){
        executionListController.setResultsController(this);
    }

    public void setPageController(PageController pageController) {
        this.pageController = pageController;
    }

    public void setExecutionListController() {
        executionListController.setExecutionListView(pageController.getDtoSimulationInfoList());
    }

    public void updateScreenBySimulationChoice(Integer id, boolean isRunning){
        DtoSimulationChoice simulationChoice = new DtoSimulationChoice(id);
        loadExecutionDetailsController(simulationChoice);
        if(!isRunning){
            DtoSimulationEndedDetails simulation = pageController.getDtoSimulationEndedDetails(simulationChoice);
            loadExecutionResultController(simulation);
        }
    }

    private void loadExecutionDetailsController(DtoSimulationChoice simulationChoice) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resultsComponent/executionDetails/ExecutionDetails.fxml"));
            Parent executionDetails = loader.load();
            executionDetailsPane.getChildren().clear();
            executionDetailsPane.getChildren().add(executionDetails);

            executionDetailsController = loader.getController();
            executionDetailsController.setter(simulationChoice, pageController);
        } catch (IOException ignored) {
        }
    }

    private void loadExecutionResultController(DtoSimulationEndedDetails simulationEndedDetails) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resultsComponent/executionResults/ExecutionResults.fxml"));
            Parent executionResults = loader.load();
            executionResultPane.getChildren().clear();
            executionResultPane.getChildren().add(executionResults);

            executionResultsController = loader.getController();
            executionResultsController.setDtoSimulationEndedDetails(simulationEndedDetails);
            executionResultsController.setSimulationDetails();
        } catch (IOException ignored) {
        }
    }
}
