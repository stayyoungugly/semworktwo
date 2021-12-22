package ru.itis.game;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import lombok.Getter;
import ru.itis.Main;
import ru.itis.application.JavaFXApplication;
import ru.itis.engine.Entity;
import ru.itis.input.Input;

@Getter
public class Floor extends Entity {
    Input input = JavaFXApplication.getInstance().getWorld().getInput();
    String imagePath = "/images/floor.png";

    public Floor(int id) {
        super(id);
        Image image = new Image(Main.class.getResourceAsStream(imagePath));
        imageProperty().set(image);
        setFitHeight(80);
        setFitWidth(540);
        setLayoutX(30);
        setLayoutY(280);
    }

    @Override
    public void update(float deltaTime) {
        if(input.isKeyPressed(KeyCode.W)) System.out.println("fine");
    }
}
