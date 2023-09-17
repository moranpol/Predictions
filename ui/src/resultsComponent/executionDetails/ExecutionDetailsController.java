package resultsComponent.executionDetails;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import pageComponent.PageController;
import results.DtoSimulationChoice;
import results.simulations.DtoSimulationInfo;
import results.simulations.DtoSimulationEntity;
import resultsComponent.ResultsController;
import resultsComponent.executionDetails.buttons.RerunController;
import resultsComponent.executionDetails.buttons.RunningController;
import resultsComponent.executionList.ExecutionListController;

import java.io.IOException;
import java.util.List;

public class ExecutionDetailsController {

    @FXML
    private VBox buttonsVBox;

    @FXML
    private Label secondsCount;

    @FXML
    private Label simulationLabel;

    @FXML
    private Label ticksCount;

    @FXML
    private TableView<DtoSimulationEntity> tableView;

    @FXML
    private TableColumn<DtoSimulationEntity, Integer> entityAmountCol;

    @FXML
    private TableColumn<DtoSimulationEntity, String> entityNameCol;

    private RerunController rerunController;

    private RunningController runningController;

    private PageController pageController;

    private Boolean stopSimulation;

    private Thread thread;

    private ExecutionListController executionListController;

    private ResultsController resultsController;

    public PageController getPageController() {
        return pageController;
    }

    public void initialize(){
        entityNameCol.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(cellData.getValue().getName());
            return property;
        });
        entityAmountCol.setCellValueFactory(cellData -> {
            SimpleIntegerProperty property = new SimpleIntegerProperty();
            property.setValue(cellData.getValue().getPopulation());
            return property.asObject();
        });
    }

    public void stopThread() {
        pageController.setExecutionDetailsController(null);
        thread.interrupt();
    }

    private void setTable(List<DtoSimulationEntity> simulationEntityList){
        ObservableList<DtoSimulationEntity> data = FXCollections.observableArrayList(simulationEntityList);
        if(data.isEmpty()){
            Label emptyLabel = new Label("No entities :(");
            tableView.setPlaceholder(emptyLabel);
        } else {
            tableView.setItems(data);
        }
    }

    private void setResultsController(ResultsController resultsController){
        this.resultsController = resultsController;
    }

    public void setStopSimulation(Boolean stopSimulation) {
        this.stopSimulation = stopSimulation;
    }

    private void setExecutionListController(ExecutionListController executionListController) {
        this.executionListController = executionListController;
    }

    public void setter(DtoSimulationChoice simulationChoice, PageController pageController, ExecutionListController executionListController, ResultsController resultsController) {
        stopSimulation = false;
        setPageController(pageController);
        pageController.setExecutionDetailsController(this);
        setExecutionListController(executionListController);
        setResultsController(resultsController);
        DtoSimulationInfo simulationInfo = pageController.getDtoSimulationInfo(simulationChoice);
        setButtonsVBox(simulationInfo.getSimulationMode(), simulationChoice);
        setLabels(simulationInfo);
        setThread(simulationChoice);
    }

    public void setThread(DtoSimulationChoice simulationChoice){
        thread = new Thread(() -> {
            boolean stop = false;
            String simulationState = "running";
            while (!stop && !simulationState.equals("ended") && !simulationState.equals("failed")) {
                try {
                    DtoSimulationInfo simulationInfo = pageController.getDtoSimulationInfo(simulationChoice);
                    Platform.runLater( () -> {
                        setLabels(simulationInfo);
                        setTable(simulationInfo.getSimulationEntities());
                    });
                    simulationState = simulationInfo.getSimulationMode();
                    if(stopSimulation) {
                        stop = true;
                        pageController.stopSimulation(simulationChoice);
                    }

                    Thread.sleep(200);
                } catch (InterruptedException ignore) {
                    stop = true;
                }
            }
            simulationState = pageController.getDtoSimulationInfo(simulationChoice).getSimulationMode();
            if(simulationState.equals("ended")){
                Platform.runLater(() -> {
                    resultsController.loadExecutionResultController(pageController.getDtoSimulationEndedDetails(simulationChoice));
                    setButtonsVBox("ended", simulationChoice);
                });
            }
        });

        thread.start();
    }

    public void setPageController(PageController pageController) {
        this.pageController = pageController;
    }

    public void setButtonsVBox(String simulationMode, DtoSimulationChoice simulationChoice){
        if(simulationMode.equals("running") || simulationMode.equals("pause")){
            loadRunningController(simulationChoice);
        } else{
            loadRerunController(simulationChoice);
        }
    }

    private void loadRunningController(DtoSimulationChoice simulationChoice){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resultsComponent/executionDetails/buttons/Running.fxml"));
            Parent running = loader.load();
            buttonsVBox.getChildren().clear();
            buttonsVBox.getChildren().add(running);

            runningController = loader.getController();
            runningController.setExecutionDetailsController(this);
            runningController.setSimulationChoice(simulationChoice);
        }
        catch (IOException ignored) {
        }
    }

    private void loadRerunController(DtoSimulationChoice simulationChoice){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resultsComponent/executionDetails/buttons/Rerun.fxml"));
            Parent rerun = loader.load();
            buttonsVBox.getChildren().clear();
            buttonsVBox.getChildren().add(rerun);

            rerunController = loader.getController();
            rerunController.setExecutionDetailsController(this);
            rerunController.setDtoSimulationChoice(simulationChoice);
        }
        catch (IOException ignored) {
        }
    }

    public void setLabels(DtoSimulationInfo simulationDetails){
        setSimulationLabel(simulationDetails.getId());
        setSecondsCount(simulationDetails.getSeconds());
        setTicksCount(simulationDetails.getTicks());
    }

    private void setSimulationLabel(Integer id){
        simulationLabel.setText("Simulation id: " + id);
    }

    private void setSecondsCount(Integer seconds){
        secondsCount.setText(seconds.toString());
    }

    private void setTicksCount(Integer ticks){
        ticksCount.setText(ticks.toString());
    }
}