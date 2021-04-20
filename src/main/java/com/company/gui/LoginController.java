package com.company.gui;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;



public class LoginController {

    private GUI gui;
    @FXML private TextField emailPanel;
    @FXML private PasswordField passwordPanel;

    //Login button pressed
    public void loginAccount(MouseEvent mouseEvent) {
        System.out.println("Email: " + emailPanel.getText());
        System.out.println("Password: " + passwordPanel.getText());
        gui.setScene("/Forside.fxml");
    }

    //Get gui object so scene can be changed
    public void loadData(GUI g)  {
        gui = g;
    }
}
