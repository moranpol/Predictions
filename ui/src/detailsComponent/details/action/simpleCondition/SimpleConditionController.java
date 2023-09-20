package detailsComponent.details.action.simpleCondition;

import details.DtoAction.DtoSimpleCondition;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class SimpleConditionController {
    @FXML
    private GridPane gridPane;

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

    private DtoSimpleCondition dtoSimpleCondition;

    public void setDtoSimpleCondition(DtoSimpleCondition dtoSimpleCondition) {
        this.dtoSimpleCondition = dtoSimpleCondition;
    }

    public void updateData(){
        operatorVariableText.setText(dtoSimpleCondition.getOperator());
        propertyVariableText.setText(dtoSimpleCondition.getProperty());
        valueVariableText.setText(dtoSimpleCondition.getValue());
        mainEntityNameVariableText.setText(dtoSimpleCondition.getMainEntityName());
        nameVariableText.setText(dtoSimpleCondition.getName());

        if(dtoSimpleCondition.getSecondaryEntityName() == null) {
            secondaryEntityNameVariableText.setText("Not Exist");
        }
        else {
            secondaryEntityNameVariableText.setText(dtoSimpleCondition.getSecondaryEntityName());
        }
    }
}
