package results.executionResults.simulationFailed;

import alert.AlertDialog;
import com.google.gson.Gson;
import http.HttpClientUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import results.simulationFailed.DtoSimulationFailedDetails;
import results.simulationRunningDetails.DtoSimulationRunningDetails;
import results.simulations.DtoSimulationInfo;

import java.io.IOException;

public class SimulationFailedController {

    @FXML
    private Label simulationError;

    @FXML
    private Label simulationLabel;

    @FXML
    private Label simulationStartTime;

    public void setter(Integer simulationId, Integer requestId) {
        String finalUrl = HttpUrl
                .parse("http://localhost:8080/predictions/simulationFailedDetails")
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
                    DtoSimulationFailedDetails dtoSimulationFailedDetails = gson.fromJson(response.body().charStream(), DtoSimulationFailedDetails.class);
                    Platform.runLater(() ->{
                        simulationError.setText(dtoSimulationFailedDetails.getFailedReason());
                        simulationLabel.setText("Simulation id: " + dtoSimulationFailedDetails.getSimulationId() + ", Request id: " + dtoSimulationFailedDetails.getRequestId());
                        simulationStartTime.setText("Start time: " + dtoSimulationFailedDetails.getStartTime());
                    });
                } else{
                    AlertDialog.showError(response.message());
                }
            }
        });
    }
}
