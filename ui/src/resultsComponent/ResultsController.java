package resultsComponent;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import pageComponent.PageController;

public class ResultsController {

    @FXML
    private Pane executionDetailsPane;

    @FXML
    private Pane executionListPane;

    @FXML
    private Pane executionResultPane;

    private PageController pageController;

    public void setPageController(PageController pageController) {
        this.pageController = pageController;
    }
}
