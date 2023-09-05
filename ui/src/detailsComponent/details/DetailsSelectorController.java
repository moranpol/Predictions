package detailsComponent.details;
import detailsComponent.DetailsFullComponentController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import pageComponent.PageController;

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
    private PageController pageController;



    public void setPageController(PageController pageController) {
        this.pageController = pageController;
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

    }

    @FXML
    void entitiesShowButtonClicked(ActionEvent event) {
        detailsFullComponentController.clearPage();
        detailsFullComponentController.showEntity(entitiesCBox.getValue());

    }

    @FXML
    void environmentCBoxChoice(ActionEvent event) {

    }

    @FXML
    void environmentShowButtonClicked(ActionEvent event) {
        detailsFullComponentController.clearPage();
        detailsFullComponentController.showEnvironment(environmentCBox.getValue());
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
        detailsFullComponentController.showRule(rulesCBox.getValue());
    }

    @FXML
    void terminationShowButtonClicked(ActionEvent event) {
        detailsFullComponentController.clearPage();
        detailsFullComponentController.showTermination();
    }

}

