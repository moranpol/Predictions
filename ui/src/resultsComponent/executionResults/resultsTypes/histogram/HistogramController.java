package resultsComponent.executionResults.resultsTypes.histogram;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import results.simulationEnded.DtoPropertyHistogram;
import results.simulationEnded.DtoSimulationEndedEntity;
import results.simulations.DtoSimulationEntity;

import java.util.Map;

public class HistogramController {

    @FXML
    private TableView<DtoPropertyHistogram> tableView;

    @FXML
    private TableColumn<DtoPropertyHistogram, Integer> amountCol;

    @FXML
    private TableColumn<DtoPropertyHistogram, String> valueCol;

    private Map<String, DtoPropertyHistogram> dtoPropertyHistogram;

    public void initialize(){
        valueCol.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(cellData.getValue().getValueOfProperty());
            return property;
        });
        amountCol.setCellValueFactory(cellData -> {
            SimpleIntegerProperty property = new SimpleIntegerProperty();
            property.setValue(cellData.getValue().getAmount());
            return property.asObject();
        });
    }

    public void setDtoPropertyHistogram(Map<String, DtoPropertyHistogram> dtoPropertyHistogram) {
        this.dtoPropertyHistogram = dtoPropertyHistogram;
    }

    public void setTable(){
        ObservableList<DtoPropertyHistogram> data = FXCollections.observableArrayList(dtoPropertyHistogram.values());
        if(data.isEmpty()){
            Label emptyLabel = new Label("All of the entities are dead :(");
            tableView.setPlaceholder(emptyLabel);
        } else {
            tableView.setItems(data);
        }
    }
}
