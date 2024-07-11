package view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.App;

import java.util.Objects;

public class TvMenu extends Application {
    @FXML
    Button back;

    @Override
    public void start(Stage stage) throws Exception {
        App.setStage(stage);
        // Load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(EliminationCup.class.getResource("/tvMenu.fxml"));
        Pane pane = fxmlLoader.load();
        Image logo = new Image(getClass().getResourceAsStream("/pics/logo.png"));
        // Set the logo image as the window icon
        stage.getIcons().add(logo);
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/tv.mp4")).toExternalForm();
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
//        Pane pane = new Pane();
        root.getChildren().add(mediaView);
        root.getChildren().add(pane);
        Scene scene = new Scene(root);
        // Ensure the video fits the stage
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            mediaView.setFitWidth(newVal.doubleValue());
        });
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            mediaView.setFitHeight(newVal.doubleValue());
        });
        try {
            String cssPath = Objects.requireNonNull(LoginMenu.class.getResource("/styles/mainMenu.css")).toExternalForm();
            root.getStylesheets().add(cssPath); // Adding the CSS file
        } catch (NullPointerException t) {
            System.out.println("CSS file not found.");
        }
        back = (Button) scene.lookup("#back");
        back.setOnMouseClicked(event -> {
            MainMenu mainMenu = new MainMenu();
            try {
                mainMenu.start(App.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            App.getStage().setFullScreen(true);
        });
        back.setOnMouseEntered(e -> animateButton(back, 1.1));
        back.setOnMouseExited(e -> animateButton(back, 1.0));

        mediaView.setFitWidth(stage.getWidth());
        mediaView.setFitHeight(stage.getHeight());
        mediaView.setPreserveRatio(false);
        stage.setFullScreen(true);
        stage.setResizable(false);

        stage.setTitle("Elimination cup Menu");
        stage.setScene(scene);
        stage.show();
    }

    private void animateButton(Button button, double scale) {
        Timeline timeline = new Timeline();
        KeyValue kvX = new KeyValue(button.scaleXProperty(), scale);
        KeyValue kvY = new KeyValue(button.scaleYProperty(), scale);
        KeyFrame kf = new KeyFrame(Duration.millis(300), kvX, kvY);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
}

