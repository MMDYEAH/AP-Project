package view;

import controller.LoginMenuController;
import controller.MainMenuController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.App;
import model.User;

import java.util.Objects;
import java.util.Scanner;


public class MainMenu extends Application {
    @FXML
    Button start;
    @FXML
    Button profile;
    @FXML
    Button pointChart;
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
        pointChart = (Button) scene.lookup(("#pointChart"));

        start.setOnMouseEntered(e -> animateButton(start, 1.1));
        start.setOnMouseExited(e -> animateButton(start, 1.0));

        profile.setOnMouseEntered(e -> animateButton(profile, 1.1));
        profile.setOnMouseExited(e -> animateButton(profile, 1.0));

        pointChart.setOnMouseEntered(e -> animateButton(pointChart, 1.1));
        pointChart.setOnMouseExited(e -> animateButton(pointChart, 1.0));

        start.setOnMouseClicked(mouseEvent -> {
            toGame(stage);
        });
        profile.setOnMouseClicked(mouseEvent -> {
            toProfile(stage);
        });
        pointChart.setOnMouseClicked(mouseEvent -> {
            pointChart();
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

    public void pointChart() {
        TilePane tilePane = new TilePane();
        Stage videoStage = new Stage();
        StackPane root = new StackPane();
        try {
            String cssPath = Objects.requireNonNull(LoginMenu.class.getResource("/styles/style.css")).toExternalForm();
            tilePane.getStylesheets().add(cssPath); // Adding the CSS file
        } catch (NullPointerException t) {
            System.out.println("CSS file not found.");
        }
        Scene scene = new Scene(tilePane, 800, 500); // Set the width and height as needed
        videoStage.setMinWidth(800);
        videoStage.setMinHeight(500);
        videoStage.setMaxWidth(800);
        videoStage.setMaxHeight(500);
        videoStage.setScene(scene);
        videoStage.setTitle("point chart");
        videoStage.show();
        // Set TilePane properties
        tilePane.setHgap(10); // horizontal gap between tiles
        tilePane.setVgap(10); // vertical gap between tiles

        // TODO: 6/27/2024 sort bar asas point
        int i = 0;
        for (User user : User.getUsers()) {
            VBox userBox = new VBox();
            userBox.getStyleClass().add("user-box");

            Label rankLabel = new Label((i + 1) + ". " + user.getUsername());
            rankLabel.getStyleClass().add("rank-label");

            Label scoreLabel = new Label("Score: " + user.getPassword());
            scoreLabel.getStyleClass().add("score-label");

            if (i == 0) {
                String imagePath = Objects.requireNonNull(getClass().getResource("/pics/gold.png").toExternalForm());
                Image image = new Image(imagePath);
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(55);
                imageView.setFitWidth(50);
                userBox.getChildren().add(imageView);
            } else if (i == 1) {
                String imagePath = Objects.requireNonNull(getClass().getResource("/pics/silver.png").toExternalForm());
                Image image = new Image(imagePath);
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(55);
                imageView.setFitWidth(50);
                userBox.getChildren().add(imageView);
            } else if (i == 2) {
                String imagePath = Objects.requireNonNull(getClass().getResource("/pics/bronze.png").toExternalForm());
                Image image = new Image(imagePath);
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(55);
                imageView.setFitWidth(50);
                userBox.getChildren().add(imageView);
            }
            userBox.setOnMouseEntered(e -> animateBox(userBox, 1.1));
            userBox.setOnMouseExited(e -> animateBox(userBox, 1.0));
            userBox.getChildren().addAll(rankLabel, scoreLabel);
            tilePane.getChildren().add(userBox);

            i++;
        }

    }

    private void animateBox(VBox box, double scale) {
        Timeline timeline = new Timeline();
        KeyValue kvX = new KeyValue(box.scaleXProperty(), scale);
        KeyValue kvY = new KeyValue(box.scaleYProperty(), scale);
        KeyFrame kf = new KeyFrame(Duration.millis(300), kvX, kvY);
        timeline.getKeyFrames().add(kf);
        timeline.play();
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
