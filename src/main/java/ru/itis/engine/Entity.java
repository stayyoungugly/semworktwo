package ru.itis.engine;

import javafx.scene.image.ImageView;
import lombok.Data;
import lombok.Getter;


public abstract class Entity extends ImageView {
    public int id;

    public Entity(int id) {
        this.id = id;
    }

    public abstract void update(float deltaTime);

}
