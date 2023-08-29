package detailsComponent.details.rule;
import detailsComponent.details.action.ActionSelectorController;
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
    private ComboBox<?> actioncBoxChoice;

    @FXML
    private Text activationVariableText;

    @FXML
    private Text nameVariableText;

}

