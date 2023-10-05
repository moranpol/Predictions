package execution.newExecution;

import com.google.gson.Gson;
import error.ErrorDialog;
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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
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
        entitiesPopulationController.setNewExecutionController(this);
        environmentInputsController.setNewExecutionController(this);

        String finalUrl = HttpUrl
                .parse("http://localhost:8080/predictions/newExecutionInfo")
                .newBuilder()
                .addQueryParameter("request id", String.valueOf(requestId))
                .build()
                .toString();

        HttpClientUtil.runAsyncGet(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                ErrorDialog.showError(e.getMessage());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    assert response.body() != null;
                    DtoNewExecution dtoNewExecution = gson.fromJson(response.body().charStream(), DtoNewExecution.class);
                    entitiesPopulationController.setMaxPopulationCount(dtoNewExecution.getMaxPopulation());
                    entitiesPopulationController.setEntitiesCount(dtoNewExecution.getDtoEntityNames());
                    environmentInputsController.setEnvironmentInputs(dtoNewExecution.getDtoEnvironmentList());
                } else{
                    ErrorDialog.showError(response.message());
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
        //pageController.sendSimulation(dtoEnvironmentInitializeList, dtoEntitiesPopulationList);
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

