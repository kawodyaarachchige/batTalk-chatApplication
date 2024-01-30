package org.example.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.client.Client;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

public class ChatFormController {

    public ImageView imgUser;
    public ScrollPane scrollPane;
    @FXML
    public VBox vBox;
    public TextField txtMsg;
    public AnchorPane emojiPane;
    private Client client;
    public Label lblUserName;

    public void initialize() {
        lblUserName.setText(LoginFormController.name);
    }

    public void setClient(Client client) throws IOException {
        this.client=client;
        String message=" joined the chat";
        appendText(message);
        client.sendMessage(message);

    }

    private void appendText(String message) {
        if (message.startsWith(" joined")) {
            HBox hBox = new HBox();
            hBox.setStyle("-fx-alignment: center;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 426;-fx-max-width: 426;-fx-padding: 10");
            Label messageLbl = new Label(message);
            messageLbl.setStyle("-fx-background-color:black;-fx-background-radius:15;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill:white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
            hBox.getChildren().add(messageLbl);
            vBox.getChildren().add(hBox);
        } else {
            HBox hBox = new HBox();
            hBox.setStyle("-fx-alignment: center-right;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 426;-fx-max-width: 426;-fx-padding: 10");
            Label messageLbl = new Label(message);
            messageLbl.setStyle("-fx-background-color:black;-fx-background-radius:15;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill:white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
            hBox.getChildren().add(messageLbl);
            vBox.getChildren().add(hBox);
        }
    }

    public void grinningFaceEmojiOnAction(MouseEvent mouseEvent) {
        txtMsg.appendText("\uD83D\uDE00");
        emojiPane.setVisible(false);
    }

    public void grinningSquintingOnAction(MouseEvent mouseEvent) {
        txtMsg.appendText("\uD83D\uDE06");
        emojiPane.setVisible(false);
    }

    public void smilingFaceWithOpenHandsOnAction(MouseEvent mouseEvent) {
        txtMsg.appendText("\uD83E\uDD17");
        emojiPane.setVisible(false);
    }

    public void grinningFaceWithSweatOnAction(MouseEvent mouseEvent) {
        txtMsg.appendText("\uD83D\uDE05");
        emojiPane.setVisible(false);
    }

    public void faceWithTearsOfJoyOnAction(MouseEvent mouseEvent) {
        txtMsg.appendText("\uD83D\uDE02");
        emojiPane.setVisible(false);
    }

    public void cryingFaceOnAction(MouseEvent mouseEvent) {
        txtMsg.appendText("\uD83D\uDE22");
        emojiPane.setVisible(false);
    }

    public void sunglassesFaceOnAction(MouseEvent mouseEvent) {
        txtMsg.appendText("\uD83D\uDE0E");
        emojiPane.setVisible(false);
    }

    public void smilingFaceWithHeartEyesOnAction(MouseEvent mouseEvent) {
        txtMsg.appendText("\uD83D\uDE0D");
        emojiPane.setVisible(false);
    }

    public void smilingFaceWithHornsOnAction(MouseEvent mouseEvent) {
        txtMsg.appendText("\uD83D\uDE08");
        emojiPane.setVisible(false);
    }

    public void thumbsUpOnAction(MouseEvent mouseEvent) {
        txtMsg.appendText("\uD83D\uDC4D");
        emojiPane.setVisible(false);
    }

    public void setImage(byte[] bytes,String sender){
        HBox hBox = new HBox();
        Label messageLbl = new Label(sender);
        messageLbl.setStyle("-fx-background-color:   #f2cb2d;-fx-background-radius:15;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill:black;-fx-wrap-text: true;-fx-alignment: center;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");

        hBox.setStyle("-fx-fill-height: true; -fx-min-height: 50; -fx-pref-width: 520; -fx-max-width: 520; -fx-padding: 10; " + (sender.equals(client.getName()) ? "-fx-alignment: center-right;" : "-fx-alignment: center-left;"));
        // Display the image in an ImageView or any other UI component
        Platform.runLater(() -> {
            ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(bytes)));
            imageView.setStyle("-fx-padding: 10px;");
            imageView.setFitHeight(180);
            imageView.setFitWidth(100);

            hBox.getChildren().addAll(messageLbl, imageView);
            vBox.getChildren().add(hBox);
        });
    }
    public void writeMessage(String message) {
        //print msg on other clients
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-left;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
        Label messageLbl = new Label(message);
        messageLbl.setStyle("-fx-background-color:   #f2cb2d;-fx-background-radius:15;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill:black;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
        hBox.getChildren().add(messageLbl);
        Platform.runLater(() -> vBox.getChildren().add(hBox));
    }

    public void btnEmojiSendOnAction(ActionEvent actionEvent) {
        emojiPane.setVisible(!emojiPane.isVisible());
    }

    public void btnImageSendOnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg","*.gif","*.webp");
        fileChooser.getExtensionFilters().add(imageFilter);
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                byte[] bytes = Files.readAllBytes(selectedFile.toPath());
                HBox hBox = new HBox();
                hBox.setStyle("-fx-fill-height: true; -fx-min-height: 50; -fx-pref-width: 520; -fx-max-width: 520; -fx-padding: 10; -fx-alignment: center-right;");

                // Display the image in an ImageView or any other UI component
                ImageView imageView = new ImageView(new Image(new FileInputStream(selectedFile)));
                imageView.setStyle("-fx-padding: 10px;");
                imageView.setFitHeight(180);
                imageView.setFitWidth(100);

                hBox.getChildren().addAll(imageView);
                vBox.getChildren().add(hBox);

                client.sendImage(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void txtMessageOnAction(ActionEvent actionEvent) {
        btnSendOnAction(actionEvent);
    }
    public void btnSendOnAction(ActionEvent actionEvent) {
        try{
            String message=txtMsg.getText();
            if(message!=null){
                appendText(message);
                client.sendMessage(message);
                txtMsg.clear();
            }else{
                ButtonType ok = new ButtonType("OK");
                ButtonType cancel = new ButtonType("Cancel");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Enter message,It's empty. Is it ok?", ok, cancel);
                alert.showAndWait();
                ButtonType result = alert.getResult();
                if(result.equals(ok)){
                    client.sendMessage(null);
                }
                txtMsg.clear();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
