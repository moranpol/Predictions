package resultsComponent.executionList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import pageComponent.PageController;
import results.simulations.DtoSimulationInfo;
import resultsComponent.ResultsController;

import java.util.List;

public class ExecutionListController {

    @FXML
    private ListView<String> executionListView;

    private ResultsController resultsController;

    private Thread thread;

    private PageController pageController;

    public void setter(ResultsController resultsController, PageController pageController){
        setPageController(pageController);
        setResultsController(resultsController);

        thread = new Thread(() -> {
            boolean stop = false;
            while (!stop) {
                try {
                    List<DtoSimulationInfo> simulationInfo = pageController.getDtoSimulationInfoList();
                    Platform.runLater(() -> setExecutionListView(simulationInfo));

                    Thread.sleep(200);
                } catch (InterruptedException ignore) {
                    stop = true;
                }
            }
        });

        thread.start();
    }

    private void setPageController(PageController pageController) {
        this.pageController = pageController;
    }

    public void stopThread(){
        pageController.setResultsController(null);
        thread.interrupt();
    }

    private void setResultsController(ResultsController resultsController) {
        this.resultsController = resultsController;
    }

    public void setExecutionListView(List<DtoSimulationInfo> simulationDetails){
        ObservableList<String> executionList = FXCollections.observableArrayList();
        for (DtoSimulationInfo simulation : simulationDetails){
            if(simulation.getSimulationMode().equals("running")){
                executionList.add("(Running) simulation id: " + simulation.getId());
            } else if(simulation.getSimulationMode().equals("failed")){
                executionList.add("(Failed) simulation id: " + simulation.getId());
            } else if(simulation.getSimulationMode().equals("pause")){
            executionList.add("(Paused) simulation id: " + simulation.getId());
            } else{
                executionList.add("(Ended) simulation id: " + simulation.getId());
            }
        }

        executionListView.setItems(executionList);
    }

    @FXML
    public void executionListViewChoice(){
        if(resultsController.getExecutionDetailsController() != null){
            resultsController.getExecutionDetailsController().stopThread();
        }
        if (executionListView != null && executionListView.getSelectionModel().getSelectedItems() != null) {
            if(executionListView.getSelectionModel().getSelectedItems().get(0) != null) {
                String[] parts = executionListView.getSelectionModel().getSelectedItems().get(0).split(" ");
                Integer id = Integer.parseInt(parts[parts.length - 1]);
                resultsController.updateScreenBySimulationChoice(id, executionListView.getSelectionModel().getSelectedItems().get(0).contains("Running"));
            }
        }
    }
}