package detailsComponent.details.rule;
import details.DtoAction.*;
import details.DtoRuleInfo;
import detailsComponent.details.action.calculation.CalculationController;
import detailsComponent.details.action.decrease.DecreaseController;
import detailsComponent.details.action.increase.IncreaseController;
import detailsComponent.details.action.kill.KillController;
import detailsComponent.details.action.multipleCondition.MultipleConditionController;
import detailsComponent.details.action.proximity.ProximityController;
import detailsComponent.details.action.replace.ReplaceController;
import detailsComponent.details.action.set.SetController;
import detailsComponent.details.action.simpleCondition.SimpleConditionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import rule.action.MultipleCondition;

import java.io.IOException;

public class RuleSelectorController {

    @FXML
    private Pane paneAction;

    @FXML
    private ComboBox<String> actioncBox;

    @FXML
    private Text activationVariableText;

    @FXML
    private Text nameVariableText;

    private DtoRuleInfo dtoRuleInfo;

    public void setDtoRuleInfo(DtoRuleInfo dtoRuleInfo) {
        this.dtoRuleInfo = dtoRuleInfo;
    }

    public void updateData(){
        nameVariableText.setText(dtoRuleInfo.getName());
        activationVariableText.setText(createStrActivation());

        ObservableList<String> propertyNames = FXCollections.observableArrayList(dtoRuleInfo.getDtoActionMap().keySet());
        actioncBox.setItems(propertyNames);
    }

    @FXML
    void actioncBoxChoice(ActionEvent event) {

        showActionPain(dtoRuleInfo.getDtoActionMap().get(actioncBox.getValue()));

    }

    private void showActionPain(DtoAction dtoAction) {
        switch (dtoAction.getName()){
            case "Calculation":
                createCalculationActionToShow((DtoCalculation)dtoAction);
                break;
            case "Decrease":
                createDecreaseActionToShow((DtoDecrease)dtoAction);
                break;
            case "Increase":
                createIncreaseActionToShow((DtoIncrease)dtoAction);
                break;
            case "Kill":
                createKillActionToShow((DtoKill)dtoAction);
                break;
            case "Multiple Condition":
                createMultipleConditionActionToShow((DtoMultipleCondition)dtoAction);
                break;
            case "Proximity":
                createProximityActionToShow((DtoProximity)dtoAction);
                break;
            case "Replace":
                createReplaceActionToShow((DtoReplace)dtoAction);
                break;
            case "Set":
                createSetActionToShow((DtoSet)dtoAction);
                break;
            case "Single Condition":
                createSingleConditionActionToShow((DtoSimpleCondition)dtoAction);
                break;
        }

    }

    private void createSingleConditionActionToShow(DtoSimpleCondition dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/action/simpleCondition/simpleCondition.fxml"));
            Parent simpleConditionInfo  = loader.load();

            SimpleConditionController simpleConditionController = loader.getController();
            simpleConditionController.setDtoSimpleCondition(dtoAction);
            simpleConditionController.updateData();

            paneAction.getChildren().clear();
            paneAction.getChildren().add(simpleConditionInfo);

        } catch (IOException e) {}//todo
    }

    private void createSetActionToShow(DtoSet dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/action/set/set.fxml"));
            Parent setInfo  = loader.load();

            SetController setController = loader.getController();
            setController.setDtoSet(dtoAction);
            setController.updateData();

            paneAction.getChildren().clear();
            paneAction.getChildren().add(setInfo);

        } catch (IOException e) {}//todo
    }

    private void createReplaceActionToShow(DtoReplace dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/action/replace/replace.fxml"));
            Parent replaceInfo  = loader.load();

            ReplaceController replaceController = loader.getController();
            replaceController.setDtoReplace(dtoAction);
            replaceController.updateData();

            paneAction.getChildren().clear();
            paneAction.getChildren().add(replaceInfo);

        } catch (IOException e) {}//todo
    }

    private void createProximityActionToShow(DtoProximity dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/action/proximity/proximity.fxml"));
            Parent proximityInfo  = loader.load();

            ProximityController proximityController  = loader.getController();
            proximityController.setDtoProximity(dtoAction);
            proximityController.updateData();

            paneAction.getChildren().clear();
            paneAction.getChildren().add(proximityInfo);

        } catch (IOException e) {}//todo
    }

    private void createMultipleConditionActionToShow(DtoMultipleCondition dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/action/multipleCondition/multipleCondition.fxml"));
            Parent multipleConditionInfo  = loader.load();

            MultipleConditionController multipleCondition  = loader.getController();
            multipleCondition.setDtoMultipleCondition(dtoAction);
            multipleCondition.updateData();

            paneAction.getChildren().clear();
            paneAction.getChildren().add(multipleConditionInfo);

        } catch (IOException e) {}//todo
    }

    private void createKillActionToShow(DtoKill dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/action/kill/kill.fxml"));
            Parent killInfo  = loader.load();

            KillController killController = loader.getController();
            killController.setDtoKill(dtoAction);
            killController.updateData();

            paneAction.getChildren().clear();
            paneAction.getChildren().add(killInfo);

        } catch (IOException e) {}//todo
    }

    private void createIncreaseActionToShow(DtoIncrease dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/action/increase/increase.fxml"));
            Parent increaseInfo  = loader.load();

            IncreaseController increaseController   = loader.getController();
            increaseController.setDtoIncrease(dtoAction);
            increaseController.updateData();

            paneAction.getChildren().clear();
            paneAction.getChildren().add(increaseInfo);

        } catch (IOException e) {}//todo
    }

    private void createDecreaseActionToShow(DtoDecrease dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/action/decrease/decrease.fxml"));
            Parent decreaseInfo  = loader.load();

            DecreaseController decreaseController  = loader.getController();
            decreaseController.setDtoDecrease(dtoAction);
            decreaseController.updateData();

            paneAction.getChildren().clear();
            paneAction.getChildren().add(decreaseInfo);

        } catch (IOException e) {}//todo
    }

    private void createCalculationActionToShow(DtoCalculation dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/action/calculation/calculation.fxml"));
            Parent calculationInfo  = loader.load();

            CalculationController calculationController = loader.getController();
            calculationController.setDtoCalculation(dtoAction);
            calculationController.updateData();

            paneAction.getChildren().clear();
            paneAction.getChildren().add(calculationInfo);

        } catch (IOException e) {}//todo
    }


    private String createStrActivation(){
        Integer ticks = dtoRuleInfo.getDtoActivation().getTicks();
        Double probability = dtoRuleInfo.getDtoActivation().getProbability();
        String strTicks,strProbability;
        if(ticks != null){
            strTicks = ticks.toString();
        }
        else{
            strTicks= "Not Exist";
        }
        if(probability != null){
            strProbability =probability.toString();
        }
        else{
            strProbability ="Not Exist";
        }
        return "Ticks: " +strTicks +"\nProbability: "+ strProbability;
    }
}

