package detailsComponent.details.action.multipleCondition;

import details.DtoAction.DtoMultipleCondition;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MultipleConditionController {
    @FXML
    private VBox vBox;

    @FXML
    private Text conditionsAmountVariableText;

    @FXML
    private Text logicVariableText;

    @FXML
    private Text mainEntityNameVariableText;

    @FXML
    private Text nameVariableText;

    @FXML
    private Text secondaryEntityNameVariableText;

    private DtoMultipleCondition dtoMultipleCondition;

    public void setDtoMultipleCondition(DtoMultipleCondition dtoMultipleCondition) {
        this.dtoMultipleCondition = dtoMultipleCondition;
    }

    public void updateData() {
        mainEntityNameVariableText.setText(dtoMultipleCondition.getMainEntityName());
        nameVariableText.setText(dtoMultipleCondition.getName());
        conditionsAmountVariableText.setText(dtoMultipleCondition.getConditionsAmount().toString());
        logicVariableText.setText(dtoMultipleCondition.getLogic());

        if(dtoMultipleCondition.getSecondaryEntityName() == null) {
            secondaryEntityNameVariableText.setText("Not Exist");
        }
        else {
            secondaryEntityNameVariableText.setText(dtoMultipleCondition.getSecondaryEntityName());
        }
    }
}
