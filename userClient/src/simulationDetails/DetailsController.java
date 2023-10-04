package simulationDetails;

import com.google.gson.Gson;
import details.DtoWorldInfo;
import details.DtoWorldsList;
import error.ErrorDialog;
import http.HttpClientUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import simulationDetails.worldDetails.WorldDetailsController;
import refresher.WorldInfoRefresher;

import java.io.Closeable;
import java.io.IOException;
import java.util.Timer;

public class DetailsController implements Closeable {

    @FXML
    private Pane detailsPane;

    @FXML
    private Button showWorldButton;

    @FXML
    private ComboBox<String> worldCBox;

    private WorldInfoRefresher worldInfoRefresher;

    private Timer timer;

    public void setWorldCBox(DtoWorldsList dtoWorldsList){
        ObservableList<String> worldNames = FXCollections.observableArrayList(dtoWorldsList.getWorldsName());
        if(!worldNames.equals(worldCBox.getItems())) {
            Platform.runLater(() -> worldCBox.setItems(worldNames));
        }
    }

    @FXML
    void showWorldButtonClicked(ActionEvent event) {
        if(worldCBox.getValue() != null){
            String finalUrl = HttpUrl
                    .parse("http://localhost:8080/predictions/worldInfo")
                    .newBuilder()
                    .addQueryParameter("world name", worldCBox.getValue())
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
                        DtoWorldInfo dtoWorldInfo = gson.fromJson(response.body().charStream(), DtoWorldInfo.class);
                        loadWorldDetailsController(dtoWorldInfo);
                    } else{
                        ErrorDialog.showError(response.message());
                    }
                }
            });
        }
    }

    private void loadWorldDetailsController(DtoWorldInfo dtoWorldInfo){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/simulationDetails/worldDetails/WorldDetails.fxml"));
            Parent details = loader.load();
            Platform.runLater(() -> {
                detailsPane.getChildren().clear();
                detailsPane.getChildren().add(details);
            });

            WorldDetailsController worldDetailsController = loader.getController();
            worldDetailsController.setter(dtoWorldInfo);
        } catch (IOException ignore) {
        }
    }

    public void worldListRefresher() {
        worldInfoRefresher = new WorldInfoRefresher(this::setWorldCBox);
        timer = new Timer();
        timer.schedule(worldInfoRefresher, 2000, 1);
    }

    @Override
    public void close() throws IOException {
        timer.cancel();
        worldInfoRefresher.cancel();
    }
}
