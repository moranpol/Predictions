package detailsComponent.details.entity.propertyDetails;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import details.DtoProperty;

public class PropertyDetailsController {

    @FXML
    private Text nameVariableText;

    @FXML
    private Text randomInitializedVariableText;

    @FXML
    private Text rangeVariableText;

    @FXML
    private Text typeVariableText;

    public void updateProperty(DtoProperty dtoProperty) {
        nameVariableText.setText(dtoProperty.getName());
        typeVariableText.setText(dtoProperty.getType());

        if(dtoProperty.isRandomInitialized()) {
            randomInitializedVariableText.setText("Yes");
        }
        else {
            randomInitializedVariableText.setText("No");
        }

        if(dtoProperty.getRange() != null) {
            rangeVariableText.setText("From: " + dtoProperty.getRange().getFrom()  + "\nTo:"+ dtoProperty.getRange().getTo());
        }
        else{
            rangeVariableText.setText("Not Exist");
        }

    }
}
