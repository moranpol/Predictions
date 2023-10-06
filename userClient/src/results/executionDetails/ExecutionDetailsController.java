package results.executionDetails;

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
import mainPage.MainPageController;
import refresher.ExecutionDetailsRefresher;
import results.simulationRunningDetails.DtoSimulationRunningDetails;
import results.simulations.DtoSimulationInfo;
import results.simulationRunningDetails.DtoSimulationEntity;
import results.ResultsController;
import results.executionDetails.buttons.RerunController;
import results.executionDetails.buttons.RunningController;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Timer;

public class ExecutionDetailsController implements Closeable {

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

    private ResultsController resultsController;

    private Timer timer;

    private ExecutionDetailsRefresher executionDetailsRefresher;

    private Integer simulationId;

    private Integer requestId;

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

    public ResultsController getResultsController() {
        return resultsController;
    }

    private void setTable(List<DtoSimulationEntity> simulationEntityList){
        ObservableList<DtoSimulationEntity> data = FXCollections.observableArrayList(simulationEntityList);
        Platform.runLater(() -> {
            if(data.isEmpty()){
                Label emptyLabel = new Label("No entities :(");
                tableView.setPlaceholder(emptyLabel);
            } else {
                tableView.setItems(data);
            }
        });
    }

    public void setter(Integer simulationId, Integer requestId, String simulationMode, ResultsController resultsController) {
        this.simulationId = simulationId;
        this.requestId = requestId;
        this.resultsController = resultsController;
        setSimulationLabel();
        refresher();
        initializeButtonsVBox(simulationMode);
        setButtonsVBox(simulationMode);
    }

    public void setController(DtoSimulationRunningDetails dtoSimulationRunningDetails){
        setLabels(dtoSimulationRunningDetails.getSeconds(), dtoSimulationRunningDetails.getTicks());
        setTable(dtoSimulationRunningDetails.getSimulationEntities());
        setButtonsVBox(dtoSimulationRunningDetails.getSimulationMode());
    }

    public void initializeButtonsVBox(String simulationMode){
        if(simulationMode.equals("running") || simulationMode.equals("pause")){
            loadRunningController(simulationMode);
        } else{
            loadRerunController();
        }
    }

    public void setButtonsVBox(String simulationMode){
        if(simulationMode.equals("failed") || simulationMode.equals("ended")){
            loadRerunController();
        }
    }

    private void loadRunningController(String simulationMode){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/results/executionDetails/buttons/Running.fxml"));
            Parent running = loader.load();
            Platform.runLater(() -> {
                buttonsVBox.getChildren().clear();
                buttonsVBox.getChildren().add(running);
            });

            runningController = loader.getController();
            runningController.setter(simulationMode, requestId, simulationId, this);
        }
        catch (IOException ignored) {
        }
    }

    private void loadRerunController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/results/executionDetails/buttons/Rerun.fxml"));
            Parent rerun = loader.load();
            Platform.runLater(() -> {
                buttonsVBox.getChildren().clear();
                buttonsVBox.getChildren().add(rerun);
            });

            rerunController = loader.getController();
            rerunController.setter(requestId, simulationId);
        }
        catch (IOException ignored) {
        }
    }

    public void setLabels(Integer seconds, Integer ticks){
        setSecondsCount(seconds);
        setTicksCount(ticks);
    }

    private void setSimulationLabel(){
        Platform.runLater(() -> simulationLabel.setText("Simulation id: " + simulationId + ", Requests id: " + requestId));
    }

    private void setSecondsCount(Integer seconds){
        Platform.runLater(() -> secondsCount.setText(seconds.toString()));
    }

    private void setTicksCount(Integer ticks){
        Platform.runLater(() -> ticksCount.setText(ticks.toString()));
    }

    public void refresher() {
        executionDetailsRefresher = new ExecutionDetailsRefresher(this::setController, simulationId, requestId);
        timer = new Timer();
        timer.schedule(executionDetailsRefresher, 1000, 1000);
    }

    @Override
    public void close() {
        executionDetailsRefresher.cancel();
        timer.cancel();
    }
}