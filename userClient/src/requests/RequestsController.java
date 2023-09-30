package requests;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RequestsController {

    @FXML
    private TableColumn<?, Integer> endedCol;

    @FXML
    private Button executeButton;

    @FXML
    private TableView<?> requestsTable;

    @FXML
    private TableColumn<?, Integer> runningCol;

    @FXML
    private TableColumn<?, Integer> runsCol;

    @FXML
    private Spinner<Integer> secondsSpinner;

    @FXML
    private CheckBox secondsTermination;

    @FXML
    private TableColumn<?, Integer> serialNumberCol;

    @FXML
    private Spinner<Integer> simulationCounterSpinner;

    @FXML
    private TableColumn<?, String> statusCol;

    @FXML
    private Button submitButton;

    @FXML
    private Spinner<Integer> ticksSpinner;

    @FXML
    private CheckBox ticksTermination;

    @FXML
    private CheckBox userTermination;

    @FXML
    private ComboBox<String> worldComboBox;

    @FXML
    private TableColumn<?, String> worldNameCol;

    @FXML
    private CheckBox secondsTicksTermination;

    public void initialize(){
        executeButton.setDisable(true);
        submitButton.setDisable(true);
        secondsTermination.setVisible(false);
        secondsSpinner.setVisible(false);
        ticksSpinner.setVisible(false);
        ticksTermination.setVisible(false);
    }

    @FXML
    void executeButtonClicked(ActionEvent event) {

    }

    @FXML
    void submitButtonClicked(ActionEvent event) {

    }

    @FXML
    void secondsTerminationClicked(ActionEvent event) {
        secondsSpinner.setEditable(secondsTermination.isSelected());
    }

    @FXML
    void ticksTerminationClicked(ActionEvent event) {
        ticksSpinner.setEditable(ticksTermination.isSelected());
    }

    @FXML
    void secondsTicksTerminationClicked(ActionEvent event) {
        secondsTermination.setVisible(secondsTicksTermination.isSelected());
        secondsSpinner.setVisible(secondsTicksTermination.isSelected());
        ticksSpinner.setVisible(secondsTicksTermination.isSelected());
        ticksTermination.setVisible(secondsTicksTermination.isSelected());
        userTermination.setSelected(!secondsTicksTermination.isSelected());
        secondsSpinner.setEditable(!secondsTicksTermination.isSelected());
        ticksSpinner.setEditable(!secondsTicksTermination.isSelected());
    }

    @FXML
    void userTerminationClicked(ActionEvent event) {
        if(userTermination.isSelected()) {
            secondsTermination.setVisible(false);
            secondsSpinner.setVisible(false);
            ticksSpinner.setVisible(false);
            ticksTermination.setVisible(false);
            secondsTicksTermination.setSelected(false);
        }
    }
}

