package body.firstPageManagement.simulationDetails.worldDetails.rule.actions.decrease;

import details.DtoAction.DtoAction;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class DecreaseController {
    @FXML
    private Text byVariableText;

    @FXML
    private Text propertyVariableText;

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
            byVariableText.setText(dtoAction.getDtoDecrease().getBy());
            propertyVariableText.setText(dtoAction.getDtoDecrease().getProperty());
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
