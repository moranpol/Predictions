package detailsComponent.details.action.increase;
import details.DtoAction.DtoIncrease;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class IncreaseController {
    @FXML
    private VBox vBox;

    @FXML
    private Text ByVariableText;

    @FXML
    private Text PropertyVariableText;

    @FXML
    private Text mainEntityNameVariableText;

    @FXML
    private Text nameVariableText;

    @FXML
    private Text secondaryEntityNameVariableText;

    private DtoIncrease dtoIncrease;

    public void setDtoIncrease(DtoIncrease dtoIncrease) {
        this.dtoIncrease = dtoIncrease;
    }

    public void updateData(){
        ByVariableText.setText(dtoIncrease.getBy());
        PropertyVariableText.setText(dtoIncrease.getProperty());
        mainEntityNameVariableText.setText(dtoIncrease.getMainEntityName());
        nameVariableText.setText(dtoIncrease.getName());

        if(dtoIncrease.getSecondaryEntityName() == null) {
            secondaryEntityNameVariableText.setText("Not Exist");
        }
        else {
            secondaryEntityNameVariableText.setText(dtoIncrease.getSecondaryEntityName());
        }
    }
}
