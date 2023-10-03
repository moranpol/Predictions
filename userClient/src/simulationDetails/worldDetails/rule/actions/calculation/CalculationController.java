package simulationDetails.worldDetails.rule.actions.calculation;

import details.DtoAction.DtoAction;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CalculationController {
    @FXML
    private VBox vBox;

    @FXML
    private Text arg1VariableText;

    @FXML
    private Text arg2VariableText;

    @FXML
    private Text arithmeticVariableText;

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
            arg1VariableText.setText(dtoAction.getDtoCalculation().getArg1());
            arg2VariableText.setText(dtoAction.getDtoCalculation().getArg2());
            arithmeticVariableText.setText(dtoAction.getDtoCalculation().getArithmetic());
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
