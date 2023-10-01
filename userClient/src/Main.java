import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Predictions - Client");

        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("/mainPage/MainPage.fxml");
        fxmlLoader.setLocation(url);
        assert url != null;
        ScrollPane scrollPane = fxmlLoader.load(url.openStream());
        Scene scene = new Scene(scrollPane, 1005, 715);
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(400);
        primaryStage.setMaxHeight(755);
        primaryStage.setMaxWidth(1020);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}