package resultsComponent.executionResults.resultsTypes.consistency;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ConsistencyController {

    @FXML
    private Label averageValue;

    @FXML
    private Label infoLabel;

    public void setter(String info, Float value){
        setInfoLabel(info);
        setAverageValue(value);
    }

    private void setAverageValue(Float value) {
        averageValue.setText(value.toString());
    }

    private void setInfoLabel(String info){
        infoLabel.setText(info);
    }
}