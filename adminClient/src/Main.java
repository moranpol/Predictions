import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


import java.net.URL;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Predictions");

        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("/mainPage/mainPage.fxml");
        fxmlLoader.setLocation(url);
        Parent scrollPane = fxmlLoader.load(url.openStream());
        //PageController pageController = fxmlLoader.getController();
        //pageController.setPrimaryStage(primaryStage);
        Scene scene = new Scene(scrollPane, 1010, 720);
        //scene.getStylesheets().add("/pageComponent/style/defaultPage.css");
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
