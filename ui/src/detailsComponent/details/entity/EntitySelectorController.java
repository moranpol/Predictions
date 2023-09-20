package detailsComponent.details.entity;

import details.DtoEntityInfo;
import detailsComponent.details.entity.propertyDetails.PropertyDetailsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

public class EntitySelectorController {
    @FXML
    public Pane paneProperty;

    @FXML
    private PropertyDetailsController propertyDetailsController;

    @FXML
    private Text nameVariableText;

    @FXML
    private ComboBox<String> propertyCBox;

    private DtoEntityInfo dtoEntityInfo;

    @FXML
    private GridPane gridPane;

    public void setDtoEntityInfo(DtoEntityInfo dtoEntityInfo) {
        this.dtoEntityInfo = dtoEntityInfo;
    }

    @FXML
    void propertyCBoxChoice(ActionEvent event) {
        if(propertyCBox.getValue() != null){
            loadPropertyDetails();
            propertyDetailsController.updateProperty(dtoEntityInfo.getPropertyDefinitions().get(propertyCBox.getValue()));
        }
    }

    private void loadPropertyDetails(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsComponent/details/entity/propertyDetails/PropertyDetails.fxml"));
            Parent propertyDetails = loader.load();
            paneProperty.getChildren().clear();
            paneProperty.getChildren().add(propertyDetails);

            propertyDetailsController = loader.getController();
        } catch (IOException ignored) {
        }
    }

    public void updateData(){
        nameVariableText.setText(dtoEntityInfo.getName());
        ObservableList<String> propertyNames = FXCollections.observableArrayList(dtoEntityInfo.getPropertyDefinitions().keySet());
        propertyCBox.setItems(propertyNames);
    }
}

