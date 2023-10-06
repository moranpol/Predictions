package results.startExecutionDetails;

import alert.AlertDialog;
import com.google.gson.Gson;
import http.HttpClientUtil;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import newExecution.DtoStartExecution;
import newExecution.dtoEntities.DtoEntitiesPopulation;
import newExecution.dtoEnvironment.DtoEnvironmentInitialize;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class StartExecutionDetailsController {

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

    public void setter(Integer requestId, Integer simulationId){
        String finalUrl = HttpUrl
                .parse("http://localhost:8080/predictions/startExecutionDetails")
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
                    DtoStartExecution dtoStartExecution = gson.fromJson(response.body().charStream(), DtoStartExecution.class);
                    setEntitiesTable(dtoStartExecution.getDtoEntitiesPopulationList());
                    setEnvironmentTable(dtoStartExecution.getDtoEnvironmentInitializeList());
                } else {
                    AlertDialog.showError(response.message());
                }
            }
        });
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
}
