package headerComponent;

import bodyComponent.BodyController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import manager.LogicManager;
import menuChoice1.DtoXmlPath;

import java.io.File;

public class HeaderController {

    private LogicManager logicManager;
    private BodyController bodyController;

    public void setBodyController(BodyController bodyController) {
        this.bodyController = bodyController;
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
        bodyController.loadDetailsComponent();
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
            logicManager.ReadXmlFile(xmlFullPathDTO);
            textLoadedFile.setText(selectedFile.getAbsolutePath());
            //setButtonsDisabled(false);
        }catch (Exception ignore) {}

        detailsButton.setDisable(false);
        newExecutionButton.setDisable(false);
        resultsButton.setDisable(false);

        String fileName = selectedFile.getName();
        textLoadedFile.setText(fileName);


    }

    @FXML
    void newExectionButtonClicked(ActionEvent event) {

    }

    @FXML
    void resultsButtonClicked(ActionEvent event) {

    }

}
