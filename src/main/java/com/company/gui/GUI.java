package com.company.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class GUI extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        //Create and show window
        primaryStage.setTitle("Credit Management System");
        //if (icon != null) {
        //    primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(icon)));
        //}
        primaryStage.setMinWidth(1024);
        primaryStage.setMinHeight(630);
        primaryStage.setScene(new Scene(new HomepageController()));
        //primaryStage.setOnCloseRequest(event -> this.quitGame());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setScene(String s) {
    }
}
