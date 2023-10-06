package results.executionList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import refresher.SimulationsInfoRefresher;
import results.ResultsController;
import results.simulations.DtoSimulationInfo;
import results.simulations.DtoSimulationsInfoList;

import java.io.Closeable;
import java.util.Timer;

public class ExecutionListController implements Closeable {

    @FXML
    private ListView<String> executionListView;

    private ResultsController resultsController;

    private Timer timer;

    private SimulationsInfoRefresher simulationsInfoRefresher;

    public void setter(ResultsController resultsController){
        this.resultsController = resultsController;
        refresher();
    }

    public void setExecutionListView(DtoSimulationsInfoList simulationDetails){
        ObservableList<String> executionList = FXCollections.observableArrayList();
        Platform.runLater(() -> {
            for (DtoSimulationInfo simulation : simulationDetails.getSimulationInfoList()){
                if(simulation.getSimulationMode().equals("running")){
                    executionList.add("(Running) simulation id: " + simulation.getSimulationId() + " request id: " + simulation.getRequestId());
                } else if(simulation.getSimulationMode().equals("failed")){
                    executionList.add("(Failed) simulation id: " + simulation.getSimulationId() + " request id: " + simulation.getRequestId());
                } else if(simulation.getSimulationMode().equals("pause")){
                    executionList.add("(Paused) simulation id: " + simulation.getSimulationId() + " request id: " + simulation.getRequestId());
                } else{
                    executionList.add("(Ended) simulation id: " + simulation.getSimulationId() + " request id: " + simulation.getRequestId());
                }
            }

            executionListView.setItems(executionList);
        });
    }

    @FXML
    public void executionListViewChoice(){
        if (executionListView != null && executionListView.getSelectionModel().getSelectedItems() != null) {
            if(executionListView.getSelectionModel().getSelectedItems().get(0) != null) {
                String[] parts = executionListView.getSelectionModel().getSelectedItems().get(0).split(" ");
                Integer simulationId = Integer.parseInt(parts[3]);
                Integer requestId = Integer.parseInt(parts[parts.length - 1]);
                String simulationMode = parts[0].substring(1, parts[0].length() - 1).toLowerCase();
                resultsController.updateScreenBySimulationChoice(simulationId, requestId, simulationMode);
            }
        }
    }

    private void refresher() {
        simulationsInfoRefresher = new SimulationsInfoRefresher(resultsController.getMainPageController().getUserName(), this::setExecutionListView);
        timer = new Timer();
        timer.schedule(simulationsInfoRefresher, 1000, 1000);
    }

    @Override
    public void close() {
        timer.cancel();
        simulationsInfoRefresher.cancel();
    }
}