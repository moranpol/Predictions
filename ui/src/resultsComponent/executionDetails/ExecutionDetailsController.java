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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import pageComponent.PageController;
import results.DtoSimulationChoice;
import results.simulations.DtoSimulationInfo;
import results.simulations.DtoSimulationEntity;
import resultsComponent.executionDetails.buttons.RerunController;
import resultsComponent.executionDetails.buttons.RunningController;

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

    public void setTable(List<DtoSimulationEntity> simulationEntityList){
        Platform.runLater(() -> {
            ObservableList<DtoSimulationEntity> data = FXCollections.observableArrayList(simulationEntityList);
            tableView.setItems(data);
        });
    }

    public void setStopSimulation(Boolean stopSimulation) {
        this.stopSimulation = stopSimulation;
    }

    public void setter(DtoSimulationChoice simulationChoice, PageController pageController) {
        stopSimulation = false;
        setPageController(pageController);

        thread = new Thread(() -> {
            boolean stop = false;
            String simulationState = "running";
            while (!stop && !simulationState.equals("ended") && !simulationState.equals("failed")) {
                try {
                    DtoSimulationInfo simulationInfo = pageController.getDtoSimulationInfo(simulationChoice);
                    Platform.runLater( () -> {
                        setButtonsVBox(simulationInfo.getSimulationMode());
                        setLabels(simulationInfo);
                    });
                    setTable(simulationInfo.getSimulationEntities());
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
        });

        thread.start();
    }

    public void setPageController(PageController pageController) {
        this.pageController = pageController;
    }

    public void setButtonsVBox(String simulationMode){
        if(simulationMode.equals("running")){
            loadRunningController();
        } else{
            loadRerunController();
        }
    }

    private void loadRunningController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resultsComponent/executionDetails/buttons/Running.fxml"));
            Parent running = loader.load();
            buttonsVBox.getChildren().clear();
            buttonsVBox.getChildren().add(running);

            runningController = loader.getController();
            runningController.setExecutionDetailsController(this);
        }
        catch (IOException ignored) {
        }
    }

    private void loadRerunController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resultsComponent/executionDetails/buttons/Rerun.fxml"));
            Parent rerun = loader.load();
            buttonsVBox.getChildren().clear();
            buttonsVBox.getChildren().add(rerun);

            rerunController = loader.getController();
            rerunController.setExecutionDetailsController(this);
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