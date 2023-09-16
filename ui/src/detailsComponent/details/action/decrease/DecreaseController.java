package detailsComponent.details.action.decrease;
import details.DtoAction.DtoDecrease;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class DecreaseController {

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

    private DtoDecrease dtoDecrease;

    public void setDtoDecrease(DtoDecrease dtoDecrease) {
        this.dtoDecrease = dtoDecrease;
    }

    public void updateData(){
        ByVariableText.setText(dtoDecrease.getBy());
        PropertyVariableText.setText(dtoDecrease.getProperty());
        mainEntityNameVariableText.setText(dtoDecrease.getMainEntityName());
        nameVariableText.setText(dtoDecrease.getName());

        if(dtoDecrease.getSecondaryEntityName() == null) {
            secondaryEntityNameVariableText.setText("Not Exist");
        }
        else {
            secondaryEntityNameVariableText.setText(dtoDecrease.getSecondaryEntityName());
        }

    }
}
