package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import animatefx.animation.FadeIn;
import animatefx.animation.SlideInUp;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;

public class WelcomeFormController {


    public Button signUppane;
    public  Button loginPane;

    public void btnSignupOnAction(ActionEvent actionEvent) throws IOException {

        Parent load = FXMLLoader.load(this.getClass().getResource("/view/signup_form.fxml"));
        Scene scene1 = new Scene(load);
        Stage stage1 =  (Stage)signUppane.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.setTitle("Signup Form");
        stage1.centerOnScreen();
        stage1.show();

    }
    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));
        Scene scene1 = new Scene(load);
        Stage stage1 =  (Stage)loginPane.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.setTitle("Login Form");
        stage1.centerOnScreen();
        stage1.show();

    }

}
