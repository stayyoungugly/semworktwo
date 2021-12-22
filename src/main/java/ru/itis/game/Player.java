package ru.itis.game;

import ru.itis.engine.Entity;

public abstract class Player extends Entity{
    boolean isCurrentPlayer;

    public Player(int id, boolean isCurrentPlayer) {
        super(id);
        this.isCurrentPlayer = isCurrentPlayer;
    }

    public abstract void move(double x, double direction);
}
