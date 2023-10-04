package refresher;


import com.google.gson.Gson;
import details.DtoWorldsList;
import error.ErrorDialog;
import http.HttpClientUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import requests.DtoRequestsInfo;

import java.io.IOException;
import java.util.TimerTask;
import java.util.function.Consumer;

public class RequestsRefresher extends TimerTask {
    private final Consumer<DtoRequestsInfo> requestsConsumer;
    private final String username;

    public RequestsRefresher(Consumer<DtoRequestsInfo> requestsConsumer, String username) {
        this.requestsConsumer = requestsConsumer;
        this.username = username;
    }

    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse("http://localhost:8080/predictions/userRequests")
                .newBuilder()
                .addQueryParameter("username", username)
                .build()
                .toString();

        HttpClientUtil.runAsyncGet(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                ErrorDialog.showError(e.getMessage());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    assert response.body() != null;
                    DtoRequestsInfo dtoRequestsInfo = gson.fromJson(response.body().charStream(), DtoRequestsInfo.class);
                    requestsConsumer.accept(dtoRequestsInfo);
                } else{
                    ErrorDialog.showError(response.message());
                }
            }
        });
    }
}
