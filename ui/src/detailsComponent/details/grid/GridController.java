package detailsComponent.details.grid;

import details.DtoAction.DtoGridInfo;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class GridController {

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
        colsLabel.setText(dtoGridInfo.getCols().toString());
        rowsLabel.setText(dtoGridInfo.getRows().toString());
    }
}