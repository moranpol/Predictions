package refresher;

import alert.AlertDialog;
import com.google.gson.Gson;
import http.HttpClientUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import requests.DtoRequestsInfo;
import results.simulationRunningDetails.DtoSimulationRunningDetails;

import java.io.IOException;
import java.util.TimerTask;
import java.util.function.Consumer;

public class ExecutionDetailsRefresher extends TimerTask {
    private final Consumer<DtoSimulationRunningDetails> dtoSimulationRunningDetailsConsumer;
    private final Integer simulationId;
    private final Integer requestId;

    public ExecutionDetailsRefresher(Consumer<DtoSimulationRunningDetails> dtoSimulationRunningDetailsConsumer, Integer simulationId, Integer requestId) {
        this.dtoSimulationRunningDetailsConsumer = dtoSimulationRunningDetailsConsumer;
        this.simulationId = simulationId;
        this.requestId = requestId;
    }

    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse("http://localhost:8080/predictions/executionDetails")
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
                    DtoSimulationRunningDetails dtoSimulationRunningDetails = gson.fromJson(response.body().charStream(), DtoSimulationRunningDetails.class);
                    dtoSimulationRunningDetailsConsumer.accept(dtoSimulationRunningDetails);
                } else{
                    AlertDialog.showError(response.message());
                }
            }
        });
    }
}
