package execution.executionStart;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import newExecution.DtoStartExecution;
import newExecution.dtoEntities.DtoEntitiesPopulation;
import newExecution.dtoEnvironment.DtoEnvironmentInitialize;

import java.util.List;

public class ExecutionStartController {

    @FXML
    private TableView<DtoEntitiesPopulation> entitiesTable;

    @FXML
    private TableColumn<DtoEntitiesPopulation, String> entityNameCol;

    @FXML
    private TableColumn<DtoEnvironmentInitialize, String> environmentNameCol;

    @FXML
    private TableView<DtoEnvironmentInitialize> environmentTable;

    @FXML
    private TableColumn<DtoEntitiesPopulation, Integer> populationCol;

    @FXML
    private TableColumn<DtoEnvironmentInitialize, String> valueCol;

    public void initialize(){
        entityNameCol.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(cellData.getValue().getName());
            return property;
        });
        environmentNameCol.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(cellData.getValue().getName());
            return property;
        });
        populationCol.setCellValueFactory(cellData -> {
            SimpleIntegerProperty property = new SimpleIntegerProperty();
            property.setValue(cellData.getValue().getPopulation());
            return property.asObject();
        });
        valueCol.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(cellData.getValue().getValue().toString());
            return property;
        });
    }

    public void setter(DtoStartExecution dtoStartExecution){
        setEntitiesTable(dtoStartExecution.getDtoEntitiesPopulationList());
        setEnvironmentTable(dtoStartExecution.getDtoEnvironmentInitializeList());
    }

    private void setEnvironmentTable(List<DtoEnvironmentInitialize> dtoEnvironmentInitializeList) {
        Platform.runLater(() -> {
            ObservableList<DtoEnvironmentInitialize> data = FXCollections.observableArrayList(dtoEnvironmentInitializeList);
            if (data.isEmpty()) {
                Label emptyLabel = new Label("No environments :(");
                environmentTable.setPlaceholder(emptyLabel);
            } else {
                environmentTable.setItems(data);
            }
        });
    }

    private void setEntitiesTable(List<DtoEntitiesPopulation> dtoEntitiesPopulationList) {
        Platform.runLater(() -> {
            ObservableList<DtoEntitiesPopulation> data = FXCollections.observableArrayList(dtoEntitiesPopulationList);
            if (data.isEmpty()) {
                Label emptyLabel = new Label("No entities :(");
                entitiesTable.setPlaceholder(emptyLabel);
            } else {
                entitiesTable.setItems(data);
            }
        });
    }

    @FXML
    void startButtonClicked(ActionEvent event) {

    }

}
