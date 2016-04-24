package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("resources/main_layout.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Алгоритм Виженера");
        stage.show();
        stage.sizeToScene();
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
