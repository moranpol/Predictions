package detailsComponent.details.rule;
import details.DtoRuleInfo;
import detailsComponent.details.action.ActionSelectorController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class RuleSelectorController {

    @FXML
    private GridPane action;  //
    @FXML
    private ActionSelectorController actionSelectorController;//

    @FXML
    private ComboBox<String> actioncBoxChoice;

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
        actioncBoxChoice.setItems(propertyNames);
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

