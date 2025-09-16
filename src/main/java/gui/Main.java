package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lebron.Lebron;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private final Lebron lebron = new Lebron("./data/userData.csv");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            scene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("LeBron Chat");
            fxmlLoader.<MainWindow>getController().setDuke(lebron);
            stage.show();
            fxmlLoader.<MainWindow>getController().startUp();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
