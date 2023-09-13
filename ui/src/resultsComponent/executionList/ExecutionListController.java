package resultsComponent.executionList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import results.simulations.DtoSimulationInfo;
import resultsComponent.ResultsController;

import java.util.List;

public class ExecutionListController {

    @FXML
    private ListView<String> executionListView;

    private ResultsController resultsController;

    public void initialize(){
        executionListViewChoice();
    }

    public void setResultsController(ResultsController resultsController) {
        this.resultsController = resultsController;
    }

    public void setExecutionListView(List<DtoSimulationInfo> simulationDetails){
        ObservableList<String> executionList = FXCollections.observableArrayList();
        for (DtoSimulationInfo simulation : simulationDetails){
            if(simulation.getSimulationMode().equals("running")){
                executionList.add("(Running) simulation id: " + simulation.getId());
            } else if(simulation.getSimulationMode().equals("failed")){
                executionList.add("(Failed) simulation id: " + simulation.getId());
            } else{
                executionList.add("(Ended) simulation id: " + simulation.getId());
            }
        }

        executionListView.setItems(executionList);
    }

    public void executionListViewChoice(){
        executionListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String[] parts = newValue.split(" ");
                Integer id = Integer.parseInt(parts[parts.length - 1]);
                if(newValue.contains("Running")){
                    resultsController.updateScreenBySimulationChoice(id, true);
                } else{
                    resultsController.updateScreenBySimulationChoice(id, false);
                }
            }
        });
    }
}