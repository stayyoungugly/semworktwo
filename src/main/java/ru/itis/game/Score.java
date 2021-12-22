package ru.itis.game;

import javafx.scene.text.Text;

public class Score extends Text {
    public Score(int player, String text) {
        super(text);
        if(player == 1) {
            setLayoutX(30);
            setLayoutY(30);
        } else {
            setLayoutX(500);
            setLayoutY(30);
        }
    }
}
