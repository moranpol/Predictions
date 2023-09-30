package detailsComponent.details;
import animations.RotateFirstPageButtons;
import detailsComponent.DetailsFullComponentController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
//import pageComponent.PageController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import rule.Rule;

public class DetailsSelectorController {

    @FXML
    private ComboBox<String> entitiesCBox;

    @FXML
    private Button entitiesShowButton;

    @FXML
    private ComboBox<String> environmentCBox;

    @FXML
    private Button environmentShowButton;

    @FXML
    private Button gridShowButton;

    @FXML
    private ComboBox<String> rulesCBox;

    @FXML
    private Button rulesShowButton;

    @FXML
    private Button terminationShowButton;

    private DetailsFullComponentController detailsFullComponentController;

    private RotateFirstPageButtons animation;

    public void initialize(){
        entitiesShowButton.setDisable(true);
        environmentShowButton.setDisable(true);
        rulesShowButton.setDisable(true);
        animation = new RotateFirstPageButtons(gridShowButton, entitiesShowButton, environmentShowButton, rulesShowButton, terminationShowButton);
    }

    public RotateFirstPageButtons getAnimation() {
        return animation;
    }

    public void setDetailsFullComponentController(DetailsFullComponentController detailsFullComponentController) {
        this.detailsFullComponentController = detailsFullComponentController;
        ObservableList<String> entitiesNames = FXCollections.observableArrayList(detailsFullComponentController.getDtoWorldInfo().getDtoEntityMap().keySet());
        ObservableList<String> environmentNames = FXCollections.observableArrayList(detailsFullComponentController.getDtoWorldInfo().getDtoEnvironmentMap().keySet());
        ObservableList<String> rules = FXCollections.observableArrayList(detailsFullComponentController.getDtoWorldInfo().getDtoRuleMap().keySet());

        entitiesCBox.setItems(entitiesNames);
        environmentCBox.setItems(environmentNames);
        rulesCBox.setItems(rules);
    }

    @FXML
    void entitiesCBoxChoice(ActionEvent event) {
        if(entitiesCBox.getValue() != null) {
            entitiesShowButton.setDisable(false);
        }
    }

    @FXML
    void entitiesShowButtonClicked(ActionEvent event) {
        environmentShowButton.setDisable(true);
        rulesShowButton.setDisable(true);
        environmentCBox.setValue(null);
        rulesCBox.setValue(null);
        detailsFullComponentController.clearPage();
        detailsFullComponentController.showEntity(entitiesCBox.getValue());
    }

    @FXML
    void environmentCBoxChoice(ActionEvent event) {
        if(environmentCBox.getValue() != null) {
            environmentShowButton.setDisable(false);
        }
    }

    @FXML
    void environmentShowButtonClicked(ActionEvent event) {
        entitiesShowButton.setDisable(true);
        rulesShowButton.setDisable(true);
        entitiesCBox.setValue(null);
        rulesCBox.setValue(null);
        detailsFullComponentController.clearPage();
        detailsFullComponentController.showEnvironment(environmentCBox.getValue());
    }

    @FXML
    void gridShowButtonClicked(ActionEvent event) {
        environmentShowButton.setDisable(true);
        entitiesShowButton.setDisable(true);
        rulesShowButton.setDisable(true);
        environmentCBox.setValue(null);
        entitiesCBox.setValue(null);
        rulesCBox.setValue(null);
        detailsFullComponentController.clearPage();
        detailsFullComponentController.showGrid();
    }

    @FXML
    void rulesCBoxChoice(ActionEvent event) {
        if(rulesCBox.getValue() != null) {
            rulesShowButton.setDisable(false);
        }
    }

    @FXML
    void rulesShowButtonClicked(ActionEvent event) {
        environmentShowButton.setDisable(true);
        entitiesShowButton.setDisable(true);
        environmentCBox.setValue(null);
        entitiesCBox.setValue(null);
        detailsFullComponentController.clearPage();
        detailsFullComponentController.showRule(rulesCBox.getValue());
    }

    @FXML
    void terminationShowButtonClicked(ActionEvent event) {
        environmentShowButton.setDisable(true);
        entitiesShowButton.setDisable(true);
        rulesShowButton.setDisable(true);
        environmentCBox.setValue(null);
        entitiesCBox.setValue(null);
        rulesCBox.setValue(null);
        detailsFullComponentController.clearPage();
        detailsFullComponentController.showTermination();
    }
}

