package detailsComponent.details.entity;
import details.DtoEntityInfo;
import detailsComponent.details.entity.propertyDetails.PropertyDetailsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class EntitySelectorController {
    @FXML
    public Pane PaneProperty;

    @FXML
    private GridPane propertyDetails;

    @FXML
    private PropertyDetailsController propertyDetailsController;

    @FXML
    private Text nameVariableText;

    @FXML
    private ComboBox<String> propertyCBox;

    private DtoEntityInfo dtoEntityInfo;

    public void setDtoEntityInfo(DtoEntityInfo dtoEntityInfo) {
        this.dtoEntityInfo = dtoEntityInfo;
    }

    @FXML
    void propertyCBoxChoice(ActionEvent event) {
        propertyDetailsController.updateProperty(dtoEntityInfo.getPropertyDefinitions().get(propertyCBox.getValue()));
    }

    public void updateData(){
        nameVariableText.setText(dtoEntityInfo.getName());
        ObservableList<String> propertyNames = FXCollections.observableArrayList(dtoEntityInfo.getPropertyDefinitions().keySet());
        propertyCBox.setItems(propertyNames);
    }

}

