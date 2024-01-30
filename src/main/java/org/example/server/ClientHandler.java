package org.example.server;
import org.example.controller.ChatFormController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

    public class ClientHandler implements Runnable{
        //3.1 create a list of clients, because we can have multiple clients.
        public static final List<ClientHandler> clientHandlersList = new ArrayList<>();
        //3.2 create a socket , so we can communicate with the client.
        public static ChatFormController chatFormController;
        private final Socket socket;
        private final DataInputStream dataInputStream;
        private final DataOutputStream dataOutputStream;

        private final String clientName;

        //3.3 create a constructor, so we can create a client handler.
        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            dataInputStream= new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            clientName = dataInputStream.readUTF();
            clientHandlersList.add(this);
        }

        //3.4 create a run method, so we can run the client handler.
        @Override
        public void run() {
            //4.1 receive messages from the client
            while(socket.isConnected()){
                try{
                    // Receive message, if the message is *image* we will receive an image
                    String message =dataInputStream.readUTF();
                    if(message.equals("*image*")){
                        receiveImage();
                    }else{
                        // else we will send the message to all clients
                        for(ClientHandler clientHandler:clientHandlersList){
                            if(!clientHandler.clientName.equals(clientName)){
                                clientHandler.sendMessage(clientName,message);     }
                        }
                    }
                }catch (IOException e){
                    clientHandlersList.remove(this);
                    break;
                }
            }

        }

        //4.2 send an image
        private void receiveImage() throws IOException {
            int size = dataInputStream.readInt();
            byte[] bytes= new byte[size];
            dataInputStream.readFully(bytes);
            for (ClientHandler clientHandler : clientHandlersList) {
                if (!clientHandler.clientName.equals(clientName)) {
                    clientHandler.sendImage(clientName, bytes);
                }
            }
        }
        private void sendImage(String clientName, byte[] bytes) throws IOException {
            dataOutputStream.writeUTF("*image*");
            dataOutputStream.writeUTF(clientName);
            dataOutputStream.writeInt(bytes.length);
            dataOutputStream.write(bytes);
            dataOutputStream.flush();
            //Sends a special message indicating an image (*image*), along with the sender's name, image size, and image bytes, to all clients.
        }

        private void sendMessage(String clientName, String message) throws IOException {
            dataOutputStream.writeUTF(clientName+" : "+message);
            dataOutputStream.flush();
        }
    }

