package body.secondPage;
import alloction.DtoAlloction;
import details.DtoEnvironmentInfo;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class AlloctionController {

    @FXML
    private TableColumn<DtoAlloction, Integer> finishRow;

    @FXML
    private TableColumn<DtoAlloction, Integer> requestIdRow;

    @FXML
    private TableColumn<DtoAlloction, Integer> runningRow;

    @FXML
    private TableView<DtoAlloction> tableInfo;

    @FXML
    private TableColumn<DtoAlloction, String> termainationRow;

    @FXML
    private TableColumn<DtoAlloction, Integer> totalAmountRow;

    @FXML
    private TableColumn<DtoAlloction, String> userNameRow;

    @FXML
    private TableColumn<DtoAlloction, String> worldNameRow;

    @FXML
    private Button AproveButton;

    @FXML
    private Button RejectButton;

    @FXML
    void AproveButtonClicked(ActionEvent event) { //todo

    }

    @FXML
    void RejectButtonClicked(ActionEvent event) {

    }

    public void initialize(){
        requestIdRow.setCellValueFactory(cellData -> {
            SimpleIntegerProperty property = new SimpleIntegerProperty();
            property.setValue(cellData.getValue().getRequestId());
            return property.asObject();
        });

        totalAmountRow.setCellValueFactory(cellData -> {
            SimpleIntegerProperty property = new SimpleIntegerProperty();
            property.setValue(cellData.getValue().getTotalAmount());
            return property.asObject();
        });

        termainationRow.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(cellData.getValue().getTermaination());
            return property;
        });

        userNameRow.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(cellData.getValue().getUserName());
            return property;
        });

        worldNameRow.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(cellData.getValue().getWorldName());
            return property;
        });

    }

    public void setTable(List<DtoAlloction> dtoDtoAlloctionList) {  // todo with timer
        Platform.runLater(() -> {
            ObservableList<DtoAlloction> data = FXCollections.observableArrayList(dtoDtoAlloctionList);
            if (data.isEmpty()) {
                Label emptyLabel = new Label("No environments :(");
                tableInfo.setPlaceholder(emptyLabel);
            } else {
                tableInfo.setItems(data);
            }
        });
    }

}

