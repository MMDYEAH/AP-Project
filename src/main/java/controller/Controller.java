package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class Controller {
    private HashMap<String, Scene> scenes;
    private Stage stage;

    public void setScenes(HashMap<String, Scene> scenes) {
        this.scenes = scenes;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void switchToScene(String sceneName) {
        Scene scene = scenes.get(sceneName);
        stage.setScene(scene);
        stage.show();
    }
}