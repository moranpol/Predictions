package body.firstPagemanagement.simulationDetails.worldDetails;

import body.firstPagemanagement.simulationDetails.worldDetails.entity.EntityDetailsController;
import body.firstPagemanagement.simulationDetails.worldDetails.environment.EnvironmentDetailsController;
import body.firstPagemanagement.simulationDetails.worldDetails.grid.GridDetailsController;
import body.firstPagemanagement.simulationDetails.worldDetails.rule.RuleDetailsController;
import details.DtoWorldInfo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class WorldDetailsController {

    @FXML
    private ComboBox<String> entitiesCBox;

    @FXML
    private Button entitiesShowButton;

    @FXML
    private Button environmentShowButton;

    @FXML
    private Button gridShowButton;

    @FXML
    private ComboBox<String> rulesCBox;

    @FXML
    private Button rulesShowButton;

    @FXML
    private Pane pane;

    private DtoWorldInfo dtoWorldInfo;

    public void initialize(){
        entitiesShowButton.setDisable(true);
        rulesShowButton.setDisable(true);
    }

    public void setter(DtoWorldInfo dtoWorldInfo){
        this.dtoWorldInfo = dtoWorldInfo;
        ObservableList<String> entitiesNames = FXCollections.observableArrayList(dtoWorldInfo.getDtoEntityMap().keySet());
        ObservableList<String> rules = FXCollections.observableArrayList(dtoWorldInfo.getDtoRuleMap().keySet());

        entitiesCBox.setItems(entitiesNames);
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
        rulesShowButton.setDisable(true);
        rulesCBox.setValue(null);
        showEntity(entitiesCBox.getValue());
    }

    public void showEntity(String entityName){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/body/firstPagemanagement/simulationDetails/worldDetails/entity/EntityDetails.fxml"));
            Parent entity = loader.load();
            Platform.runLater(() -> {
                pane.getChildren().clear();
                pane.getChildren().add(entity);
            });

            EntityDetailsController entityDetailsController = loader.getController();
            entityDetailsController.setDtoEntityInfo(dtoWorldInfo.getDtoEntityMap().get(entityName));
            entityDetailsController.updateData();
        } catch (IOException ignore) {}
    }

    @FXML
    void environmentShowButtonClicked(ActionEvent event) {
        entitiesShowButton.setDisable(true);
        rulesShowButton.setDisable(true);
        entitiesCBox.setValue(null);
        rulesCBox.setValue(null);
        showEnvironment();
    }

    public void showEnvironment(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/body/firstPagemanagement/simulationDetails/worldDetails/environment/EnvironmentDetails.fxml"));
            Parent environment = loader.load();
            Platform.runLater(() -> {
                        pane.getChildren().clear();
                        pane.getChildren().add(environment);
                    });

            EnvironmentDetailsController environmentDetailsController = loader.getController();
            environmentDetailsController.setTable(dtoWorldInfo.getDtoEnvironmentList());
        } catch (IOException ignore) {}
    }

    @FXML
    void gridShowButtonClicked(ActionEvent event) {
        entitiesShowButton.setDisable(true);
        rulesShowButton.setDisable(true);
        entitiesCBox.setValue(null);
        rulesCBox.setValue(null);
        showGrid();
    }

    public void showGrid() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/body/firstPagemanagement/simulationDetails/worldDetails/grid/GridDetails.fxml"));
            Parent grid = loader.load();
            Platform.runLater(() -> {
                        pane.getChildren().clear();
                        pane.getChildren().add(grid);
                    });

            GridDetailsController gridDetailsController = loader.getController();
            gridDetailsController.setter(dtoWorldInfo.getDtoGridInfo());
        } catch (IOException ignore) {}
    }

    @FXML
    void rulesCBoxChoice(ActionEvent event) {
        if(rulesCBox.getValue() != null) {
            rulesShowButton.setDisable(false);
        }
    }

    @FXML
    void rulesShowButtonClicked(ActionEvent event) {
        entitiesShowButton.setDisable(true);
        entitiesCBox.setValue(null);
        showRule(rulesCBox.getValue());
    }

    public void showRule(String ruleName){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/body/firstPagemanagement/simulationDetails/worldDetails/rule/RuleDetails.fxml"));
            Parent rule = loader.load();
            Platform.runLater(() -> {
                        pane.getChildren().clear();
                        pane.getChildren().add(rule);
                    });

            RuleDetailsController ruleDetailsController = loader.getController();
            ruleDetailsController.setDtoRuleInfo(dtoWorldInfo.getDtoRuleMap().get(ruleName));
            ruleDetailsController.updateData();
        } catch (IOException ignore) {}
    }
}
