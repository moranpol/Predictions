package detailsComponent.details.entity;
import detailsComponent.details.propertyDetails.PropertyDetailsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class EntitySelectorController {
    @FXML
    private GridPane property;  //
    @FXML
    private PropertyDetailsController propertyDetailsController;//

    @FXML
    private Text nameVariableText;

    @FXML
    private Text populationVariableText;

    @FXML
    private ComboBox<?> propertyCbox;

    @FXML
    void propertyCboxChoice(ActionEvent event) {

    }

}

