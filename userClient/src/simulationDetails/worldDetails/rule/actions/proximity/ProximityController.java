package simulationDetails.worldDetails.rule.actions.proximity;

import details.DtoAction.DtoAction;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ProximityController {
    @FXML
    private VBox vBox;

    @FXML
    private Text actionAmountVariableText;

    @FXML
    private Text depthVariableText;

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

    public void updateData(){
        Platform.runLater(() -> {
            actionAmountVariableText.setText(dtoAction.getDtoProximity().getActionAmount().toString());
            depthVariableText.setText(dtoAction.getDtoProximity().getDepth());
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
