package body.secondPage;
import alloction.DtoAlloction;
import details.DtoEnvironmentInfo;
import error.ErrorDialog;
import http.HttpClientUtil;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
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
import refresher.RequestsRefresher;
import requests.DtoRequestInfo;
import requests.DtoRequestsInfo;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Timer;

public class AlloctionController {

    @FXML
    private TableColumn<DtoRequestInfo, Integer> finishRow;

    @FXML
    private TableColumn<DtoRequestInfo, String> StatusRow;

    @FXML
    private TableColumn<DtoRequestInfo, Integer> requestIdRow;

    @FXML
    private TableColumn<DtoRequestInfo, Integer> runningRow;

    @FXML
    private TableView<DtoRequestInfo> tableInfo;

    @FXML
    private TableColumn<DtoRequestInfo, String> termainationRow;

    @FXML
    private TableColumn<DtoRequestInfo, Integer> totalAmountRow;

    @FXML
    private TableColumn<DtoRequestInfo, String> userNameRow;

    @FXML
    private TableColumn<DtoRequestInfo, String> worldNameRow;

    @FXML
    private Button AproveButton;

    @FXML
    private Button RejectButton;

    private RequestsRefresher requestsRefresher;
    private Timer timer;
    @FXML
    void AproveButtonClicked(ActionEvent event) {
        String baseUrl = "http://localhost:8080/predictions/managerRequests";
        HttpUrl.Builder urlBuilder = HttpUrl
                .parse(baseUrl).
                newBuilder();
        urlBuilder.addEncodedQueryParameter("requestStatus", "APPROVED");
        urlBuilder.addEncodedQueryParameter("requestId", String.valueOf(requestIdRow));

        String finalUrl = urlBuilder.
                build().
                toString();

        RequestBody body = RequestBody.create(null, new byte[0]);

        HttpClientUtil.runAsyncPost(finalUrl, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()) {
                    ErrorDialog.showError(response.message());
                }
            }
        });
        AproveButton.setVisible(false);
        RejectButton.setVisible(false);
    }

    @FXML
    void RejectButtonClicked(ActionEvent event) {
        String baseUrl = "http://localhost:8080/predictions/managerRequests";
        HttpUrl.Builder urlBuilder = HttpUrl
                .parse(baseUrl).
                newBuilder();
        urlBuilder.addEncodedQueryParameter("requestStatus", "REJECTED");
        urlBuilder.addEncodedQueryParameter("requestId", String.valueOf(requestIdRow));

        String finalUrl = urlBuilder.
                build().
                toString();

        RequestBody body = RequestBody.create(null, new byte[0]);
        HttpClientUtil.runAsyncPost(finalUrl, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()) {
                    ErrorDialog.showError(response.message());
                }
            }
        });
        AproveButton.setVisible(false);
        RejectButton.setVisible(false);
    }


    @FXML
    void rowTableClicked(MouseEvent event) {
        if(Objects.equals(StatusRow.getText(), "PENDING")){
            AproveButton.setVisible(true);
            RejectButton.setVisible(true);
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

        termainationRow.setCellValueFactory(cellData -> {
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
        StatusRow.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(cellData.getValue().getRequestStatus());
            return property;
        });

        //checkIfRowsCliecked();
        refresher();

    }

    /*
    private void checkIfRowsCliecked() {
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
            ObservableList<DtoRequestInfo> data = FXCollections.observableArrayList(dtoRequestsInfo.getRequestList());
            if (data.isEmpty()) {
                Label emptyLabel = new Label("No environments :(");
                tableInfo.setPlaceholder(emptyLabel);
            } else {
                tableInfo.setItems(data);
            }
        });
    }

    private void refresher() {
        requestsRefresher = new RequestsRefresher(this::setTable);
        timer = new Timer();
        timer.schedule(requestsRefresher, 2000, 1);
    }

  }

