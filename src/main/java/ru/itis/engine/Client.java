package ru.itis.engine;

import ru.itis.application.JavaFXApplication;
import ru.itis.controller.GameController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class Client {
    private Socket socket;
    private World world;

    private final int maximumFps = 60;
    private double lastTime;

    public Client() {
        try {
            socket = new Socket("127.0.0.1", 16431);
            world = JavaFXApplication.getInstance().getWorld();
            world.startGame(false);
            startCommunicateWithServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startCommunicateWithServer() {
        lastTime = System.currentTimeMillis() / 1e3;
        Thread thread = new Thread(() -> {
            while(true) {
                double time = System.currentTimeMillis() / 1e3;
                double deltaTime = time - lastTime;
                if(deltaTime < 1.0 / maximumFps) {
                    continue;
                }
                lastTime = time;

                try {
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

                    List<Event> events = world.getEventsToSend();
                    for (Event event : events) {
                        Event.writeEvent(event, dataOutputStream);
                    }
                    dataOutputStream.writeInt(Event.END);

                    Event event;
                    while ((event = Event.readEvent(dataInputStream)) != null) {
                        world.proccesEventFrom(event);
                    }
                } catch (IOException e) {
                    throw new IllegalArgumentException(e);
                }
            }

        });

        thread.start();
    }
}
