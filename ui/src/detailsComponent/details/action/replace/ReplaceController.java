package detailsComponent.details.action.replace;
import details.DtoAction.DtoReplace;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ReplaceController {

    @FXML
    private Text mainEntityNameVariableText;

    @FXML
    private Text nameVariableText;

    @FXML
    private Text secondaryEntityNameVariableText;

    private DtoReplace dtoReplace;

    public void setDtoReplace(DtoReplace dtoReplace) {
        this.dtoReplace = dtoReplace;
    }

    public void updateData(){
        mainEntityNameVariableText.setText(dtoReplace.getMainEntityName());
        nameVariableText.setText(dtoReplace.getName());

        if(dtoReplace.getSecondaryEntityName() == null) {
            secondaryEntityNameVariableText.setText("Not Exist");
        }
        else {
            secondaryEntityNameVariableText.setText(dtoReplace.getSecondaryEntityName());
        }
    }
}