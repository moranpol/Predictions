package resultsComponent.executionResults;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import results.simulationEnded.DtoPropertyHistogram;
import results.simulationEnded.DtoSimulationEndedDetails;
import results.simulationEnded.DtoPropertyResults;
import resultsComponent.executionResults.resultsTypes.consistency.ConsistencyController;
import resultsComponent.executionResults.resultsTypes.entityQuantity.EntityQuantityController;
import resultsComponent.executionResults.resultsTypes.histogram.HistogramController;

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

    private EntityQuantityController entityQuantityController;

    private HistogramController histogramController;

    private ConsistencyController consistencyController;

    private DtoSimulationEndedDetails simulationEndedDetails;

    public void initialize(){
        entityCBox.setVisible(false);
        propertyCBox.setVisible(false);
        informationCBox.setVisible(false);
    }

    public void setSimulationDetails(){
        this.simulationDetails.setText("Simulation id: " + simulationEndedDetails.getId() + "\nStart time: " + simulationEndedDetails.getStartTime());
    }

    private void setEntityCBox() {
        ObservableList<String> entitiesName = FXCollections.observableArrayList(simulationEndedDetails.getDtoEntityMap().keySet());
        entityCBox.setItems(entitiesName);
    }

    private void setPropertyCBox(Map<String, DtoPropertyResults> properties) {
        ObservableList<String> propertiesName = FXCollections.observableArrayList(properties.keySet());
        propertyCBox.setItems(propertiesName);
    }

    private void setInformationCBox(DtoPropertyResults property) {
        ObservableList<String> choices = FXCollections.observableArrayList("Histogram on the population", "Consistency - Average steps the value did not change");
        if (property != null && property.getType() != null) {
            if (property.getType().equals("decimal") || property.getType().equals("float")) {
                choices.add("Average value of the property in the population");
            }
        }

        informationCBox.setItems(choices);
    }

    @FXML
    void analyseTypeCBoxClick(ActionEvent event) {
        resultsPane.getChildren().clear();
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
        resultsPane.getChildren().clear();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resultsComponent/executionResults/resultsTypes/entityQuantity/EntityQuantity.fxml"));
            Parent entityQuantity = loader.load();
            resultsPane.getChildren().clear();
            resultsPane.getChildren().add(entityQuantity);

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resultsComponent/executionResults/resultsTypes/histogram/Histogram.fxml"));
            Parent histogram = loader.load();
            resultsPane.getChildren().clear();
            resultsPane.getChildren().add(histogram);

            histogramController = loader.getController();
            histogramController.setDtoPropertyHistogram(dtoPropertyHistogram);
            histogramController.setTable();
        } catch (IOException ignored) {
        }
    }

    private void loadConsistencyController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resultsComponent/executionResults/resultsTypes/consistency/Consistency.fxml"));
            Parent consistency = loader.load();
            resultsPane.getChildren().clear();
            resultsPane.getChildren().add(consistency);

            consistencyController = loader.getController();
        } catch (IOException ignored) {
        }
    }

    @FXML
    void propertyCBoxClick(ActionEvent event) {
        resultsPane.getChildren().clear();
        informationCBox.setValue(null);
        if(propertyCBox.getValue() != null){
            setInformationCBox(simulationEndedDetails.getDtoEntityMap().get(entityCBox.getValue()).getProperties().get(propertyCBox.getValue()));
            informationCBox.setVisible(true);
        }
    }

    public void setDtoSimulationEndedDetails(DtoSimulationEndedDetails simulationEndedDetails) {
        this.simulationEndedDetails = simulationEndedDetails;
    }
}
