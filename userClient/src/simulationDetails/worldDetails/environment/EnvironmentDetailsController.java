package simulationDetails.worldDetails.environment;

import details.DtoEnvironmentInfo;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class EnvironmentDetailsController {

    @FXML
    private TableView<DtoEnvironmentInfo> environmentTableView;

    @FXML
    private TableColumn<DtoEnvironmentInfo, String> nameCol;

    @FXML
    private TableColumn<DtoEnvironmentInfo, Double> rangeFromCol;

    @FXML
    private TableColumn<DtoEnvironmentInfo, Double> rangeToCol;

    @FXML
    private TableColumn<DtoEnvironmentInfo, String> typeCol;

    public void initialize(){
        nameCol.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(cellData.getValue().getName());
            return property;
        });
        rangeFromCol.setCellValueFactory(cellData -> {
            SimpleDoubleProperty property = new SimpleDoubleProperty();
            property.setValue(cellData.getValue().getFrom());
            return property.asObject();
        });
        typeCol.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(cellData.getValue().getType());
            return property;
        });
        rangeToCol.setCellValueFactory(cellData -> {
            SimpleDoubleProperty property = new SimpleDoubleProperty();
            property.setValue(cellData.getValue().getTo());
            return property.asObject();
        });
    }

    public void setTable(List<DtoEnvironmentInfo> dtoEnvironmentInfoList) {
        Platform.runLater(() -> {
            ObservableList<DtoEnvironmentInfo> data = FXCollections.observableArrayList(dtoEnvironmentInfoList);
            if (data.isEmpty()) {
                Label emptyLabel = new Label("No environments :(");
                environmentTableView.setPlaceholder(emptyLabel);
            } else {
                environmentTableView.setItems(data);
            }
        });
    }
}
