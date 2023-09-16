package detailsComponent.details.action.proximity;
import details.DtoAction.DtoProximity;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ProximityController {

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

    private DtoProximity dtoProximity;

    public void setDtoProximity(DtoProximity dtoProximity) {
        this.dtoProximity = dtoProximity;
    }

    public void updateData(){
        actionAmountVariableText.setText(dtoProximity.getActionAmount().toString());
        depthVariableText.setText(dtoProximity.getDepth());
        mainEntityNameVariableText.setText(dtoProximity.getMainEntityName());
        nameVariableText.setText(dtoProximity.getName());

        if(dtoProximity.getSecondaryEntityName() == null) {
            secondaryEntityNameVariableText.setText("Not Exist");
        }
        else {
            secondaryEntityNameVariableText.setText(dtoProximity.getSecondaryEntityName());
        }
    }
}
