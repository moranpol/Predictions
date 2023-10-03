package mainPage;

import header.HeaderController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import loginHeader.LoginHeaderController;
import requests.RequestsController;
import simulationDetails.DetailsController;

import java.io.IOException;

public class MainPageController {

    @FXML
    private Pane bodyPane;

    private DetailsController detailsController;

    @FXML
    private Pane headerPane;

    @FXML
    private HeaderController headerController;

    private RequestsController requestsController;

    public void initialize(){
        loadLoginHeaderController();

    }

    public void loadLoginHeaderController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginHeader/LoginHeader.fxml"));
            Parent login = loader.load();
            headerPane.getChildren().clear();
            headerPane.getChildren().add(login);

            LoginHeaderController loginHeaderController = loader.getController();
            loginHeaderController.setter(this);
        } catch (IOException ignore) {
        }
    }

    public void loadHeaderController(String username){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/header/Header.fxml"));
            Parent header = loader.load();
            bodyPane.getChildren().clear();
            bodyPane.getChildren().add(header);

            headerController = loader.getController();
            headerController.setter(this, username);
        } catch (IOException ignore) {
        }
    }

    public void loadDetailsController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/simulationDetails/Details.fxml"));
            Parent details = loader.load();
            bodyPane.getChildren().clear();
            bodyPane.getChildren().add(details);

            detailsController = loader.getController();
            detailsController.worldListRefresher();
        } catch (IOException ignore) {
        }
    }

    public void loadRequestsControllerController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/requests/Requests.fxml"));
            Parent details = loader.load();
            bodyPane.getChildren().clear();
            bodyPane.getChildren().add(details);

            requestsController = loader.getController();
        } catch (IOException ignore) {
        }
    }


}