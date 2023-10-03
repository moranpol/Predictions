package mainPage;

import body.firstPagemanagement.ManagementBodyControll;
import header.HeaderController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class MainPageController {

    @FXML
    private GridPane header;
    @FXML
    private HeaderController headerController;
    @FXML
    private AnchorPane body;

    public void initialize(){
        headerController.setter(this);
    }

    public void loadManagementBodyController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/body/firstPagemanagement/managementBody.fxml"));
            Parent management = loader.load();
            Platform.runLater(() -> {
                body.getChildren().clear();
                body.getChildren().add(management);
            });

            //ManagementBodyControll managementBodyControll = loader.getController();

        } catch (IOException ignore) {
        }
    }
}
