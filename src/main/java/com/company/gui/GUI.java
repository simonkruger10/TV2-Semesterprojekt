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

        try {
            //Create loader and load fxml
            URL fxml = GUI.class.getResource("/Login.fxml");
            FXMLLoader loader = new FXMLLoader(fxml);
            Parent root = loader.load();

            //Load data (GUI object) into controller object
            LoginController controller = loader.<LoginController>getController();
            controller.loadData(this);

            //Create and show window
            primaryStage.setTitle("Credit Management System");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    //Set scene from fxml file
    public void setScene(String file)  {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(file));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
