package mainPage;

import execution.executionStart.ExecutionStartController;
import execution.newExecution.NewExecutionController;
import header.HeaderController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import loginHeader.LoginHeaderController;
import newExecution.DtoStartExecution;
import requests.RequestsController;
import results.ResultsController;
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

    private String userName;

    public void initialize(){
        loadLoginHeaderController();
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void loadLoginHeaderController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginHeader/LoginHeader.fxml"));
            Parent login = loader.load();
            Platform.runLater(() -> {
                headerPane.getChildren().clear();
                headerPane.getChildren().add(login);
            });

            LoginHeaderController loginHeaderController = loader.getController();
            loginHeaderController.setter(this);
        } catch (IOException ignore) {
        }
    }

    public void loadHeaderController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/header/Header.fxml"));
            Parent header = loader.load();
            Platform.runLater(() -> {
                headerPane.getChildren().clear();
                headerPane.getChildren().add(header);
            });

            headerController = loader.getController();
            headerController.setter(this, userName);
        } catch (IOException ignore) {
        }
    }

    public void loadDetailsController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/simulationDetails/Details.fxml"));
            Parent details = loader.load();
            Platform.runLater(() ->{
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(details);
            });

            detailsController = loader.getController();
            detailsController.worldListRefresher();
        } catch (IOException ignore) {
        }
    }

    public void loadRequestsControllerController(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/requests/Requests.fxml"));
            Parent details = loader.load();
            Platform.runLater(() -> {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(details);
            });

            requestsController = loader.getController();
            requestsController.setter(this);
        } catch (IOException ignore) {
        }
    }

    public void loadNewExecutionController(Integer requestId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/execution/newExecution/NewExecution.fxml"));
            Parent newExecution = loader.load();
            Platform.runLater(() -> {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(newExecution);
            });

            NewExecutionController newExecutionController = loader.getController();
            newExecutionController.setter(this, requestId);
        } catch (IOException ignored) {
        }
    }

    public void loadExecutionStartController(DtoStartExecution dtoStartExecution) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/execution/executionStart/ExecutionStart.fxml"));
            Parent executionStart = loader.load();
            Platform.runLater(() -> {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(executionStart);
            });

            ExecutionStartController executionStartController = loader.getController();
            executionStartController.setter(dtoStartExecution, this);
        } catch (IOException ignored) {
        }
    }

    public void loadResultsController() {
        try {
            headerController.setResultsButton();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/results/Results.fxml"));
            Parent results = loader.load();
            Platform.runLater(() -> {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(results);
            });

            ResultsController resultsController = loader.getController();
            resultsController.setter(this);
        } catch (IOException ignored) {
        }
    }
}
