package loginHeader;

import alert.AlertDialog;
import http.HttpClientUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import mainPage.MainPageController;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class LoginHeaderController {

    @FXML
    private TextField userNameTextField;

    private MainPageController mainPageController;

    public void setter(MainPageController mainPageController){
        setMainPageController(mainPageController);
    }

    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    @FXML
    void loginButtonClicked(ActionEvent event) {
        String userName = userNameTextField.getText();
        if (userName.isEmpty()) {
            AlertDialog.showError("User name is empty. You can't login with empty user name");
            return;
        }

        String finalUrl = HttpUrl
                .parse("http://localhost:8080/predictions/login")
                .newBuilder()
                .addQueryParameter("username", userName)
                .build()
                .toString();

        HttpClientUtil.runAsyncGet(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> AlertDialog.showError("Something went wrong: " + e.getMessage()));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() ->
                            AlertDialog.showError("Something went wrong: " + responseBody)
                    );
                } else {
                    Platform.runLater(() -> {
                        mainPageController.setUserName(userName);
                        mainPageController.loadHeaderController();
                    });
                }
            }
        });
    }

}


