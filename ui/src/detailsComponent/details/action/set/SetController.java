package detailsComponent.details.action.set;

import details.DtoAction.DtoSet;
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

    private DtoSet dtoSet;

    public void setDtoSet(DtoSet dtoSet) {
        this.dtoSet = dtoSet;
    }

    public void updateData(){
        mainEntityNameVariableText.setText(dtoSet.getMainEntityName());
        nameVariableText.setText(dtoSet.getName());
        propertyVariableText.setText(dtoSet.getProperty());
        valueVariableText.setText(dtoSet.getValue());

        if(dtoSet.getSecondaryEntityName() == null) {
            secondaryEntityNameVariableText.setText("Not Exist");
        }
        else {
            secondaryEntityNameVariableText.setText(dtoSet.getSecondaryEntityName());
        }
    }
}
