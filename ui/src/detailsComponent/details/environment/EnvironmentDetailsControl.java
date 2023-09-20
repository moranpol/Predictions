package detailsComponent.details.environment;
import details.DtoEnvironmentInfo;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class EnvironmentDetailsControl {
    @FXML
    public Text typeVariableText;

    @FXML
    public Text rangeVariableText;

    @FXML
    private Text nameVariableText;

    private DtoEnvironmentInfo dtoEnvironmentInfo;

    @FXML
    private GridPane gridPane;

    public void setDtoEnvironmentInfo(DtoEnvironmentInfo dtoEnvironmentInfo) {
        this.dtoEnvironmentInfo = dtoEnvironmentInfo;
    }

    public void updateData(){
        nameVariableText.setText(dtoEnvironmentInfo.getName());
        typeVariableText.setText(dtoEnvironmentInfo.getType());

        if(dtoEnvironmentInfo.getRange() != null) {
            rangeVariableText.setText("From: " + dtoEnvironmentInfo.getRange().getFrom()  + "\nTo:"+ dtoEnvironmentInfo.getRange().getTo());
        }
        else{
            rangeVariableText.setText("Not Exist");
        }
    }
}

