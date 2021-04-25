package com.company.gui;

import com.company.common.IAccount;
import com.company.data.AccountEntity;
import com.company.domain.AccountDTO;
import com.company.domain.AccountManagement;
import com.company.domain.IAccountManagement;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.security.NoSuchAlgorithmException;


public class LoginController extends GuiController{

    @FXML private TextField emailPanel;
    @FXML private PasswordField passwordPanel;

    IAccountManagement aMgt = new AccountManagement();

    //Login button pressed
    public void loginAccount(MouseEvent mouseEvent) {
        String email = emailPanel.getText();
        String password = passwordPanel.getText();

        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        /*
        try {
            aMgt.login(email, password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        */

        if(password.equals("1") && email.equals("1"))    {
            super.gui.setScene("/Homepage.fxml");
        }   else    {
            System.out.println("Wrong password and/or email (its 1 & 1");
        }

    }

}
