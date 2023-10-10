package results.executionResults.resultsTypes.consistency;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ConsistencyController {

    @FXML
    private Label averageValue;

    @FXML
    private Label infoLabel;

    public void setter(String info, Float value){
        setInfoLabel(info);
        if(value == null){
            setAverageValue("All entities are dead :(");
        } else {
            setAverageValue(value.toString());
        }
    }

    private void setAverageValue(String value) {
        Platform.runLater(() -> averageValue.setText(value.toString()));
    }

    private void setInfoLabel(String info){
        Platform.runLater(() -> infoLabel.setText(info));
    }
}