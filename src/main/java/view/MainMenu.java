package view;

import controller.LoginMenuController;
import controller.MainMenuController;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import model.App;

import java.util.Objects;
import java.util.Scanner;


public class MainMenu extends Application {
    @FXML
    Button start;
    @FXML
    Button profile;
//    MainMenuController controller = new MainMenuController(this);

    @Override
    public void start(Stage stage) throws Exception {
        stage.setFullScreen(true);
        App.setStage(stage);
        // Load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(LoginMenu.class.getResource("/mainMenu.fxml"));
        Pane pane = fxmlLoader.load();
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/witcherGG.mp4")).toExternalForm();
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.statusProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == MediaPlayer.Status.READY) {
                // The media is ready to be played
                mediaPlayer.play();
            }
        });

        // Add the video to a StackPane
        StackPane root = new StackPane();
        root.getChildren().add(mediaView);
        root.getChildren().add(pane);
        Scene scene = new Scene(root);
        try {
            String cssPath = Objects.requireNonNull(LoginMenu.class.getResource("/styles/style.css")).toExternalForm();
            root.getStylesheets().add(cssPath); // Adding the CSS file
        } catch (NullPointerException t) {
            System.out.println("CSS file not found.");
        }
        // Ensure the video fits the stage
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            mediaView.setFitWidth(newVal.doubleValue());
        });
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            mediaView.setFitHeight(newVal.doubleValue());
        });

        start = (Button) scene.lookup("#start");
        profile = (Button) scene.lookup("#profile");

        start.setOnMouseClicked(mouseEvent -> {
            toGame(stage);
        });
        profile.setOnMouseClicked(mouseEvent -> {
            toProfile(stage);
        });

        mediaView.setFitWidth(stage.getWidth());
        mediaView.setFitHeight(stage.getHeight());
        mediaView.setPreserveRatio(false);
        stage.setResizable(false);
        stage.setFullScreen(true);

        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public void toProfile(Stage stage) {
        ProfileMenu profileMenu = new ProfileMenu();
        try {
            profileMenu.start(App.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        stage.setFullScreen(true);
    }

    public void toGame(Stage stage) {
        PreGameMenu preGameMenu = new PreGameMenu();
        try {
            preGameMenu.start(App.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        stage.setFullScreen(true);
    }
}
