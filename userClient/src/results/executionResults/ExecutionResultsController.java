package results.executionResults;

import alert.AlertDialog;
import com.google.gson.Gson;
import http.HttpClientUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import results.simulationEnded.DtoPropertyHistogram;
import results.simulationEnded.DtoSimulationEndedDetails;
import results.simulationEnded.DtoPropertyResults;
import results.executionResults.resultsTypes.consistency.ConsistencyController;
import results.executionResults.resultsTypes.entityQuantity.EntityQuantityController;
import results.executionResults.resultsTypes.histogram.HistogramController;
import results.simulationFailed.DtoSimulationFailedDetails;

import java.io.IOException;
import java.util.Map;

public class ExecutionResultsController {

    @FXML
    private ComboBox<String> analyseTypeCBox;

    @FXML
    private ComboBox<String> entityCBox;

    @FXML
    private ComboBox<String> informationCBox;

    @FXML
    private ComboBox<String> propertyCBox;

    @FXML
    private Pane resultsPane;

    @FXML
    private Label simulationDetails;

    @FXML
    private Label simulationStartTime;

    private EntityQuantityController entityQuantityController;

    private HistogramController histogramController;

    private ConsistencyController consistencyController;

    private DtoSimulationEndedDetails simulationEndedDetails;

    public void initialize(){
        entityCBox.setVisible(false);
        propertyCBox.setVisible(false);
        informationCBox.setVisible(false);
    }

    public void setter(Integer simulationId, Integer requestId) {
        String finalUrl = HttpUrl
                .parse("http://localhost:8080/predictions/simulationResultsDetails")
                .newBuilder()
                .addQueryParameter("request id", requestId.toString())
                .addQueryParameter("simulation id", simulationId.toString())
                .build()
                .toString();

        HttpClientUtil.runAsyncGet(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                AlertDialog.showError(e.getMessage());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    assert response.body() != null;
                    simulationEndedDetails = gson.fromJson(response.body().charStream(), DtoSimulationEndedDetails.class);
                    setSimulationDetails();
                } else{
                    AlertDialog.showError(response.message());
                }
            }
        });
    }

    public void setSimulationDetails(){
        Platform.runLater(() -> {
            simulationDetails.setText("Simulation id: " + simulationEndedDetails.getSimulationId() + ", Request id: " +simulationEndedDetails.getRequestId());
            simulationStartTime.setText("Start time: " + simulationEndedDetails.getStartTime());
        });
    }

    private void setEntityCBox() {
        ObservableList<String> entitiesName = FXCollections.observableArrayList(simulationEndedDetails.getDtoEntityMap().keySet());
        Platform.runLater(() -> entityCBox.setItems(entitiesName));
    }

    private void setPropertyCBox(Map<String, DtoPropertyResults> properties) {
        ObservableList<String> propertiesName = FXCollections.observableArrayList(properties.keySet());
        Platform.runLater(() -> propertyCBox.setItems(propertiesName));
    }

    private void setInformationCBox(DtoPropertyResults property) {
        ObservableList<String> choices = FXCollections.observableArrayList("Histogram on the population", "Consistency - Average steps the value did not change");

        Platform.runLater(() -> {
            if (property != null && property.getType() != null) {
                if (property.getType().equals("decimal") || property.getType().equals("float")) {
                    choices.add("Average value of the property in the population");
                }
            }

            informationCBox.setItems(choices);
        });
    }

    @FXML
    void analyseTypeCBoxClick(ActionEvent event) {
        Platform.runLater(() -> resultsPane.getChildren().clear());
        entityCBox.setValue(null);
        if(analyseTypeCBox.getValue() != null){
            if(analyseTypeCBox.getValue().equals("Entity's quantity change graph")){
                propertyCBox.setVisible(false);
                informationCBox.setVisible(false);
            }
            setEntityCBox();
            entityCBox.setVisible(true);
        }
    }

    @FXML
    void entityCBoxClick(ActionEvent event) {
        Platform.runLater(() -> resultsPane.getChildren().clear());
        propertyCBox.setValue(null);
        informationCBox.setValue(null);
        propertyCBox.setVisible(false);
        informationCBox.setVisible(false);
        if(entityCBox.getValue() != null){
            if(analyseTypeCBox.getValue().equals("Entity's quantity change graph")){
                loadEntityQuantityController();
            } else if (analyseTypeCBox.getValue().equals("Property's statistical info")) {
                setPropertyCBox(simulationEndedDetails.getDtoEntityMap().get(entityCBox.getValue()).getProperties());
                propertyCBox.setVisible(true);
            }
        }
    }

    private void loadEntityQuantityController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/results/executionResults/resultsTypes/entityQuantity/EntityQuantity.fxml"));
            Parent entityQuantity = loader.load();
            Platform.runLater(() -> {
                resultsPane.getChildren().clear();
                resultsPane.getChildren().add(entityQuantity);
            });
            entityQuantityController = loader.getController();
            entityQuantityController.setter(simulationEndedDetails.getDtoEntityMap().get(entityCBox.getValue()));
        } catch (IOException ignored) {
        }
    }

    @FXML
    void informationCBoxClick(ActionEvent event) {
        resultsPane.getChildren().clear();
        if(propertyCBox.getValue() != null && informationCBox.getValue() != null) {
            if (informationCBox.getValue().equals("Histogram on the population")) {
                loadHistogramController(simulationEndedDetails.getDtoEntityMap().get(entityCBox.getValue()).getProperties().get(propertyCBox.getValue()).getDtoPropertyHistogram());
            } else if (informationCBox.getValue().equals("Consistency - Average steps the value did not change")) {
                loadConsistencyController();
                consistencyController.setter("Consistency - Average steps the property " + propertyCBox.getValue() + " did not change:",
                        simulationEndedDetails.getDtoEntityMap().get(entityCBox.getValue()).getProperties().get(propertyCBox.getValue()).getConsistency());
            } else if (informationCBox.getValue().equals("Average value of the property in the population")) {
                loadConsistencyController();
                consistencyController.setter("Average value of the property " + propertyCBox.getValue() + " in the population:",
                        simulationEndedDetails.getDtoEntityMap().get(entityCBox.getValue()).getProperties().get(propertyCBox.getValue()).getAverageOfPropertyInPopulation());
            }
        }
    }

    private void loadHistogramController(Map<String, DtoPropertyHistogram> dtoPropertyHistogram) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/results/executionResults/resultsTypes/histogram/Histogram.fxml"));
            Parent histogram = loader.load();
            Platform.runLater(() -> {
                resultsPane.getChildren().clear();
                resultsPane.getChildren().add(histogram);
            });

            histogramController = loader.getController();
            histogramController.setter(dtoPropertyHistogram);
        } catch (IOException ignored) {
        }
    }

    private void loadConsistencyController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/results/executionResults/resultsTypes/consistency/Consistency.fxml"));
            Parent consistency = loader.load();
            Platform.runLater(() -> {
                resultsPane.getChildren().clear();
                resultsPane.getChildren().add(consistency);
            });

            consistencyController = loader.getController();
        } catch (IOException ignored) {
        }
    }

    @FXML
    void propertyCBoxClick(ActionEvent event) {
        Platform.runLater(() -> resultsPane.getChildren().clear());
        informationCBox.setValue(null);
        if(propertyCBox.getValue() != null){
            setInformationCBox(simulationEndedDetails.getDtoEntityMap().get(entityCBox.getValue()).getProperties().get(propertyCBox.getValue()));
            informationCBox.setVisible(true);
        }
    }
}
