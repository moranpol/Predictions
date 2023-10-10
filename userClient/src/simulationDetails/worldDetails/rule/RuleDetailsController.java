package simulationDetails.worldDetails.rule;

import details.DtoAction.DtoAction;
import details.DtoRuleInfo;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import simulationDetails.worldDetails.rule.actions.calculation.CalculationController;
import simulationDetails.worldDetails.rule.actions.decrease.DecreaseController;
import simulationDetails.worldDetails.rule.actions.increase.IncreaseController;
import simulationDetails.worldDetails.rule.actions.kill.KillController;
import simulationDetails.worldDetails.rule.actions.multipleCondition.MultipleConditionController;
import simulationDetails.worldDetails.rule.actions.proximity.ProximityController;
import simulationDetails.worldDetails.rule.actions.replace.ReplaceController;
import simulationDetails.worldDetails.rule.actions.set.SetController;
import simulationDetails.worldDetails.rule.actions.singleCondition.SingleConditionController;

import java.io.IOException;
import java.util.List;

public class RuleDetailsController {
    @FXML
    private VBox actionVBox;

    @FXML
    private TreeView<String> actionsTreeView;

    @FXML
    private Text activationVariableText;

    @FXML
    private Text nameVariableText;

    private DtoRuleInfo dtoRuleInfo;

    @FXML
    private Button selectActionButton;

    public void setDtoRuleInfo(DtoRuleInfo dtoRuleInfo) {
        this.dtoRuleInfo = dtoRuleInfo;
    }

    public void updateData(){
        Platform.runLater(() -> {
            nameVariableText.setText(dtoRuleInfo.getName());
            activationVariableText.setText(createStrActivation());
            setActionsTreeView(dtoRuleInfo.getDtoActions());
        });
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
                createCalculationActionToShow(dtoAction);
                break;
            case "Decrease":
                createDecreaseActionToShow(dtoAction);
                break;
            case "Increase":
                createIncreaseActionToShow(dtoAction);
                break;
            case "Kill":
                createKillActionToShow(dtoAction);
                break;
            case "Multiple Condition":
                createMultipleConditionActionToShow(dtoAction);
                break;
            case "Proximity":
                createProximityActionToShow(dtoAction);
                break;
            case "Replace":
                createReplaceActionToShow(dtoAction);
                break;
            case "Set":
                createSetActionToShow(dtoAction);
                break;
            case "Single Condition":
                createSingleConditionActionToShow(dtoAction);
                break;
        }
    }

    private void createSingleConditionActionToShow(DtoAction dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/simulationDetails/worldDetails/rule/actions/singleCondition/SingleCondition.fxml"));
            Parent simpleConditionInfo  = loader.load();
            Platform.runLater(() -> {
                actionVBox.getChildren().clear();
                actionVBox.getChildren().add(simpleConditionInfo);
            });

            SingleConditionController singleConditionController = loader.getController();
            singleConditionController.setDtoAction(dtoAction);
            singleConditionController.updateData();
        } catch (IOException ignore) {}
    }

    private void createSetActionToShow(DtoAction dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/simulationDetails/worldDetails/rule/actions/set/Set.fxml"));
            Parent setInfo  = loader.load();
            Platform.runLater(() -> {
                actionVBox.getChildren().clear();
                actionVBox.getChildren().add(setInfo);
            });

            SetController setController = loader.getController();
            setController.setDtoAction(dtoAction);
            setController.updateData();
        } catch (IOException ignore) {}
    }

    private void createReplaceActionToShow(DtoAction dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/simulationDetails/worldDetails/rule/actions/replace/Replace.fxml"));
            Parent replaceInfo  = loader.load();
            Platform.runLater(() -> {
                actionVBox.getChildren().clear();
                actionVBox.getChildren().add(replaceInfo);
            });

            ReplaceController replaceController = loader.getController();
            replaceController.setDtoAction(dtoAction);
            replaceController.updateData();
        } catch (IOException ignore) {}
    }

    private void createProximityActionToShow(DtoAction dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/simulationDetails/worldDetails/rule/actions/proximity/Proximity.fxml"));
            Parent proximityInfo  = loader.load();
            Platform.runLater(() -> {
                actionVBox.getChildren().clear();
                actionVBox.getChildren().add(proximityInfo);
            });

            ProximityController proximityController = loader.getController();
            proximityController.setDtoAction(dtoAction);
            proximityController.updateData();
        } catch (IOException ignore) {}
    }

    private void createMultipleConditionActionToShow(DtoAction dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/simulationDetails/worldDetails/rule/actions/multipleCondition/MultipleCondition.fxml"));
            Parent multipleConditionInfo  = loader.load();
            Platform.runLater(() -> {
                actionVBox.getChildren().clear();
                actionVBox.getChildren().add(multipleConditionInfo);
            });

            MultipleConditionController multipleConditionController = loader.getController();
            multipleConditionController.setDtoAction(dtoAction);
            multipleConditionController.updateData();
        } catch (IOException ignore) {}
    }

    private void createKillActionToShow(DtoAction dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/simulationDetails/worldDetails/rule/actions/kill/Kill.fxml"));
            Parent killInfo  = loader.load();
            Platform.runLater(() -> {
                actionVBox.getChildren().clear();
                actionVBox.getChildren().add(killInfo);
            });

            KillController killController = loader.getController();
            killController.setDtoAction(dtoAction);
            killController.updateData();
        } catch (IOException ignore) {}
    }

    private void createIncreaseActionToShow(DtoAction dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/simulationDetails/worldDetails/rule/actions/increase/Increase.fxml"));
            Parent increaseInfo  = loader.load();
            Platform.runLater(() -> {
                actionVBox.getChildren().clear();
                actionVBox.getChildren().add(increaseInfo);
            });

            IncreaseController increaseController = loader.getController();
            increaseController.setDtoAction(dtoAction);
            increaseController.updateData();
        } catch (IOException ignore) {}
    }

    private void createDecreaseActionToShow(DtoAction dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/simulationDetails/worldDetails/rule/actions/decrease/Decrease.fxml"));
            Parent decreaseInfo  = loader.load();
            Platform.runLater(() -> {
                actionVBox.getChildren().clear();
                actionVBox.getChildren().add(decreaseInfo);
            });

            DecreaseController decreaseController = loader.getController();
            decreaseController.setDtoAction(dtoAction);
            decreaseController.updateData();
        } catch (IOException ignore) {}
    }

    private void createCalculationActionToShow(DtoAction dtoAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/simulationDetails/worldDetails/rule/actions/calculation/Calculation.fxml"));
            Parent calculationInfo  = loader.load();
            Platform.runLater(() -> {
                actionVBox.getChildren().clear();
                actionVBox.getChildren().add(calculationInfo);
            });

            CalculationController calculationController = loader.getController();
            calculationController.setDtoAction(dtoAction);
            calculationController.updateData();
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
