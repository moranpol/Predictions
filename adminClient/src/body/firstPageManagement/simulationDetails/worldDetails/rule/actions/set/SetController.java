package body.firstPageManagement.simulationDetails.worldDetails.rule.actions.set;

import details.DtoAction.DtoAction;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SetController {
    @FXML
    private VBox vBox;

    @FXML
    private Text mainEntityNameVariableText;

    @FXML
    private Text nameVariableText;

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
            mainEntityNameVariableText.setText(dtoAction.getMainEntityName());
            nameVariableText.setText(dtoAction.getName());
            propertyVariableText.setText(dtoAction.getDtoSet().getProperty());
            valueVariableText.setText(dtoAction.getDtoSet().getValue());

            if (dtoAction.getSecondaryEntityName() == null) {
                secondaryEntityNameVariableText.setText("Not Exist");
            } else {
                secondaryEntityNameVariableText.setText(dtoAction.getSecondaryEntityName());
            }
        });
    }
}
