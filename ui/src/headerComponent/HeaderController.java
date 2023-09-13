package headerComponent;

import pageComponent.PageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import menuChoice1.DtoXmlPath;

import java.io.File;

public class HeaderController {

    private PageController pageController;

    public void setPageController(PageController pageController) {
        this.pageController = pageController;
    }

    @FXML
    private Button LoadFileButton;

    @FXML
    private Button detailsButton;

    @FXML
    private Button newExecutionButton;

    @FXML
    private Pane queueManagement;

    @FXML
    private Button resultsButton;

    @FXML
    private TextField textLoadedFile;


    @FXML
    void detailsButtonClicked(ActionEvent event) {
        pageController.loadDetailsComponent();
    }

    @FXML
    void loadFileButtonClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        DtoXmlPath xmlFullPathDTO = new DtoXmlPath(selectedFile.getAbsolutePath());
        try {
            pageController.getLogicManager().ReadXmlFile(xmlFullPathDTO);
            textLoadedFile.setText(selectedFile.getAbsolutePath());
        }catch (Exception ignore) {}

        detailsButton.setDisable(false);
        newExecutionButton.setDisable(false);
        resultsButton.setDisable(false);

        String fileName = selectedFile.getName();
        textLoadedFile.setText(fileName);
    }

    @FXML
    void newExecutionButtonClicked(ActionEvent event) {
        pageController.loadNewExecutionComponent();
    }

    @FXML
    void resultsButtonClicked(ActionEvent event) {
        pageController.loadResultsComponent();
    }

}
