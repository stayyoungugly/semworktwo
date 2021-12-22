package ru.itis.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.itis.application.JavaFXApplication;
import ru.itis.input.Input;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    Pane pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void startServerButtonTapped(ActionEvent actionEvent) throws IOException {
        GameController gameController = startGame();
        gameController.startServer();
    }

    @FXML
    private void connectButtonTapped(ActionEvent actionEvent) throws IOException {
        GameController gameController = startGame();
        gameController.startClient();
    }

    private GameController startGame() throws IOException {
        String fxmlFile = "/fxml/Game.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        fxmlLoader.load();
        GameController gameController = fxmlLoader.getController();

        Scene scene = new Scene(gameController.pane);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(scene);

        JavaFXApplication.getInstance().getWorld().setInput(new Input());
        JavaFXApplication.getInstance().getWorld().setPane(gameController.pane);
        JavaFXApplication.getInstance().getWorld().getInput().listenScene(scene);

        return gameController;
    }
}
