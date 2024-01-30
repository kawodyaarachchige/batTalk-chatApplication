package org.example.controller;

import animatefx.animation.SlideInUp;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.Mail;
import org.example.client.Client;
import org.example.dto.UserDTO;
import org.example.model.UserModel;
import org.example.server.Server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;


public class LoginFormController {

    public Button backpane;
    public TextField txtUserName;

    public static String name;
    public Label forgetpwdPane;
    public ImageView imgUser;
    public PasswordField txtPassword;
    private File file;
    public static int oneTimePassword;
    public static String tempUserName;
    public void initialize() throws IOException {
        stratServer();
    }

    private void stratServer() throws IOException {
        Server server = Server.getServerSocket();
        Thread thread = new Thread(server);
        //we created a thread for the server.. it means we called the run method of runnable interface
        //then server will run on a separate thread while program is running on main thread
        thread.start();
    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException, SQLException {
        loadUserDetails();
        txtUserName.clear();
        txtPassword.clear();
        imgUser.setImage(null);
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
            Parent load = FXMLLoader.load(getClass().getResource("/view/welcome_form.fxml"));
            Scene scene1 = new Scene(load);
            Stage stage1 = (Stage) backpane.getScene().getWindow();
            stage1.setScene(scene1);
            stage1.setTitle("Login Form");
            stage1.centerOnScreen();
    }

    public void txtForgetPwdOnAction(MouseEvent mouseEvent) {
        System.out.println("User request a password reset..");
        if(!txtUserName.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Do you want to Change Your Password ?");
            alert.setContentText("Send OTP To Your Email");

            ButtonType buttonTypeYes = new ButtonType("OK");
            ButtonType buttonTypeNo = new ButtonType("Cancel");

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            alert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeYes) {
                    try {
                        oneTimePassword = generateOTP();
                        Mail mail = new Mail();
                        mail.setMsg("Hello," + "\n\n\tAn OTP Request Detected at :  " + LocalDateTime.now() + " \n\n\tOTP : " + oneTimePassword + " \n\nThank You,\n" +
                                "~BatTalk~");
                        mail.setTo(txtUserName.getText());
                        tempUserName=txtUserName.getText();
                        mail.setSubject("OTP Verification");

                        Thread thread = new Thread(mail);
                        thread.start();

                        Parent load = FXMLLoader.load(getClass().getResource("/view/forgetpassword_form.fxml"));
                        Scene scene1 = new Scene(load);
                        Stage stage1 = (Stage) forgetpwdPane.getScene().getWindow();
                        stage1.setScene(scene1);
                        stage1.setTitle("Change Password Form");
                        stage1.centerOnScreen();

                        new SlideInUp(load).play();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else if (response == buttonTypeNo) {
                    System.out.println("User Canceled the Operation");
                }
            });
        }else{
            new Alert(Alert.AlertType.ERROR, "Enter your email first..").show();
        }
    }
    public static int generateOTP(){
        Random random = new Random();
        int password = random.nextInt(9000000) + 1000000;
        System.out.println(password);
        return password;
    }

    public void imgCameraOnAction(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg","*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);
        file = fileChooser.showOpenDialog(txtUserName.getScene().getWindow());
        if(file != null){
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                imgUser.setImage(new Image(fileInputStream));
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }
    private void loadUserDetails() throws IOException, SQLException {
        UserDTO user = UserModel.getUser(txtUserName.getText(), txtPassword.getText());
        if(user != null){
            Client client = new Client(user.getName(),user.getProfilePic());
            Thread thread = new Thread(client);
            thread.start();

            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/chat_form.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.setTitle(user.getName() + "'s Chat");
            stage.setAlwaysOnTop(true);
        }else{
            new Alert(Alert.AlertType.ERROR, "Invalid User Name or Password Check again !").show();
        }
    }

}
