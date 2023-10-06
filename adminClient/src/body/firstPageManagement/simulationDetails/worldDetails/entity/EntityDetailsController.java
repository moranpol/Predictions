package body.firstPageManagement.simulationDetails.worldDetails.entity;

import details.DtoEntityInfo;
import details.DtoProperty;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.util.List;

public class EntityDetailsController {

    @FXML
    private TableColumn<DtoProperty, Double> fromCol;

    @FXML
    private TableColumn<DtoProperty, String> nameCol;

    @FXML
    private Text nameVariableText;

    @FXML
    private TableView<DtoProperty> propertiesTableView;

    @FXML
    private TableColumn<DtoProperty, Boolean> randomCol;

    @FXML
    private TableColumn<DtoProperty, Double> toCol;

    @FXML
    private TableColumn<DtoProperty, String> typeCol;

    private DtoEntityInfo dtoEntityInfo;

    public void initialize(){
        nameCol.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(cellData.getValue().getName());
            return property;
        });
        fromCol.setCellValueFactory(cellData -> {
            SimpleDoubleProperty property = new SimpleDoubleProperty();
            property.setValue(cellData.getValue().getFrom());
            return property.asObject();
        });
        typeCol.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(cellData.getValue().getType());
            return property;
        });
        toCol.setCellValueFactory(cellData -> {
            SimpleDoubleProperty property = new SimpleDoubleProperty();
            property.setValue(cellData.getValue().getTo());
            return property.asObject();
        });
        randomCol.setCellValueFactory(cellData -> {
            SimpleBooleanProperty property = new SimpleBooleanProperty();
            property.setValue(cellData.getValue().isRandomInitialized());
            return property.asObject();
        });
    }

    public void setDtoEntityInfo(DtoEntityInfo dtoEntityInfo) {
        this.dtoEntityInfo = dtoEntityInfo;
    }

    public void updateData(){
        Platform.runLater(() -> {
            nameVariableText.setText(dtoEntityInfo.getName());
            setTable(dtoEntityInfo.getPropertyDefinitions());
        });
    }

    private void setTable(List<DtoProperty> dtoEnvironmentInfoList){
        ObservableList<DtoProperty> data = FXCollections.observableArrayList(dtoEnvironmentInfoList);
        if(data.isEmpty()){
            Label emptyLabel = new Label("No properties :(");
            propertiesTableView.setPlaceholder(emptyLabel);
        } else {
            propertiesTableView.setItems(data);
        }
    }
}
