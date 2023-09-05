package detailsComponent;

import details.*;
import detailsComponent.details.DetailsSelectorController;
import detailsComponent.details.entity.EntitySelectorController;
import detailsComponent.details.environment.EnvironmentDetailsControl;
import detailsComponent.details.rule.RuleSelectorController;
import detailsComponent.details.termination.TerminationController;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import pageComponent.PageController;
import termination.Termination;

import java.io.IOException;

public class DetailsFullComponentController {

    @FXML
    private GridPane details;
    @FXML
    private DetailsSelectorController detailsController;

    @FXML
    private Pane pane;
    private PageController pageController;

    private DtoWorldInfo dtoWorldInfo;

    //private EntitySelectorController entitySelectorController;

    public void setPageController(PageController pageController) {
        this.pageController = pageController;
        dtoWorldInfo = pageController.getLogicManager().getDtoWorldInfo(); // exepstion
        detailsController.setDetailsFullComponentController(this);
    }

    public DtoWorldInfo getDtoWorldInfo() {
        return dtoWorldInfo;
    }

    @FXML
    public void initialize(){

    }

    public void loadDetailsComponent(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/detailsSelector.fxml"));
            Parent details = loader.load();
            detailsController.setDetailsFullComponentController(this); //

            //DetailsSelectorController detailsSelectorController = loader.getController();

        } catch (IOException e) {}

    }


    public void showEnvironment(String enviromentName){
        try { // todo - enter to xenviroment controler
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/environment/environment.fxml"));
            Parent environment = loader.load();
            pane.getChildren().add(environment);

            DtoEnvironmentInfo dtoEnvironmentInfo = dtoWorldInfo.getDtoEnvironmentMap().get(enviromentName);
            EnvironmentDetailsControl environmentDetailsControl = loader.getController();
            environmentDetailsControl.setDtoEnvironmentInfo(dtoEnvironmentInfo);
            environmentDetailsControl.updateData();




        } catch (IOException e) {} //todo
    }


    public void showEntity(String entityName){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/entity/EntitySelector.fxml"));
            Parent entity = loader.load();
            pane.getChildren().add(entity);

            DtoEntityInfo dtoEntityInfo = dtoWorldInfo.getDtoEntityMap().get(entityName);
            EntitySelectorController entitySelectorController= loader.getController();
            entitySelectorController.setDtoEntityInfo(dtoEntityInfo);
            entitySelectorController.updateData();

        } catch (IOException e) {} //todo
    }



    public void clearPage(){
        pane.getChildren().clear();
    }

    public void showRule(String ruleName){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/rule/rule.fxml"));
            Parent entity = loader.load();
            pane.getChildren().add(entity);

            DtoRuleInfo dtoRuleInfo = dtoWorldInfo.getDtoRuleMap().get(ruleName);

            RuleSelectorController ruleSelectorController = loader.getController();
            ruleSelectorController.setDtoRuleInfo(dtoRuleInfo);
            ruleSelectorController.updateData();

        } catch (IOException e) {}//todo
    }


    public void showTermination() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/termination/termination.fxml"));
            Parent entity = loader.load();
            pane.getChildren().add(entity);

            DtoTermination dtoTermination = dtoWorldInfo.getDtoTermination();
            TerminationController terminationController = loader.getController();
            terminationController.setDtoTermination(dtoTermination);
            terminationController.updateData();


        } catch (IOException e) {}//todo
    }
}
