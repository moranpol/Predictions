package ThreadInfoRefresher;

import com.google.gson.Gson;
import details.DtoWorldsList;
import header.DtoSimulationQueue;
import http.HttpClientUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.TimerTask;
import java.util.function.Consumer;

public class ThreadInfoRefresher extends TimerTask {
    private final Consumer<DtoSimulationQueue> threadInfoConsumer;

    public ThreadInfoRefresher(Consumer<DtoSimulationQueue> threadInfoConsumer) {
        this.threadInfoConsumer = threadInfoConsumer;
    }

    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse("http://localhost:8080/predictions/threadInfo")
                .newBuilder()
                .build()
                .toString();

        HttpClientUtil.runAsyncGet(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    assert response.body() != null;
                    DtoSimulationQueue dtoSimulationQueue = gson.fromJson(response.body().charStream(), DtoSimulationQueue.class);
                    threadInfoConsumer.accept(dtoSimulationQueue);
                }
            }
        });
    }
}
