package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import pageComponent.PageController;

import java.net.URL;

public class PredictionApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Predictions");

        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("/pageComponent/Page.fxml");
        fxmlLoader.setLocation(url);
        ScrollPane scrollPane = fxmlLoader.load(url.openStream());
        PageController pageController = fxmlLoader.getController();
        pageController.setPrimaryStage(primaryStage);
        Scene scene = new Scene(scrollPane, 1005, 715);
        scene.getStylesheets().add("/pageComponent/style/defaultPage.css");
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(400);
        primaryStage.setMaxHeight(755);
        primaryStage.setMaxWidth(1020);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
