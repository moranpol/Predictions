package detailsComponent;

import detailsComponent.details.DetailsSelectorController;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class DetailsFullComponentController {

    @FXML
    private GridPane details;
    @FXML
    private DetailsSelectorController detailsController;

    @FXML
    private Pane pane;



    @FXML
    public void initialize(){
        detailsController.setDetailsFullComponentController(this);
    }

    public void loadDetailsComponent(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/detailsSelector.fxml"));
            Parent details = loader.load();
            //bodyLeftSide.getChildren().add(details); //



            //DetailsSelectorController detailsSelectorController = loader.getController();

        } catch (IOException e) {}

    }

    public void showEnvironment(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/environment/environment.fxml"));
            Parent environment = loader.load();
            pane.getChildren().add(environment);


        } catch (IOException e) {}
    }


    public void showEntity(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/entity/EntitySelector.fxml"));
            Parent entity = loader.load();
            pane.getChildren().add(entity);

        } catch (IOException e) {}
    }

    public void clearPage(){
        pane.getChildren().clear();
    }

    public void showRule(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/rule/rule.fxml"));
            Parent entity = loader.load();
            pane.getChildren().add(entity);

        } catch (IOException e) {}
    }


    public void showTermination() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/termination/termination.fxml"));
            Parent entity = loader.load();
            pane.getChildren().add(entity);

        } catch (IOException e) {}
    }
}
