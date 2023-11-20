package adminHeader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import mainPage.MainPageController;

public class HeaderController {

    @FXML
    private Button allocationsButton;

    @FXML
    private Button managementButton;

    private MainPageController mainPageController;

    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    @FXML
    public void allocationsButtonClicked(ActionEvent event) {
        mainPageController.loadAllocationBodyController();
    }

    @FXML
    void managementButtonClicked(ActionEvent event) {
        mainPageController.loadManagementBodyController();
    }

    public void setter(MainPageController mainPageController) {
        setMainPageController(mainPageController);
    }
}

