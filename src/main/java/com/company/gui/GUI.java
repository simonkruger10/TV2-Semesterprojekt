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
            URL fxml = GUI.class.getResource("/ProductionsOverview.fxml");
            FXMLLoader loader = new FXMLLoader(fxml);
            Parent root = loader.load();

            //Load data (GUI object) into controller object
            GuiController controller = loader.getController();
            controller.loadGui(this);

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
            URL fxml = GUI.class.getResource(file);
            FXMLLoader loader = new FXMLLoader(fxml);
            Parent root = loader.load();

            //Load data (GUI object) into controller object
            GuiController controller = loader.getController();
            controller.loadGui(this);

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