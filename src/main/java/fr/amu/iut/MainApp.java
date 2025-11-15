package fr.amu.iut;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Bonjour le village des irréductibles !");

        StackPane root = new StackPane();
        root.getChildren().add(label);

        Scene scene = new Scene(root, 640, 480);

        primaryStage.setTitle("Projet Simulation (Gaulois & Lycanthropes)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}