package body.firstPagemanagement;
import http.HttpClientUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class managementBodyControll {

    @FXML
    private Button ContinueButton;

    @FXML
    private Button loadFileButton;

    @FXML
    private Pane paneSimulationsDetails;

    @FXML
    private Pane paneThreadPool;

    @FXML
    private Button setThreadsCountButton;

    @FXML
    private TextField textFieldFilePath;

    @FXML
    void continueButtonClicked(ActionEvent event) {
        textFieldFilePath.setText("File Path");
        loadFileButton.setVisible(true);
        ContinueButton.setVisible(false);
    }

    @FXML
    void loadFileButtonClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        /*
        if(selectedFile != null) {
            DtoXmlPath xmlFullPathDTO = new DtoXmlPath(selectedFile.getAbsolutePath());
            try {
                //pageController.getLogicManager().ReadXmlFile(xmlFullPathDTO);
                textFieldFilePath.setText(selectedFile.getAbsolutePath());
                //pageController.clearPaneBody();

                //setThread();

                //String fileName = selectedFile.getName();
                //textLoadedFile.setText(fileName);
            } catch (Exception e) {
                textFieldFilePath.setText("Error: " + e.getMessage());
                ContinueButton.setVisible(true);
                loadFileButton.setVisible(false);

            }
        }
         */

        RequestBody body =
                new MultipartBody.Builder()
                        .addFormDataPart("xmlFile", selectedFile.getAbsolutePath(), RequestBody.create(selectedFile, MediaType.parse("application/octet-stream")))
                        .build();

        String finalUrl = HttpUrl
                .parse("http://localhost:8080/predictions/loadXml")
                .newBuilder()
                .build()
                .toString();

        HttpClientUtil.runAsyncPost(finalUrl, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    textFieldFilePath.setText(selectedFile.getAbsolutePath());
                    //setThread();

                    //String fileName = selectedFile.getName();
                    //textLoadedFile.setText(fileName);
                    //------------------------------------------------------



                    //thirdPageController.setAmountOfSimulations(0);
                    //thirdPageController.setAmountOfSimulationsEnded(0);
                    //setAmountOfCompletedSimulation("0");
                    // setAmountOfSimulationsInQueue("0");
                    //setAmountOfSimulationsInProgress("0");
                    //thirdPageController.setSimulationTask(null);
                    //thirdPageController.setFinishSimulationTask(null);
                    //thirdPageController.resetPauseAndResume();
                    //setAllPagesDetails();
                    //isFileLoaded.set(true);
                    //isDetailsClicked.set(false);
                    //xmlPathProperty.set(f.getTitle());
                }
                    else{
                        //textFieldFilePath.setText("Error: " + response.body().string());
                        ContinueButton.setVisible(true);
                        loadFileButton.setVisible(false);

                    String responseBody = response.body().string(); // Get the HTML response body as a string

                    // Attempt to extract the error message from the HTML response
                    String errorMessage = extractErrorMessage(responseBody);
                    if (errorMessage != null) {
                        textFieldFilePath.setText("Error Message: " + errorMessage);
                    } else {
                        textFieldFilePath.setText("Error Message: Error message not found in the HTML");
                    }

                    }

            }

        });


    }
    private String extractErrorMessage(String htmlContent) {
        // Implement a method to extract the error message from the HTML content
        // You can use regular expressions, string manipulation, or other parsing techniques
        // based on the specific structure of the HTML response.
        // This example uses a simple regular expression to find an error message inside a <p> tag.

        Pattern pattern = Pattern.compile("<p><b>Message</b> (.+?)</p>");
        Matcher matcher = pattern.matcher(htmlContent);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null; // Return null if the error message is not found
    }

    @FXML
    void setThreadsCountButtonClicked(ActionEvent event) {

    }

}

