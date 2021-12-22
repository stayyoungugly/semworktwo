package ru.itis.game;


import javafx.scene.image.Image;
import lombok.Getter;
import ru.itis.Main;
import ru.itis.application.JavaFXApplication;
import ru.itis.engine.Entity;
import ru.itis.engine.World;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Apple extends Entity {
    Image image_r = new Image(Main.class.getResourceAsStream("/images/apple_r.png"));
    Image image_g = new Image(Main.class.getResourceAsStream("/images/apple_g.png"));
    int color;//1green 2red
    float speed;
    World world;

    public Apple(int id, int color, double x, float speed) {
        super(id);
        this.color = color;
        this.speed = speed;
        world = JavaFXApplication.getInstance().getWorld();
        imageProperty().set(color == 2 ? image_r: image_g);
        setFitHeight(30);
        setFitWidth(30);
        setLayoutX(x);
        setLayoutY(50);
    }

    @Override
    public void update(float deltaTime) {
        if(getLayoutY() > 700) return;
        setLayoutY(getLayoutY() + speed * deltaTime);

        List<Entity> entities = new ArrayList<>(world.getAllEntities());
        for(Entity entity: entities) {
            if(getBoundsInParent().intersects(entity.getBoundsInParent()) && entity.id != this.id) {
                world.removeEntity(this);
                if(JavaFXApplication.getInstance().getWorld().isServer()) {
                    if(((Player) entity).isCurrentPlayer) {
                        if(color == 1) world.setServerPlayer1Score(world.getServerPlayer1Score() + 1);
                        else world.setServerPlayer1Score(world.getServerPlayer1Score() - 1);
                    } else {
                        if(color == 1) world.setClientPlayer2Score(world.getClientPlayer2Score() - 1);
                        else world.setClientPlayer2Score(world.getClientPlayer2Score() + 1);
                    }
                } else {
                    if(((Player) entity).isCurrentPlayer) {
                        if(color == 1) world.setClientPlayer2Score(world.getClientPlayer2Score() - 1);
                        else world.setClientPlayer2Score(world.getClientPlayer2Score() + 1);
                    } else {
                        if(color == 1) world.setServerPlayer1Score(world.getServerPlayer1Score() + 1);
                        else world.setServerPlayer1Score(world.getServerPlayer1Score() - 1);
                    }
                }
                world.updateScore();
            }
        }
    }
}
