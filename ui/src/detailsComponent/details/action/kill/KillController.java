package detailsComponent.details.action.kill;
import details.DtoAction.DtoKill;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class KillController {

    @FXML
    private Text mainEntityNameVariableText;

    @FXML
    private Text nameVariableText;

    @FXML
    private Text secondaryEntityNameVariableText;

    private DtoKill dtoKill;

    public void setDtoKill(DtoKill dtoKill) {
        this.dtoKill = dtoKill;
    }

    public void updateData(){
        nameVariableText.setText(dtoKill.getName());
        mainEntityNameVariableText.setText(dtoKill.getMainEntityName());

        if(dtoKill.getSecondaryEntityName() == null) {
            secondaryEntityNameVariableText.setText("Not Exist");
        }
        else {
            secondaryEntityNameVariableText.setText(dtoKill.getSecondaryEntityName());
        }
    }
}

