package detailsComponent.details;
import detailsComponent.DetailsFullComponentController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class DetailsSelectorController {

    @FXML
    private ComboBox<?> entitiesCBox;

    @FXML
    private Button entitiesShowButton;

    @FXML
    private ComboBox<?> environmentCBox;

    @FXML
    private Button environmentShowButton;

    @FXML
    private ComboBox<?> gridCBox;

    @FXML
    private Button gridShowButton;

    @FXML
    private ComboBox<?> rulesCBox;

    @FXML
    private Button rulesShowButton;

    @FXML
    private Button terminationShowButton;

    private DetailsFullComponentController detailsFullComponentController;

    @FXML
    public void initialize(){

    }

    public void setDetailsFullComponentController(DetailsFullComponentController detailsFullComponentController) {
        this.detailsFullComponentController = detailsFullComponentController;
    }

    @FXML
    void entitiesCBoxChoice(ActionEvent event) {

    }

    @FXML
    void entitiesShowButtonClicked(ActionEvent event) {
        detailsFullComponentController.clearPage();
        detailsFullComponentController.showEntity();

    }

    @FXML
    void environmentCBoxChoice(ActionEvent event) {

    }

    @FXML
    void environmentShowButtonClicked(ActionEvent event) {
        detailsFullComponentController.clearPage();
        detailsFullComponentController.showEnvironment();
    }

    @FXML
    void gridCBoxChoice(ActionEvent event) {

    }

    @FXML
    void gridShowButtonClicked(ActionEvent event) {

    }

    @FXML
    void rulesCBoxChoice(ActionEvent event) {

    }

    @FXML
    void rulesShowButtonClicked(ActionEvent event) {
        detailsFullComponentController.clearPage();
        detailsFullComponentController.showRule();
    }

    @FXML
    void terminationShowButtonClicked(ActionEvent event) {
        detailsFullComponentController.clearPage();
        detailsFullComponentController.showTermination();
    }

}

