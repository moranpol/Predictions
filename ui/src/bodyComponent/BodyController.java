package bodyComponent;
import headerComponent.HeaderController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class BodyController {

    @FXML
    private GridPane header;
    @FXML
    private HeaderController headerController;

    @FXML
    private Pane paneBody;


    @FXML
    public void initialize(){
        headerController.setBodyController(this);
    }

    public void loadDetailsComponent(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/detailsFullComponent.fxml"));
            Parent details = loader.load();
            paneBody.getChildren().add(details);


            //DetailsSelectorController detailsSelectorController = loader.getController();

        }
        catch (IOException e) {}
    }



}

