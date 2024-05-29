package model;

import javafx.stage.Stage;
import javafx.scene.media.MediaPlayer;

import java.util.Random;


public class App {
    private static Stage stage = new Stage();

    private static MediaPlayer mediaplayer;

    private static final Random random = new Random();

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        App.stage = stage;
    }

    public static MediaPlayer getMediaplayer() {
        return mediaplayer;
    }

    public static void setMediaplayer(MediaPlayer mediaplayer) {
        App.mediaplayer = mediaplayer;
    }

    public static Random getRandom() {
        return random;
    }

}
