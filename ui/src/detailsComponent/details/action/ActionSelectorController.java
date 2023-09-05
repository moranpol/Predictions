package detailsComponent.details.action;
import details.DtoAction.DtoAction;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ActionSelectorController {

    @FXML
    private Text DetailsVariableText;

    @FXML
    private Text mainEntityNameVariableText;

    @FXML
    private Text nameVariableText;

    @FXML
    private Text secondaryEntityNameVariableText;


    public void updateAction(DtoAction dtoAction){ // todo - seperite for every one
        mainEntityNameVariableText.setText(dtoAction.getMainEntityName());
        secondaryEntityNameVariableText.setText(dtoAction.getSecondaryEntityName());
    }
}

