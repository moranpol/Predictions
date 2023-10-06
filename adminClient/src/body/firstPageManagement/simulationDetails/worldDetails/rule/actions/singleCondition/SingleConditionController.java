package body.firstPageManagement.simulationDetails.worldDetails.rule.actions.singleCondition;

import details.DtoAction.DtoAction;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class SingleConditionController {

    @FXML
    private Text mainEntityNameVariableText;

    @FXML
    private Text nameVariableText;

    @FXML
    private Text operatorVariableText;

    @FXML
    private Text propertyVariableText;

    @FXML
    private Text secondaryEntityNameVariableText;

    @FXML
    private Text valueVariableText;

    private DtoAction dtoAction;

    public void setDtoAction(DtoAction dtoAction) {
        this.dtoAction = dtoAction;
    }

    public void updateData(){
        Platform.runLater(() -> {
            operatorVariableText.setText(dtoAction.getDtoSimpleCondition().getOperator());
            propertyVariableText.setText(dtoAction.getDtoSimpleCondition().getProperty());
            valueVariableText.setText(dtoAction.getDtoSimpleCondition().getValue());
            mainEntityNameVariableText.setText(dtoAction.getMainEntityName());
            nameVariableText.setText(dtoAction.getName());

            if (dtoAction.getSecondaryEntityName() == null) {
                secondaryEntityNameVariableText.setText("Not Exist");
            } else {
                secondaryEntityNameVariableText.setText(dtoAction.getSecondaryEntityName());
            }
        });
    }
}
