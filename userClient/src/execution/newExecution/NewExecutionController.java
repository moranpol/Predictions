package execution.newExecution;

import com.google.gson.Gson;
import alert.AlertDialog;
import execution.simulationEntitiesPopulation.SimulationEntitiesPopulationController;
import execution.simulationEnvironmentsInputs.SimulationEnvironmentInputsController;
import http.HttpClientUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import mainPage.MainPageController;
import newExecution.DtoNewExecution;
import newExecution.DtoStartExecution;
import newExecution.dtoEntities.DtoEntitiesPopulation;
import newExecution.dtoEnvironment.DtoEnvironmentInitialize;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewExecutionController {
    @FXML
    private BorderPane newExecutionBorderPane;

    @FXML
    private Button clearButton;

    @FXML
    private Pane simulationEntitiesPopulation;

    @FXML
    private Pane simulationEnvironmentInputs;

    @FXML
    private Button sendButton;

    @FXML
    private BorderPane entitiesPopulation;

    @FXML
    private SimulationEntitiesPopulationController entitiesPopulationController;

    @FXML
    private BorderPane environmentInputs;

    @FXML
    private SimulationEnvironmentInputsController environmentInputsController;

    private List<DtoEntitiesPopulation> dtoEntitiesPopulationList;

    private List<SendButtonListener> startNewExecutionListeners;

    private List<DtoEnvironmentInitialize> dtoEnvironmentInitializeList;

    private MainPageController mainPageController;

    private Integer requestId;

    @FXML
    public void initialize(){
        dtoEntitiesPopulationList = new ArrayList<>();
        startNewExecutionListeners = new ArrayList<>();
        dtoEnvironmentInitializeList = new ArrayList<>();
    }

    public void setter(MainPageController mainPageController, Integer requestId) {
        this.mainPageController = mainPageController;
        this.requestId = requestId;
        setEnvironmentAndEntities();
    }

    public void setEnvironmentAndEntities(){
        String finalUrl = HttpUrl
                .parse("http://localhost:8080/predictions/newExecutionInfo")
                .newBuilder()
                .addQueryParameter("request id", String.valueOf(requestId))
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
                    DtoNewExecution dtoNewExecution = gson.fromJson(response.body().charStream(), DtoNewExecution.class);
                    entitiesPopulationController.setter(NewExecutionController.this, dtoNewExecution.getDtoEntityNames(), dtoNewExecution.getMaxPopulation());
                    environmentInputsController.setter(NewExecutionController.this, dtoNewExecution.getDtoEnvironmentList());
                } else{
                    AlertDialog.showError(response.message());
                }
            }
        });
    }

    @FXML
    void clearButtonClick(ActionEvent event) {
        startNewExecutionListeners.clear();
        dtoEnvironmentInitializeList.clear();
        dtoEntitiesPopulationList.clear();
        entitiesPopulationController.initialize();
        setEnvironmentAndEntities();
    }

    @FXML
    void sendButtonClick(ActionEvent event) {
        for (SendButtonListener listener : startNewExecutionListeners){
            listener.sendOnClicked();
        }

        String finalUrl = HttpUrl
                .parse("http://localhost:8080/predictions/sendExecution")
                .newBuilder()
                .addQueryParameter("request id", requestId.toString())
                .build()
                .toString();

        Gson gson = new Gson();
        DtoStartExecution dtoSendExecution = new DtoStartExecution(dtoEntitiesPopulationList, dtoEnvironmentInitializeList, null, null);
        String jsonRequest = gson.toJson(dtoSendExecution);
        RequestBody body = RequestBody.create(jsonRequest, MediaType.parse("application/json; charset=utf-8"));

        HttpClientUtil.runAsyncPost(finalUrl, body, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                AlertDialog.showError(e.getMessage());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if (response.isSuccessful()) {
                    DtoStartExecution dtoStartExecution = gson.fromJson(response.body().charStream(), DtoStartExecution.class);
                    mainPageController.loadExecutionStartController(dtoStartExecution);
                } else {
                    AlertDialog.showError(response.message());
                }
            }
        });
    }

    public void addListenerToStartButton(SendButtonListener listener) {
        startNewExecutionListeners.add(listener);
    }

    public void addToDtoEntitiesPopulationList(DtoEntitiesPopulation dtoEntitiesPopulation) {
        dtoEntitiesPopulationList.add(dtoEntitiesPopulation);
    }

    public void addToDtoEnvironmentInitializeList(DtoEnvironmentInitialize dtoEnvironmentInitialize){
        dtoEnvironmentInitializeList.add(dtoEnvironmentInitialize);
    }

    public void rerunExecution(DtoStartExecution dtoStartExecution) {
        entitiesPopulationController.rerunExecutionEntities(dtoStartExecution.getDtoEntitiesPopulationList());
        environmentInputsController.rerunExecutionEnvironments(dtoStartExecution.getDtoEnvironmentInitializeList());
    }
}

