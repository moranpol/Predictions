package detailsComponent.details.action.calculation;
import details.DtoAction.DtoCalculation;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class CalculationController {

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

    private DtoCalculation dtoCalculation;

    public void setDtoCalculation(DtoCalculation dtoCalculation) {
        this.dtoCalculation = dtoCalculation;
    }

    public void updateData() {
        arg1VariableText.setText(dtoCalculation.getArg1());
        arg2VariableText.setText(dtoCalculation.getArg2());
        arithmeticVariableText.setText(dtoCalculation.getArithmetic());
        mainEntityNameVariableText.setText(dtoCalculation.getMainEntityName());
        nameVariableText.setText(dtoCalculation.getName());

        if(dtoCalculation.getSecondaryEntityName() == null) {
            secondaryEntityNameVariableText.setText("Not Exist");
        }
        else {
            secondaryEntityNameVariableText.setText(dtoCalculation.getSecondaryEntityName());
        }
    }

}
