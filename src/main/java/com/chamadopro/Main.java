package com.chamadopro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/login.fxml")));
        Scene scene = new Scene(root);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/dashboard.css")).toExternalForm());

        stage.setScene(scene);
        stage.setTitle("ChamadoPro");

        stage.setWidth(1360);
        stage.setHeight(768);
        stage.setResizable(true);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
