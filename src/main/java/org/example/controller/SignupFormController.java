package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.dto.UserDTO;
import org.example.model.UserModel;

import java.io.*;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class SignupFormController {
    public AnchorPane signPane;
    public TextField txtUserName;
    public Button backpane;
    public TextField txtEmail;
    public PasswordField txtPassword;
    public ImageView imgUser;
    public PasswordField txtRePassword;
    public File file;
    public byte[] imageData;

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
            Parent load = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
            Scene scene1 = new Scene(load);
            Stage stage1 = (Stage) backpane.getScene().getWindow();
            stage1.setScene(scene1);
            stage1.setTitle("Login Form");
            stage1.centerOnScreen();
    }
    public void btnSignUpOnAction(ActionEvent actionEvent) {
        if(!txtUserName.getText().isEmpty() && !txtEmail.getText().isEmpty() && !txtPassword.getText().isEmpty() && !txtRePassword.getText().isEmpty()){
            if(Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", txtEmail.getText())){
                if(Pattern.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", txtPassword.getText())) {
                    if (txtPassword.getText().equals(txtRePassword.getText())) {
                        UserDTO userDTO = new UserDTO( txtEmail.getText(),txtUserName.getText(),txtPassword.getText(), imageData);
                        try {
                            boolean isSaved = UserModel.saveUser(userDTO);
                            if(isSaved) {
                                new Alert(Alert.AlertType.INFORMATION, "Account Created,You Can now Login").show();
                                btnBackOnAction(actionEvent);
                            }else{
                                new Alert(Alert.AlertType.ERROR, "Not Saved").show();
                            }
                        } catch (SQLException | IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Password not matched").show();
                    }
                } else{
                    new Alert(Alert.AlertType.ERROR,"Weak Password, must contain at least 1 uppercase, 1 lowercase, 1 digit and 1 special character").show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR,"Invalid Email").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"All fields are required").show();
        }
    }

    public void imgCameraOnAction(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg","*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);
        file = fileChooser.showOpenDialog(txtUserName.getScene().getWindow());
        if(file != null){
            try {
                FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
                imageData = new byte[fileInputStream.available()];
                fileInputStream.read(imageData);
                imgUser.setImage(new Image(new ByteArrayInputStream(imageData)));
               /*FileInputStream fileInputStream = new FileInputStream(file);
               imgUser.setImage(new Image(fileInputStream));*/
            }catch (FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
