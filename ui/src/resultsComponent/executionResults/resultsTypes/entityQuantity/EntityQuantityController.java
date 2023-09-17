package resultsComponent.executionResults.resultsTypes.entityQuantity;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import results.simulationEnded.DtoEntityQuantityGraph;
import results.simulationEnded.DtoSimulationEndedEntity;

public class EntityQuantityController {

    @FXML
    private LineChart<Integer, Integer> chart;

    @FXML
    private NumberAxis quantity;

    @FXML
    private NumberAxis ticks;

    private DtoSimulationEndedEntity dtoSimulationEndedEntity;

    public void setter(DtoSimulationEndedEntity dtoSimulationEndedEntity){
        setDtoSimulationEndedEntity(dtoSimulationEndedEntity);
        setGraph();
    }

    private void setDtoSimulationEndedEntity(DtoSimulationEndedEntity dtoSimulationEndedEntity) {
        this.dtoSimulationEndedEntity = dtoSimulationEndedEntity;
    }

    private void setGraph(){
        XYChart.Series<Integer, Integer> series = new XYChart.Series<>();
        for (DtoEntityQuantityGraph entityQuantity : dtoSimulationEndedEntity.getEntityQuantityGraph()) {
            series.getData().add(new XYChart.Data<>(entityQuantity.getTick(), entityQuantity.getEntityAmount()));
        }
        series.setName("Population");

        chart.getData().add(series);
    }
}
