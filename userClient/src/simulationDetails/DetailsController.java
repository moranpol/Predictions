package simulationDetails;

import details.DtoWorldsList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import worldInfoRefresher.WorldInfoRefresher;

import java.util.Timer;

public class DetailsController {

    @FXML
    private Pane detailsPane;

    @FXML
    private Button showWorldButton;

    @FXML
    private ComboBox<String> worldCBox;

    private WorldInfoRefresher worldInfoRefresher;

    private Timer timer;

    public void setWorldCBox(DtoWorldsList dtoWorldsList){
        Platform.runLater(() -> {
            ObservableList<String> worldNames = FXCollections.observableArrayList(dtoWorldsList.getWorldsName());
            worldCBox.setItems(worldNames);
        });
    }

    @FXML
    void showWorldButtonClicked(ActionEvent event) {

    }


    public void worldListRefresher() {
        worldInfoRefresher = new WorldInfoRefresher(this::setWorldCBox);
        timer = new Timer();
        timer.schedule(worldInfoRefresher, 2000, 2000);
    }
}
