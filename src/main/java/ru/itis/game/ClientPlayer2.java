package ru.itis.game;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import lombok.Getter;
import ru.itis.Main;
import ru.itis.application.JavaFXApplication;
import ru.itis.engine.Event;
import ru.itis.input.Input;

public class ClientPlayer2 extends Player {
    Input input = JavaFXApplication.getInstance().getWorld().getInput();
    Image image_r = new Image(Main.class.getResourceAsStream("/images/player2_r.png"));
    Image image_l = new Image(Main.class.getResourceAsStream("/images/player2_l.png"));
    private final float speed = 300.f;
    private double direction;

    public ClientPlayer2(int id, boolean isCurrentPlayer) {
        super(id, isCurrentPlayer);
        imageProperty().set(image_r);
        setFitHeight(40);
        setFitWidth(40);
        setLayoutX(500);
        setLayoutY(255);
    }

    @Override
    public void update(float deltaTime) {
        if(!isCurrentPlayer) return;
        boolean isMoved = false;
        if(input.isKeyPressed(KeyCode.D)) {
            if(input.isKeyPressed(KeyCode.A)) return;
            if(getLayoutX() >= 525) return;
            if(direction != 2) {
                direction = 2;
                imageProperty().set(image_r);
            }
            setLayoutX(getLayoutX() + speed * deltaTime);
            isMoved = true;
        }

        if(input.isKeyPressed(KeyCode.A)) {
            if(input.isKeyPressed(KeyCode.D)) return;
            if(getLayoutX() <= 33) return;
            if(direction != 1) {
                direction = 1;
                imageProperty().set(image_l);
            }
            setLayoutX(getLayoutX() - speed * deltaTime);
            isMoved = true;
        }

        if(isMoved) {
            double[] doubles = new double[10];
            doubles[0] = getLayoutX();
            doubles[1] = direction;
            Event event = new Event(1, id, doubles);
            JavaFXApplication.getInstance().getWorld().addEventToSend(event);
        }
    }

    @Override
    public void move(double x, double direction) {
        if(direction == 1 && getLayoutX() > 33) {
            if(this.direction != direction) {
                this.direction = direction;
                imageProperty().set(image_l);
            }
            setLayoutX(x);
        }

        if(direction == 2 && getLayoutX() < 525) {
            if(this.direction != direction) {
                this.direction = direction;
                imageProperty().set(image_r);
            }
            setLayoutX(x);
        }
    }
}
