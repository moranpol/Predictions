package body.secondPage;

import alert.AlertDialog;
import http.HttpClientUtil;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import refresher.RequestsRefresher;
import requests.DtoRequestInfo;
import requests.DtoRequestsInfo;
import javafx.scene.input.MouseEvent;

import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;
import java.util.Timer;

public class AllocationController implements Closeable {

    @FXML
    private TableColumn<DtoRequestInfo, Integer> finishRow;

    @FXML
    private TableColumn<DtoRequestInfo, String> statusRow;

    @FXML
    private TableColumn<DtoRequestInfo, Integer> requestIdRow;

    @FXML
    private TableColumn<DtoRequestInfo, Integer> runningRow;

    @FXML
    private TableView<DtoRequestInfo> tableInfo;

    @FXML
    private TableColumn<DtoRequestInfo, String> terminationRow;

    @FXML
    private TableColumn<DtoRequestInfo, Integer> totalAmountRow;

    @FXML
    private TableColumn<DtoRequestInfo, String> userNameRow;

    @FXML
    private TableColumn<DtoRequestInfo, String> worldNameRow;

    @FXML
    private Button approveButton;

    @FXML
    private Button rejectButton;

    private RequestsRefresher requestsRefresher;

    private Timer timer;

    private ObservableList<DtoRequestInfo> data = FXCollections.observableArrayList();

    @FXML
    void approveButtonClicked(ActionEvent event) {
        String baseUrl = "http://localhost:8080/predictions/managerRequests";
        HttpUrl.Builder urlBuilder = HttpUrl
                .parse(baseUrl).
                newBuilder();
        urlBuilder.addEncodedQueryParameter("requestStatus", "approved");
        urlBuilder.addEncodedQueryParameter("requestId", tableInfo.getSelectionModel().getSelectedItem().getId().toString());

        String finalUrl = urlBuilder.
                build().
                toString();

        RequestBody body = RequestBody.create(null, new byte[0]);

        HttpClientUtil.runAsyncPost(finalUrl, body, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                AlertDialog.showError(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if(!response.isSuccessful()) {
                    AlertDialog.showError(response.message());
                } else{
                    approveButton.setVisible(false);
                    rejectButton.setVisible(false);
                    AlertDialog.showSuccess("Status set successfully.");
                }
            }
        });

    }

    @FXML
    void rejectButtonClicked(ActionEvent event) {
        String baseUrl = "http://localhost:8080/predictions/managerRequests";
        HttpUrl.Builder urlBuilder = HttpUrl
                .parse(baseUrl).
                newBuilder();
        urlBuilder.addEncodedQueryParameter("requestStatus", "rejected");
        urlBuilder.addEncodedQueryParameter("requestId", tableInfo.getSelectionModel().getSelectedItem().getId().toString());

        String finalUrl = urlBuilder.
                build().
                toString();

        RequestBody body = RequestBody.create(null, new byte[0]);
        HttpClientUtil.runAsyncPost(finalUrl, body, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                AlertDialog.showError(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if(!response.isSuccessful()) {
                    AlertDialog.showError(response.message());
                } else{
                    approveButton.setVisible(false);
                    rejectButton.setVisible(false);
                    AlertDialog.showSuccess("Status set successfully.");
                }
            }
        });
    }

    private void initializeTable(){
        tableInfo.setItems(data);

        Platform.runLater(() -> {
            if (data.isEmpty()) {
                Label emptyLabel = new Label("No requests :(");
                tableInfo.setPlaceholder(emptyLabel);
            }
        });
    }

    @FXML
    void rowTableClicked(MouseEvent event) {
        if(tableInfo.getSelectionModel().getSelectedItem() != null) {
            if (Objects.equals(tableInfo.getSelectionModel().getSelectedItem().getRequestStatus(), "pending")) {
                approveButton.setVisible(true);
                rejectButton.setVisible(true);
            } else {
                approveButton.setVisible(false);
                rejectButton.setVisible(false);
            }
        }
    }

    public void initialize(){
        requestIdRow.setCellValueFactory(cellData -> {
            SimpleIntegerProperty property = new SimpleIntegerProperty();
            property.setValue(cellData.getValue().getId());
            return property.asObject();
        });
        totalAmountRow.setCellValueFactory(cellData -> {
            SimpleIntegerProperty property = new SimpleIntegerProperty();
            property.setValue(cellData.getValue().getNumOfWantedSimulations());
            return property.asObject();
        });
        terminationRow.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(cellData.getValue().getTermination());
            return property;
        });
        userNameRow.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(cellData.getValue().getUserName());
            return property;
        });
        worldNameRow.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(cellData.getValue().getWorldName());
            return property;
        });
        statusRow.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(cellData.getValue().getRequestStatus());
            return property;
        });
        finishRow.setCellValueFactory(cellData -> {
            SimpleIntegerProperty property = new SimpleIntegerProperty();
            property.setValue(cellData.getValue().getEndedSimulations());
            return property.asObject();
        });
        runningRow.setCellValueFactory(cellData -> {
            SimpleIntegerProperty property = new SimpleIntegerProperty();
            property.setValue(cellData.getValue().getRunningSimulations());
            return property.asObject();
        });

        //checkIfRowsClicked();
        refresher();
        approveButton.setVisible(false);
        rejectButton.setVisible(false);
        initializeTable();
    }

    /*
    private void checkIfRowsClicked() {
        tableInfo.getColumns().addAll(requestIdRow, userNameRow, worldNameRow, totalAmountRow, StatusRow, termainationRow, runningRow, finishRow); //
        for(DtoRequestInfo dtoRequestInfo: tableInfo.getItems()){
            if(!Objects.equals(dtoRequestInfo.getRequestStatus(), "PENDING")){
                tableInfo.getSelectionModel().select(dtoRequestInfo);
            }
        }
    }
     */

    public void setTable(DtoRequestsInfo dtoRequestsInfo) {
        Platform.runLater(() -> {
            for (DtoRequestInfo dtoRequestInfo : dtoRequestsInfo.getRequestList()) {
                boolean found = false;
                for (int i = 0; i < data.size(); i++) {
                    if (Objects.equals(data.get(i).getId(), dtoRequestInfo.getId())) {
                        found = true;
                        if(!Objects.equals(data.get(i).getEndedSimulations(), dtoRequestInfo.getEndedSimulations()) || !Objects.equals(data.get(i).getRunningSimulations(), dtoRequestInfo.getRunningSimulations())
                                || !Objects.equals(data.get(i).getRequestStatus(), dtoRequestInfo.getRequestStatus())){
                            data.set(i, dtoRequestInfo);
                        }
                        break;
                    }
                }
                if (!found) {
                    data.add(dtoRequestInfo);
                }
            }
        });
    }

    private void refresher() {
        requestsRefresher = new RequestsRefresher(this::setTable);
        timer = new Timer();
        timer.schedule(requestsRefresher, 1, 1000);
    }

    @Override
    public void close() {
        requestsRefresher.cancel();
        timer.cancel();
    }
}

