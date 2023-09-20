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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

public class RuleSelectorController {

    @FXML
    private Pane paneAction;

    @FXML
    private TreeView<String> actionsTreeView;

    @FXML
    private Text activationVariableText;

    @FXML
    private Text nameVariableText;

    private DtoRuleInfo dtoRuleInfo;

    @FXML
    private Button selectActionButton;

    @FXML
    private GridPane gridPane;

    public void setDtoRuleInfo(DtoRuleInfo dtoRuleInfo) {
        this.dtoRuleInfo = dtoRuleInfo;
    }

    public void updateData(){
        nameVariableText.setText(dtoRuleInfo.getName());
        activationVariableText.setText(createStrActivation());
        setActionsTreeView(dtoRuleInfo.getDtoActions());
    }

    private void setActionsTreeView(List<DtoAction> dtoActions){
        TreeItem<String> root = new TreeItem<>("Select Action");
        for(DtoAction dtoAction : dtoActions){
            root.getChildren().add(new TreeItem<>(dtoAction.getName()));
        }

        actionsTreeView.setRoot(root);
    }

    @FXML
    private void SelectActionClicked(ActionEvent event) {
        TreeItem<String> selectedItem = actionsTreeView.getSelectionModel().getSelectedItem();
        if(selectedItem != null && selectedItem.isLeaf()) {
            int i = 0;
            for (TreeItem<String> treeItem : actionsTreeView.getRoot().getChildren()) {
                if (treeItem.equals(selectedItem)) {
                    break;
                }
                i++;
            }
            DtoAction dtoAction = dtoRuleInfo.getDtoActions().get(i);
            showActionPain(dtoAction);
        }
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/action/simpleCondition/SimpleCondition.fxml"));
            Parent simpleConditionInfo  = loader.load();

            SimpleConditionController simpleConditionController = loader.getController();
            simpleConditionController.setDtoSimpleCondition(dtoAction);
            simpleConditionController.updateData();

            paneAction.getChildren().clear();
            paneAction.getChildren().add(simpleConditionInfo);
        } catch (IOException ignore) {}
    }

    private void createSetActionToShow(DtoSet dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/action/set/Set.fxml"));
            Parent setInfo  = loader.load();

            SetController setController = loader.getController();
            setController.setDtoSet(dtoAction);
            setController.updateData();

            paneAction.getChildren().clear();
            paneAction.getChildren().add(setInfo);
        } catch (IOException ignore) {}
    }

    private void createReplaceActionToShow(DtoReplace dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/action/replace/Replace.fxml"));
            Parent replaceInfo  = loader.load();

            ReplaceController replaceController = loader.getController();
            replaceController.setDtoReplace(dtoAction);
            replaceController.updateData();

            paneAction.getChildren().clear();
            paneAction.getChildren().add(replaceInfo);
        } catch (IOException ignore) {}
    }

    private void createProximityActionToShow(DtoProximity dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/action/proximity/Proximity.fxml"));
            Parent proximityInfo  = loader.load();

            ProximityController proximityController = loader.getController();
            proximityController.setDtoProximity(dtoAction);
            proximityController.updateData();

            paneAction.getChildren().clear();
            paneAction.getChildren().add(proximityInfo);
        } catch (IOException ignore) {}
    }

    private void createMultipleConditionActionToShow(DtoMultipleCondition dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/action/multipleCondition/MultipleCondition.fxml"));
            Parent multipleConditionInfo  = loader.load();

            MultipleConditionController multipleConditionController = loader.getController();
            multipleConditionController.setDtoMultipleCondition(dtoAction);
            multipleConditionController.updateData();

            paneAction.getChildren().clear();
            paneAction.getChildren().add(multipleConditionInfo);
        } catch (IOException ignore) {}
    }

    private void createKillActionToShow(DtoKill dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/action/kill/Kill.fxml"));
            Parent killInfo  = loader.load();

            KillController killController = loader.getController();
            killController.setDtoKill(dtoAction);
            killController.updateData();

            paneAction.getChildren().clear();
            paneAction.getChildren().add(killInfo);
        } catch (IOException ignore) {}
    }

    private void createIncreaseActionToShow(DtoIncrease dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/action/increase/Increase.fxml"));
            Parent increaseInfo  = loader.load();

            IncreaseController increaseController = loader.getController();
            increaseController.setDtoIncrease(dtoAction);
            increaseController.updateData();

            paneAction.getChildren().clear();
            paneAction.getChildren().add(increaseInfo);
        } catch (IOException ignore) {}
    }

    private void createDecreaseActionToShow(DtoDecrease dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/action/decrease/Decrease.fxml"));
            Parent decreaseInfo  = loader.load();

            DecreaseController decreaseController = loader.getController();
            decreaseController.setDtoDecrease(dtoAction);
            decreaseController.updateData();

            paneAction.getChildren().clear();
            paneAction.getChildren().add(decreaseInfo);
        } catch (IOException ignore) {}
    }

    private void createCalculationActionToShow(DtoCalculation dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/action/calculation/Calculation.fxml"));
            Parent calculationInfo  = loader.load();

            CalculationController calculationController = loader.getController();
            calculationController.setDtoCalculation(dtoAction);
            calculationController.updateData();

            paneAction.getChildren().clear();
            paneAction.getChildren().add(calculationInfo);
        } catch (IOException ignore) {}
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

