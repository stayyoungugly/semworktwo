package ru.itis.engine;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lombok.Data;
import ru.itis.game.*;
import ru.itis.input.Input;

import java.util.*;

@Data
public class World {
    private Pane pane;
    private Input input;
    private double lastFrameTime;
    private int estimateFPS;
    private List<Entity> entities;
    boolean isServer;
    private List<Event> eventsToSend;
    long appleInterval = 2;
    float appleSpeed = 80f;
    private long fps;
    int serverPlayer1Score = 0;
    int clientPlayer2Score = 0;
    Score scorePlayer1;
    Score scorePlayer2;


    public void startGame(boolean isServer) {
        this.isServer = isServer;

        entities = new ArrayList<>();
        eventsToSend = new ArrayList<>();

        startAnimationTimer();

        createStartEntities();
        startApples();
    }

    private void startAnimationTimer() {
        lastFrameTime = System.currentTimeMillis() / 1.0E3;
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                double time = System.currentTimeMillis() / 1.0E3;
                float deltaTime = (float) (time - lastFrameTime);
                estimateFPS = (int) (1f / deltaTime);
                lastFrameTime = time;
                updateEntities(deltaTime);
            }
        };
        animationTimer.start();
    }

    private void updateEntities(float deltaTime) {
        if (System.currentTimeMillis() - fps > 60) {
            for (Entity entity : entities) {
                entity.update(deltaTime);
            }
            fps = System.currentTimeMillis();
        }
    }

    private void createStartEntities() {
        ServerPlayer1 serverPlayer1 = new ServerPlayer1( 1, isServer);
        addEntity(serverPlayer1);

        ClientPlayer2 clientPlayer2 = new ClientPlayer2( 2, !isServer);
        addEntity(clientPlayer2);

        Floor floor = new Floor( 3);
        addEntity(floor);

        scorePlayer1 = new Score(1, "Player 1 score: " + serverPlayer1Score);
        scorePlayer2 = new Score(2, "Player 2 score: " + clientPlayer2Score);
        Platform.runLater(() -> {
            pane.getChildren().add(scorePlayer1);
            pane.getChildren().add(scorePlayer2);
        });
    }

    public void addEntity(Entity entity) {
        entities.add(entity);

        Platform.runLater(() -> pane.getChildren().add(entity));
    }

    public void proccesEventFrom(Event event) {
        if(event.getType() == 1) entities.stream()
                .filter(e -> e.id == event.getObjectId())
                .map(Player.class::cast)
                .findAny()
                .orElseThrow(IllegalArgumentException::new)
                .move(event.getData()[0], event.getData()[1]);

        if(event.getType() == 2) {
            double[] data = event.getData();
            Apple apple = new Apple(event.getObjectId(), (int) data[1], data[0], (float) data[2]);
            addEntity(apple);
        }
    }

    public void addEventToSend(Event event) {
        eventsToSend.add(event);
    }

    public List<Event> getEventsToSend() {
        List<Event> e = new ArrayList<>(eventsToSend);
        eventsToSend.clear();
        return e;
    }

    private void startApples() {
        if(!isServer) return;

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                double[] positions = new double[] {60, 140, 225, 310, 390, 470};
                double[] data = new double[10];
                data[0]  = positions[new Random().nextInt(positions.length)];
                data[1] = Math.random() < 0.5 ? 2: 1;
                data[2] = appleSpeed;
                Apple apple = new Apple(entities.size() + 1, (int) data[1], data[0], appleSpeed);
                addEntity(apple);
                appleSpeed += 5;
                appleInterval -= 0.1f;

                Event event = new Event(2, apple.id, data);
                addEventToSend(event);
            }
        };
        timer.scheduleAtFixedRate(timerTask, 5 * 1000, appleInterval * 1000);
    }

    public void removeEntity(Entity entity) {
        Platform.runLater(() -> {
            entities.remove(entity);
            pane.getChildren().remove(entity);
        });
    }

    public List<Entity> getAllEntities(){
        return new ArrayList<>(entities);
    }

    public void updateScore() {
        scorePlayer1.setText("Player 1 score: " + serverPlayer1Score);
        scorePlayer2.setText("Player 2 score: " + clientPlayer2Score);
    }
}
