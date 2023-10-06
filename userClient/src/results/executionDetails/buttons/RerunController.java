package results.executionDetails.buttons;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class RerunController {

    @FXML
    private Button rerunButton;

    private Integer requestId;

    private Integer simulationId;

    @FXML
    void rerunButtonClick(ActionEvent event) {
        //todo
    }

    public void setter(Integer requestId, Integer simulationId){
        this.requestId = requestId;
        this.simulationId = simulationId;
    }
}
