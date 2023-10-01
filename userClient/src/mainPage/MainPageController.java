package mainPage;

import header.HeaderController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import simulationDetails.DetailsController;

import java.io.IOException;

public class MainPageController {

    @FXML
    private Pane bodyPane;

    private DetailsController detailsController;

    @FXML
    private GridPane header;

    @FXML
    private HeaderController headerController;

    public void initialize(){
        headerController.setter(this);
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

}
