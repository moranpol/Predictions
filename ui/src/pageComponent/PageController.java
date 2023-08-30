package pageComponent;
import headerComponent.HeaderController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import manager.LogicManager;

import java.io.IOException;

public class PageController {

    @FXML
    private GridPane header;
    @FXML
    private HeaderController headerController;

    @FXML
    private Pane paneBody;

    private LogicManager logicManager;

    @FXML
    public void initialize(){
        headerController.setPageController(this);
        logicManager = new LogicManager();
    }

    public LogicManager getLogicManager() {
        return logicManager;
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

