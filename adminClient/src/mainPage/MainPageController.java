package mainPage;

import adminHeader.HeaderController;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/body/firstPageManagement/managementBody.fxml"));
            Parent management = loader.load();
            Platform.runLater(() -> {
                body.getChildren().clear();
                body.getChildren().add(management);
            });

        } catch (IOException ignore) {
        }
    }

    public void loadAllocationBodyController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/body/secondPage/allocation.fxml"));
            Parent allocation = loader.load();
            Platform.runLater(() -> {
                body.getChildren().clear();
                body.getChildren().add(allocation);
            });

        } catch (IOException ignore) {
        }
    }
}
