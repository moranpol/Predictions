package refresher;

import alert.AlertDialog;
import com.google.gson.Gson;
import http.HttpClientUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import results.simulations.DtoSimulationsInfoList;

import java.io.IOException;
import java.util.TimerTask;
import java.util.function.Consumer;

public class SimulationsInfoRefresher extends TimerTask {
    private final String username;
    private final Consumer<DtoSimulationsInfoList> dtoSimulationsInfoListConsumer;

    public SimulationsInfoRefresher(String username, Consumer<DtoSimulationsInfoList> dtoSimulationsInfoListConsumer) {
        this.username = username;
        this.dtoSimulationsInfoListConsumer = dtoSimulationsInfoListConsumer;
    }

    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse("http://localhost:8080/predictions/simulationsInfoUser")
                .newBuilder()
                .addQueryParameter("username", username)
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
                    DtoSimulationsInfoList dtoSimulationsInfoList = gson.fromJson(response.body().charStream(), DtoSimulationsInfoList.class);
                    dtoSimulationsInfoListConsumer.accept(dtoSimulationsInfoList);
                } else{
                    AlertDialog.showError(response.message());
                }
            }
        });
    }
}
