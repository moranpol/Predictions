package results.executionDetails.buttons;

import alert.AlertDialog;
import com.google.gson.Gson;
import http.HttpClientUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import results.executionDetails.ExecutionDetailsController;

import java.io.IOException;

public class RunningController {

    @FXML
    private Button pauseButton;

    @FXML
    private Button resumeButton;

    @FXML
    private Button stopButton;

    private ExecutionDetailsController executionDetailsController;

    private Integer requestId;

    private Integer simulationId;

    @FXML
    void pauseButtonClick(ActionEvent event) {
        String finalUrl = HttpUrl
                .parse("http://localhost:8080/predictions/pauseSimulation")
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
                    resumeButton.setDisable(false);
                    pauseButton.setDisable(true);
                    executionDetailsController.close();
                } else{
                    AlertDialog.showError(response.message());
                }
            }
        });
    }

    @FXML
    void resumeButtonClick(ActionEvent event) {
        String finalUrl = HttpUrl
                .parse("http://localhost:8080/predictions/resumeSimulation")
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
                    resumeButton.setDisable(true);
                    pauseButton.setDisable(false);
                    executionDetailsController.refresher();
                } else{
                    AlertDialog.showError(response.message());
                }
            }
        });
    }

    @FXML
    void stopButtonClick(ActionEvent event) {
        String finalUrl = HttpUrl
                .parse("http://localhost:8080/predictions/stopSimulation")
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
                    executionDetailsController.getResultsController().updateScreenBySimulationChoice(simulationId, requestId, "ended");
                } else{
                    AlertDialog.showError(response.message());
                }
            }
        });
    }

    public void setter(String simulationMode, Integer requestId, Integer simulationId, ExecutionDetailsController executionDetailsController) {
        this.executionDetailsController = executionDetailsController;
        resumeButton.setDisable(!simulationMode.equals("pause"));
        pauseButton.setDisable(simulationMode.equals("pause"));
        this.requestId = requestId;
        this.simulationId = simulationId;
    }
}
