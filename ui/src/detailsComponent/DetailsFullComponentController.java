package detailsComponent;

import animations.RotateFirstPageButtons;
import details.*;
import details.DtoAction.DtoGridInfo;
import detailsComponent.details.DetailsSelectorController;
import detailsComponent.details.entity.EntitySelectorController;
import detailsComponent.details.environment.EnvironmentDetailsControl;
import detailsComponent.details.grid.GridController;
import detailsComponent.details.rule.RuleSelectorController;
import detailsComponent.details.termination.TerminationController;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import pageComponent.PageController;

import java.io.IOException;

public class DetailsFullComponentController {
    @FXML
    private GridPane details;

    @FXML
    private DetailsSelectorController detailsController;

    @FXML
    private Pane pane;

    private DtoWorldInfo dtoWorldInfo;

    public void setPageController(PageController pageController) {
        dtoWorldInfo = pageController.getLogicManager().getDtoWorldInfo();
        detailsController.setDetailsFullComponentController(this);
    }

    public RotateFirstPageButtons getAnimation(){
        return detailsController.getAnimation();
    }

    public DtoWorldInfo getDtoWorldInfo() {
        return dtoWorldInfo;
    }

    public void showEnvironment(String environmentName){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/environment/EnvironmentDetails.fxml"));
            Parent environment = loader.load();
            pane.getChildren().add(environment);

            DtoEnvironmentInfo dtoEnvironmentInfo = dtoWorldInfo.getDtoEnvironmentMap().get(environmentName);
            EnvironmentDetailsControl environmentDetailsControl = loader.getController();
            environmentDetailsControl.setDtoEnvironmentInfo(dtoEnvironmentInfo);
            environmentDetailsControl.updateData();
        } catch (IOException ignore) {}
    }


    public void showEntity(String entityName){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/entity/EntitySelector.fxml"));
            Parent entity = loader.load();
            pane.getChildren().add(entity);

            DtoEntityInfo dtoEntityInfo = dtoWorldInfo.getDtoEntityMap().get(entityName);
            EntitySelectorController entitySelectorController = loader.getController();
            entitySelectorController.setDtoEntityInfo(dtoEntityInfo);
            entitySelectorController.updateData();
        } catch (IOException ignore) {}
    }

    public void clearPage(){
        pane.getChildren().clear();
    }

    public void showRule(String ruleName){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/rule/RuleSelector.fxml"));
            Parent rule = loader.load();
            pane.getChildren().add(rule);

            DtoRuleInfo dtoRuleInfo = dtoWorldInfo.getDtoRuleMap().get(ruleName);

            RuleSelectorController ruleSelectorController = loader.getController();
            ruleSelectorController.setDtoRuleInfo(dtoRuleInfo);
            ruleSelectorController.updateData();
        } catch (IOException ignore) {}
    }


    public void showTermination() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/termination/Termination.fxml"));
            Parent termination = loader.load();
            pane.getChildren().add(termination);

            DtoTermination dtoTermination = dtoWorldInfo.getDtoTermination();
            TerminationController terminationController = loader.getController();
            terminationController.setDtoTermination(dtoTermination);
            terminationController.updateData();
        } catch (IOException ignore) {}
    }

    public void showGrid() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/grid/Grid.fxml"));
            Parent grid = loader.load();
            pane.getChildren().add(grid);

            DtoGridInfo dtoGridInfo = dtoWorldInfo.getDtoGridInfo();
            GridController gridController = loader.getController();
            gridController.setter(dtoGridInfo);
        } catch (IOException ignore) {}
    }
}
