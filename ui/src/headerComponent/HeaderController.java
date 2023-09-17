package headerComponent;

import header.DtoSimulationQueue;
import javafx.application.Platform;
import javafx.scene.control.Label;
import pageComponent.PageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import header.DtoXmlPath;

import java.io.File;

public class HeaderController {

    private PageController pageController;

    @FXML
    private Button loadFileButton;

    @FXML
    private Button detailsButton;

    @FXML
    private Button newExecutionButton;

    @FXML
    private Pane queueManagement;

    @FXML
    private Button resultsButton;

    @FXML
    private Button okButton;

    @FXML
    private TextField textLoadedFile;

    @FXML
    private Label endedCounter;

    @FXML
    private Label queueCounter;

    @FXML
    private Label runningCounter;

    @FXML
    private Label errorLabel;

    public void setter(PageController pageController){
        setPageController(pageController);
        okButton.setVisible(false);
        errorLabel.setVisible(false);
    }

    private void setThread(){
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    DtoSimulationQueue dtoSimulationQueue = pageController.getDtoSimulationQueue();
                    Platform.runLater( () -> {
                        endedCounter.setText(dtoSimulationQueue.getEndedCounter().toString());
                        queueCounter.setText(dtoSimulationQueue.getQueueCounter().toString());
                        runningCounter.setText(dtoSimulationQueue.getRunningCounter().toString());
                    });

                    Thread.sleep(200);
                } catch (InterruptedException ignore) {
                }
            }
        });

        thread.start();
    }

    private void setPageController(PageController pageController) {
        this.pageController = pageController;
    }

    public void setResultsButtonDisable(){
        resultsButton.setDisable(false);
    }

    @FXML
    void detailsButtonClicked(ActionEvent event) {
        pageController.loadDetailsComponent();
    }

    @FXML
    void okButtonClicked(ActionEvent event){
        loadFileButton.setDisable(false);
        errorLabel.setVisible(false);
        okButton.setVisible(false);
        detailsButton.setVisible(true);
        newExecutionButton.setVisible(true);
        resultsButton.setVisible(true);
    }

    @FXML
    void loadFileButtonClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if(selectedFile != null) {
            DtoXmlPath xmlFullPathDTO = new DtoXmlPath(selectedFile.getAbsolutePath());
            try {
                pageController.getLogicManager().ReadXmlFile(xmlFullPathDTO);
                textLoadedFile.setText(selectedFile.getAbsolutePath());
                pageController.clearPaneBody();
                detailsButton.setDisable(false);
                newExecutionButton.setDisable(false);
                resultsButton.setDisable(true);
                setThread();

                String fileName = selectedFile.getName();
                textLoadedFile.setText(fileName);
            } catch (Exception e) {
                errorLabel.setVisible(true);
                errorLabel.setText("Error: " + e.getMessage());
                okButton.setVisible(true);
                loadFileButton.setDisable(true);
                detailsButton.setVisible(false);
                newExecutionButton.setVisible(false);
                resultsButton.setVisible(false);
            }
        }
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
