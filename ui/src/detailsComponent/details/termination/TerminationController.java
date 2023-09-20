package detailsComponent.details.termination;
import details.DtoTermination;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class TerminationController {

    @FXML
    private Text humanVariableText;

    @FXML
    private Text secondsVariableText;

    @FXML
    private Text ticksVariableText;

    private DtoTermination dtoTermination;

    @FXML
    private GridPane gridPane;

    public void setDtoTermination(DtoTermination dtoTermination) {
        this.dtoTermination = dtoTermination;
    }

    public void updateData(){
        humanVariableText.setText(dtoTermination.getHuman().toString());

        if(dtoTermination.getSeconds() == null){
            secondsVariableText.setText("Not Exist");
        }
        else{
            secondsVariableText.setText(dtoTermination.getSeconds().toString());
        }

        if(dtoTermination.getTicks() == null){
            ticksVariableText.setText("Not Exist");
        }
        else{
            ticksVariableText.setText(dtoTermination.getTicks().toString());
        }
    }
}

