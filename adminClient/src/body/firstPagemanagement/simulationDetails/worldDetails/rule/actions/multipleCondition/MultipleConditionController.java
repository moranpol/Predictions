package body.firstPagemanagement.simulationDetails.worldDetails.rule.actions.multipleCondition;

import details.DtoAction.DtoAction;
import javafx.application.Platform;
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

    private DtoAction dtoAction;

    public void setDtoAction(DtoAction dtoAction) {
        this.dtoAction = dtoAction;
    }

    public void updateData() {
        Platform.runLater(() -> {
            mainEntityNameVariableText.setText(dtoAction.getMainEntityName());
            nameVariableText.setText(dtoAction.getName());
            conditionsAmountVariableText.setText(dtoAction.getDtoMultipleCondition().getConditionsAmount().toString());
            logicVariableText.setText(dtoAction.getDtoMultipleCondition().getLogic());

            if (dtoAction.getSecondaryEntityName() == null) {
                secondaryEntityNameVariableText.setText("Not Exist");
            } else {
                secondaryEntityNameVariableText.setText(dtoAction.getSecondaryEntityName());
            }
        });
    }
}
