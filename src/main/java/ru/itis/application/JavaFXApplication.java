package ru.itis.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Data;
import ru.itis.controller.GameController;
import ru.itis.engine.World;
import ru.itis.input.Input;

@Data
public class JavaFXApplication extends Application {
    private static JavaFXApplication instance;
    private World world;

    public static JavaFXApplication getInstance() { return instance; }

    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;
        world = new World();

        String fxmlFile = "/fxml/Main.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent parent = fxmlLoader.load();

        Scene scene = new Scene(parent);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Game");
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(false);
        primaryStage.show();
    }

    public void start(String[] args) {
        launch(args);
    }
}
