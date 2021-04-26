package com.company.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        primaryStage.setScene(new Scene(new HomepageController()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setScene(String s) {
    }
}
