package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class PredictionApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Predictions");

        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/pageComponent/page.fxml")));
        Scene scene = new Scene(load, 1005, 705);
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(400);
        primaryStage.setMaxHeight(745);
        primaryStage.setMaxWidth(1020);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
