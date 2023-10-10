package header;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import mainPage.MainPageController;

public class HeaderController {

    @FXML
    private Button executionButton;

    @FXML
    private Button requestButton;

    @FXML
    private Button resultsButton;

    @FXML
    private Button simulationDetailsButton;

    @FXML
    private Label userNameLabel;

    private MainPageController mainPageController;

    public void setter(MainPageController mainPageController, String username){
        setMainPageController(mainPageController);
        setUserNameLabel(username);
    }

    private void setUserNameLabel(String username){
        this.userNameLabel.setText("Name: " + username);
    }

    private void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    @FXML
    void detailsButtonClicked(ActionEvent event) {
        mainPageController.loadDetailsController();
    }

    @FXML
    void executionButtonClicked(ActionEvent event) {

    }

    @FXML
    void requestButtonClicked(ActionEvent event) {
        mainPageController.loadRequestsControllerController();
    }

    @FXML
    void resultsButtonClicked(ActionEvent event) {
        mainPageController.loadResultsController();
    }

    public void setResultsButton() {
        resultsButton.setDisable(false);
    }
}
