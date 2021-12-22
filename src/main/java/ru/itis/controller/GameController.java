package ru.itis.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import ru.itis.application.JavaFXApplication;
import ru.itis.engine.Client;
import ru.itis.engine.Server;
import ru.itis.engine.World;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    Pane pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    public void startServer() {
        Server server = new Server();
    }

    public void startClient() {
        Client client = new Client();
    }
/*
    private void startCommunicateWithClient(Socket socket) {


        Thread listenClient = new Thread(() -> {
            try {
                InputStream inputStream = socket.getInputStream();
                double lasttime = System.currentTimeMillis() / 1e3;

                while(true) {
                    double time = System.currentTimeMillis() / 1e3;
                    double deltatime = time - lasttime;
                    if(deltatime < 1.0 / maximumFPS) continue;
                    lasttime = System.currentTimeMillis() / 1e3;

                    int i;
                    while((i = inputStream.read()) != -1) {
                        System.out.println("Client say:" + i);
                    }
                }

            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }

        });
        Thread sayToClient = new Thread(() -> {
            try {
                OutputStream outputStream = socket.getOutputStream();
                double lasttime = System.currentTimeMillis() / 1e3;
                int i = 0;

                while(true) {
                    double time = System.currentTimeMillis() / 1e3;
                    double deltatime = time - lasttime;
                    if(deltatime < 1.0 / maximumFPS) continue;
                    lasttime = System.currentTimeMillis() / 1e3;


                    outputStream.write(i);
                    i++;
                }

            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }

        });

        listenClient.start();
        sayToClient.start();
    }

    private void startCommunicateWithServer(Socket socket) {
        Thread listenServer = new Thread(() -> {
            try {
                InputStream inputStream = socket.getInputStream();
                double lasttime = System.currentTimeMillis() / 1e3;

                while(true) {
                    double time = System.currentTimeMillis() / 1e3;
                    double deltatime = time - lasttime;
                    if(deltatime < 1.0 / maximumFPS) continue;
                    lasttime = System.currentTimeMillis() / 1e3;

                    int i;
                    while((i = inputStream.read()) != -1) {
                        System.out.println("Server say:" + i);
                    }
                }

            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }

        });
        Thread sayToServer = new Thread(() -> {
            try {
                OutputStream outputStream = socket.getOutputStream();
                double lasttime = System.currentTimeMillis() / 1e3;
                int i = 255;

                while(true) {
                    double time = System.currentTimeMillis() / 1e3;
                    double deltatime = time - lasttime;
                    if(deltatime < 1.0 / maximumFPS) continue;
                    lasttime = System.currentTimeMillis() / 1e3;


                    outputStream.write(i);
                    i--;
                }

            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }

        });

        listenServer.start();
        sayToServer.start();
    }

 */
}
