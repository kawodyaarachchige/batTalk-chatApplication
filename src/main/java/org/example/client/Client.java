package org.example.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.Getter;
import org.example.controller.ChatFormController;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable, Serializable {
    @Getter
    private final String name;

    @Getter
    private byte[] imgUser;

    private final Socket socket;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    private ChatFormController chatFormController;

    public Client(String name, byte[] imgUser) throws IOException {
        this.name = name;
        if(imgUser==null) {

        }else{
            this.imgUser = imgUser;
        }
        this.socket = new Socket("localhost", 1200);
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(name);
        dataOutputStream.flush();
        try {
            loadScene();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadScene() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/chat_form.fxml"));
        Parent parent = fxmlLoader.load();
        chatFormController = fxmlLoader.getController();
        chatFormController.setClient(this);
        chatFormController.lblUserName.setText(name);
        chatFormController.imgUser.setImage(new Image(new ByteArrayInputStream(imgUser)));
        stage.setResizable(false);
        stage.setScene(new Scene(parent));
        stage.setTitle(name+" 's Chat");
        stage.show();

        stage.setOnCloseRequest(event -> {
            try{
                dataInputStream.close();
                dataOutputStream.close();
                socket.close();
            }catch (IOException e){
                System.out.println(e);
            }
        });
    }


    @Override
    public void run() {
        //Listen for messages from the Server
        String message = "";
        while(!message.equals("exit")){
            try{
                message = dataInputStream.readUTF();
                if(message.equals("*image*")){
                    recieveImage();
                }else{
                    chatFormController.writeMessage(message);
                }
            }catch (IOException e){
                try {
                    socket.close();
                }catch (IOException ex){
                    System.out.println(ex);
                }
            }
        }
    }

    public void sendImage(byte[] bytes) throws IOException {
        dataOutputStream.writeUTF("*image*");
        dataOutputStream.writeInt(bytes.length);
        dataOutputStream.write(bytes);
        dataOutputStream.flush();
    }

    public void sendMessage(String message) throws IOException {
        dataOutputStream.writeUTF(message);
        dataOutputStream.flush();
    }

    private void recieveImage() throws IOException {
        String message = dataInputStream.readUTF();
        int size = dataInputStream.readInt();
        byte[] bytes = new byte[size];
        dataInputStream.readFully(bytes);
        System.out.println(name+"- Image Recieved from "+ message);
        chatFormController.setImage(bytes,message);
    }
}


