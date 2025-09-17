package gui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

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

    private Lebron lebron;

    @Override
    public void start(Stage stage) {

        String jarDir = new File(System.getProperty("user.dir")).getAbsolutePath();
        File dataDir = new File(jarDir, "data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        String dataFile = new File(dataDir, "userData.csv").getAbsolutePath();


        this.lebron = new Lebron(dataFile);

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
            stage.setOnCloseRequest(event -> {
                lebron.getStorage().storeTasks();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
