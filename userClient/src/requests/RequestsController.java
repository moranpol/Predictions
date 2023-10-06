package requests;

import com.google.gson.Gson;
import details.DtoWorldsList;
import alert.AlertDialog;
import http.HttpClientUtil;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import mainPage.MainPageController;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import refresher.RequestsRefresher;
import refresher.WorldInfoRefresher;

import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;
import java.util.Timer;

public class RequestsController implements Closeable {

    @FXML
    private TableColumn<DtoRequestInfo, Integer> endedCol;

    @FXML
    private Button executeButton;

    @FXML
    private TableView<DtoRequestInfo> requestsTable;

    @FXML
    private TableColumn<DtoRequestInfo, Integer> runningCol;

    @FXML
    private TableColumn<DtoRequestInfo, Integer> runsCol;

    @FXML
    private Spinner<Integer> secondsSpinner;

    @FXML
    private CheckBox secondsTermination;

    @FXML
    private TableColumn<DtoRequestInfo, Integer> serialNumberCol;

    @FXML
    private Spinner<Integer> simulationCounterSpinner;

    @FXML
    private TableColumn<DtoRequestInfo, String> statusCol;

    @FXML
    private Button submitButton;

    @FXML
    private Spinner<Integer> ticksSpinner;

    @FXML
    private CheckBox ticksTermination;

    @FXML
    private ComboBox<String> worldComboBox;

    @FXML
    private TableColumn<DtoRequestInfo, String> worldNameCol;

    @FXML
    private ComboBox<String> terminationComboBox;

    private MainPageController mainPageController;

    private WorldInfoRefresher worldInfoRefresher;

    private RequestsRefresher requestsRefresher;

    private ObservableList<DtoRequestInfo> data = FXCollections.observableArrayList();

    private Timer timer;

    public void initialize(){
        secondsTermination.setVisible(false);
        secondsSpinner.setVisible(false);
        ticksSpinner.setVisible(false);
        ticksTermination.setVisible(false);
        submitButton.setDisable(true);
        executeButton.setDisable(true);
        initializeSpinners();

        statusCol.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(cellData.getValue().getRequestStatus());
            return property;
        });
        worldNameCol.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(cellData.getValue().getWorldName());
            return property;
        });
        endedCol.setCellValueFactory(cellData -> {
            SimpleIntegerProperty property = new SimpleIntegerProperty();
            property.setValue(cellData.getValue().getEndedSimulations());
            return property.asObject();
        });
        runningCol.setCellValueFactory(cellData -> {
            SimpleIntegerProperty property = new SimpleIntegerProperty();
            property.setValue(cellData.getValue().getRunningSimulations());
            return property.asObject();
        });
        runsCol.setCellValueFactory(cellData -> {
            SimpleIntegerProperty property = new SimpleIntegerProperty();
            property.setValue(cellData.getValue().getNumOfWantedSimulations());
            return property.asObject();
        });
        serialNumberCol.setCellValueFactory(cellData -> {
            SimpleIntegerProperty property = new SimpleIntegerProperty();
            property.setValue(cellData.getValue().getId());
            return property.asObject();
        });
    }

    public void setter(MainPageController mainPageController) {
        setMainPageController(mainPageController);
        initializeTable();
        refresher();
    }

    private void initializeTable(){
        requestsTable.setItems(data);

        Platform.runLater(() -> {
            if (data.isEmpty()) {
                Label emptyLabel = new Label("No requests :(");
                requestsTable.setPlaceholder(emptyLabel);
            }
        });
    }

    private void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    private void initializeSpinners(){
        Platform.runLater(() -> {
            secondsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1));
            ticksSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1));
            simulationCounterSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1));
        });
    }

    @FXML
    void executeButtonClicked(ActionEvent event) {
        mainPageController.loadNewExecutionController(requestsTable.getSelectionModel().getSelectedItem().getId());
    }



    @FXML
    void tableRowClicked(MouseEvent event) {
        if (requestsTable.getSelectionModel().getSelectedItem() != null){
            DtoRequestInfo dtoRequestInfo = requestsTable.getSelectionModel().getSelectedItem();
            executeButton.setDisable(!dtoRequestInfo.getRequestStatus().equals("approved"));
        } else{
            executeButton.setDisable(true);
        }
    }

    @FXML
    void submitButtonClicked(ActionEvent event) {
        String finalUrl = HttpUrl
                .parse("http://localhost:8080/predictions/newRequest")
                .newBuilder()
                .build()
                .toString();

        DtoNewRequest dtoNewRequest = new DtoNewRequest(mainPageController.getUserName(), worldComboBox.getValue(), simulationCounterSpinner.getValue(), createDtoTermination());
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(dtoNewRequest);
        RequestBody body = RequestBody.create(jsonRequest, MediaType.parse("application/json; charset=utf-8"));

        HttpClientUtil.runAsyncPost(finalUrl, body, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                AlertDialog.showError(e.getMessage());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if (!response.isSuccessful()) {
                    AlertDialog.showError(response.message());
                } else{
                    AlertDialog.showSuccess("Request sent successfully.");
                }
            }
        });
    }

    private DtoTermination createDtoTermination(){
        if(terminationComboBox.getValue().equals("By ticks/ seconds") && secondsTermination.isSelected() && ticksTermination.isSelected()){
            return new DtoTermination(ticksSpinner.getValue(), secondsSpinner.getValue(), false);
        } else if(terminationComboBox.getValue().equals("By ticks/ seconds") && secondsTermination.isSelected()){
            return new DtoTermination(null, secondsSpinner.getValue(), false);
        } else if (terminationComboBox.getValue().equals("By ticks/ seconds") && ticksTermination.isSelected()) {
            return new DtoTermination(ticksSpinner.getValue(), null, false);
        } else{
            return new DtoTermination(null, null, true);
        }
    }

    @FXML
    void secondsTerminationClicked(ActionEvent event) {
        secondsSpinner.setEditable(secondsTermination.isSelected());
        setSubmitButton();
    }

    @FXML
    void ticksTerminationClicked(ActionEvent event) {
        ticksSpinner.setEditable(ticksTermination.isSelected());
        setSubmitButton();
    }

    @FXML
    void terminationComboBoxClicked(ActionEvent event) {
        if(terminationComboBox.getValue() != null && terminationComboBox.getValue().equals("By ticks/ seconds")){
            secondsTermination.setVisible(true);
            secondsSpinner.setVisible(true);
            ticksSpinner.setVisible(true);
            ticksTermination.setVisible(true);
        } else {
            secondsTermination.setVisible(false);
            secondsSpinner.setVisible(false);
            ticksSpinner.setVisible(false);
            ticksTermination.setVisible(false);
        }
        setSubmitButton();
    }


    @FXML
    void worldComboBoxClicked(ActionEvent event) {
        setSubmitButton();
    }

    public void setWorldCBox(DtoWorldsList dtoWorldsList){
        ObservableList<String> worldNames = FXCollections.observableArrayList(dtoWorldsList.getWorldsName());
        if(!worldNames.equals(worldComboBox.getItems())) {
            Platform.runLater(() -> worldComboBox.setItems(worldNames));
        }
    }

    public void setTable(DtoRequestsInfo requestInfoList){
        Platform.runLater(() -> {
            for (DtoRequestInfo dtoRequestsInfo : requestInfoList.getRequestList()) {
                boolean found = false;
                for (int i = 0; i < data.size(); i++) {
                    if (Objects.equals(data.get(i).getId(), dtoRequestsInfo.getId())) {
                        found = true;
                        if(!Objects.equals(data.get(i).getEndedSimulations(), dtoRequestsInfo.getEndedSimulations()) || !Objects.equals(data.get(i).getRunningSimulations(), dtoRequestsInfo.getRunningSimulations())){
                            data.set(i, dtoRequestsInfo);
                        }
                        break;
                    }
                }
                if (!found) {
                    data.add(dtoRequestsInfo);
                }
            }
        });
    }

    private void setSubmitButton(){
        if(worldComboBox.getValue() == null){
            submitButton.setDisable(true);
        } else if(terminationComboBox.getValue() == null){
            submitButton.setDisable(true);
        } else submitButton.setDisable(terminationComboBox.getValue().equals("By ticks/ seconds") && !secondsTermination.isSelected() && !ticksTermination.isSelected());
    }

    private void refresher() {
        worldInfoRefresher = new WorldInfoRefresher(this::setWorldCBox);
        requestsRefresher = new RequestsRefresher(this::setTable, mainPageController.getUserName());
        timer = new Timer();
        timer.schedule(worldInfoRefresher, 1000, 1000);
        timer.schedule(requestsRefresher, 1000, 1000);
    }

    @Override
    public void close() {
        worldInfoRefresher.cancel();
        requestsRefresher.cancel();
        timer.cancel();
    }
}

