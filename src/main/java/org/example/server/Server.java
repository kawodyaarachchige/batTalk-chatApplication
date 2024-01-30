package org.example.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private static Server server;
    private final ServerSocket serverSocket;

    //Only one server could be running at a time , so we use singleton pattern

    private Server() throws IOException {
        serverSocket = new ServerSocket(1200);
        System.out.println("Server Started Successfully");
    }

    public static Server getServerSocket() throws IOException {
        return server == null ? server = new Server() : server;
    }

    //We need to start the server , so we override the run method of runnable interface
    @Override
    public void run() {
        // We need to accept the client requests from the server
        while (!serverSocket.isClosed()){
            try {
                //Accept the client
                Socket socket = serverSocket.accept();
                //Create a handler for the client
                ClientHandler clientHandler = new ClientHandler(socket);
                //Create a thread for the client and start the thread
                Thread thread = new Thread(clientHandler);
                thread.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}