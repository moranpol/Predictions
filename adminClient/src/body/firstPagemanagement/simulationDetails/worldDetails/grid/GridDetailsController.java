package body.firstPagemanagement.simulationDetails.worldDetails.grid;

import details.DtoGridInfo;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class GridDetailsController {
    @FXML
    private Text colsLabel;

    @FXML
    private Text rowsLabel;

    private DtoGridInfo dtoGridInfo;

    public void setter(DtoGridInfo dtoGridInfo){
        setDtoGridInfo(dtoGridInfo);
        updateData();
    }

    private void setDtoGridInfo(DtoGridInfo dtoGrid) {
        this.dtoGridInfo = dtoGrid;
    }

    public void updateData(){
        Platform.runLater(() -> {
            colsLabel.setText(dtoGridInfo.getCols().toString());
            rowsLabel.setText(dtoGridInfo.getRows().toString());
        });
    }
}
