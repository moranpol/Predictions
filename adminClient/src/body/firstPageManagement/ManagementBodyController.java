package body.firstPageManagement;

import alert.AlertDialog;
import org.jetbrains.annotations.NotNull;
import refresher.ThreadInfoRefresher;
import header.DtoSimulationQueue;
import http.HttpClientUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import okhttp3.*;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Timer;

public class ManagementBodyController implements Closeable {

    @FXML
    private Button setThreadsCountButton;

    @FXML
    private Label endedCounter;

    @FXML
    private Button loadFileButton;

    @FXML
    private Label queueCounter;

    @FXML
    private Label runningCounter;

    @FXML
    private Spinner<Integer> spinnerThreadsNum;

    @FXML
    private TextField textFieldFilePath;

    private Timer timer;

    private ThreadInfoRefresher threadInfoRefresher;

    @FXML
    void setThreadsCountButtonClicked(ActionEvent event) {
        String finalUrl = HttpUrl
                .parse("http://localhost:8080/predictions/threadInfo")
                .newBuilder()
                .addEncodedQueryParameter("threadNumber", spinnerThreadsNum.getValue().toString())
                .build()
                .toString();

        RequestBody body = RequestBody.create(null, new byte[0]);

        HttpClientUtil.runAsyncPut(finalUrl, body, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                AlertDialog.showError(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if(!response.isSuccessful()) {
                    AlertDialog.showError(response.message());
                } else{
                    AlertDialog.showSuccess("Threads set successfully.");
                }
            }
        });
    }

    public void initialize(){
        setRange();
        threadInfoRefresher();
    }

    public void updateThreadInfo(DtoSimulationQueue dtoSimulationQueue){
        Platform.runLater(() -> {
            endedCounter.setText(dtoSimulationQueue.getEndedCounter().toString());
            queueCounter.setText(dtoSimulationQueue.getQueueCounter().toString());
            runningCounter.setText(dtoSimulationQueue.getRunningCounter().toString());
        });
    }

    public void threadInfoRefresher() {
        threadInfoRefresher = new ThreadInfoRefresher(this::updateThreadInfo);

        timer = new Timer();
        timer.schedule(threadInfoRefresher, 1, 1000);
    }

    private void setRange() {
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        spinnerThreadsNum.setValueFactory(spinnerValueFactory);
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

        RequestBody body = new MultipartBody.Builder()
                        .addFormDataPart("xmlFile", selectedFile.getAbsolutePath(), RequestBody.create(selectedFile, MediaType.parse("application/octet-stream")))
                        .build();

        String finalUrl = HttpUrl
                .parse("http://localhost:8080/predictions/loadXml")
                .newBuilder()
                .build()
                .toString();

        HttpClientUtil.runAsyncPost(finalUrl, body, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                AlertDialog.showError(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if(response.isSuccessful()) {
                    textFieldFilePath.setText(selectedFile.getAbsolutePath());
                    AlertDialog.showSuccess("File load successfully.");
                } else{
                    AlertDialog.showError(response.message());
                }
            }
        });
    }

    @Override
    public void close() {
        threadInfoRefresher.cancel();
        timer.cancel();
    }
}

