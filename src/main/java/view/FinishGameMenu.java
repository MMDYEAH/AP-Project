package view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.App;
import model.Game;

import java.util.Objects;

public class FinishGameMenu extends Application {
    @FXML
    Button toMainMenu;
    @FXML
    ImageView victoryDefeatPic;
    @FXML
    Text meFirst;
    @FXML
    Text meSecond;
    @FXML
    Text meThird;
    @FXML
    Text enemyFirst;
    @FXML
    Text enemySecond;
    @FXML
    Text enemyThird;

    @Override
    public void start(Stage stage) throws Exception {
        if (Game.getCurrentGame().getMylive() == 0) {
            App.setStage(stage);
            FXMLLoader fxmlLoader = new FXMLLoader(LoginMenu.class.getResource("/finishGame.fxml"));
            Pane pane = fxmlLoader.load();
            // Set up the background video
            String videoPath = Objects.requireNonNull(getClass().getResource("/videos/defeat.mp4")).toExternalForm();
            Media preGameMenuVideo = new Media(videoPath);
            MediaPlayer mediaPlayer = new MediaPlayer(preGameMenuVideo);
            MediaView mediaView = new MediaView(mediaPlayer);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.statusProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == MediaPlayer.Status.READY) {
                    mediaPlayer.play();
                }
            });
            StackPane root = new StackPane();
            root.getChildren().add(mediaView);
            root.getChildren().add(pane);
            root.setCenterShape(true);
            Scene scene = new Scene(root);

            toMainMenu = (Button) scene.lookup("#toMainMenu");
            meFirst = (Text) scene.lookup("#meFirst");
            meSecond = (Text) scene.lookup("#meSecond");
            meThird = (Text) scene.lookup("#meThird");
            enemyFirst = (Text) scene.lookup("#enemyFirst");
            enemySecond = (Text) scene.lookup("#enemySecond");
            enemyThird = (Text) scene.lookup("#enemyThird");
            victoryDefeatPic = (ImageView) scene.lookup("#victoryDefeatPic");
            String victoryDefeatPath = getClass().getResource("/pics/defeat.png").toExternalForm();
            victoryDefeatPic.setImage(new Image(victoryDefeatPath));

            meFirst.setText(Game.getCurrentGame().getRoundsScore().get(0).get(0).toString());
            meSecond.setText(Game.getCurrentGame().getRoundsScore().get(1).get(0).toString());
            meThird.setText(Game.getCurrentGame().getRoundsScore().get(2).get(0).toString());
            enemyFirst.setText(Game.getCurrentGame().getRoundsScore().get(0).get(1).toString());
            enemySecond.setText(Game.getCurrentGame().getRoundsScore().get(1).get(1).toString());
            enemyThird.setText(Game.getCurrentGame().getRoundsScore().get(2).get(1).toString());


            try {
                String cssPath = Objects.requireNonNull(LoginMenu.class.getResource("/styles/finishGame.css")).toExternalForm();
                scene.getStylesheets().add(cssPath); // Adding the CSS file to the scene
            } catch (NullPointerException e) {
                System.out.println("CSS file not found.");
            }

            toMainMenu.setOnMouseEntered(e -> animateButton(toMainMenu, 1.1));
            toMainMenu.setOnMouseExited(e -> animateButton(toMainMenu, 1.0));

            toMainMenu.setOnMouseClicked(event -> {
                toMainMenu(stage);
            });

            stage.setScene(scene);
            stage.setFullScreen(true);
            mediaView.setFitWidth(1920);
            mediaView.setFitHeight(1080);
            stage.setResizable(false);
            stage.show();
        } else {
            App.setStage(stage);
            FXMLLoader fxmlLoader = new FXMLLoader(LoginMenu.class.getResource("/finishGame.fxml"));
            Pane pane = fxmlLoader.load();
            // Set up the background video
            String videoPath = Objects.requireNonNull(getClass().getResource("/videos/victory.mp4")).toExternalForm();
            Media preGameMenuVideo = new Media(videoPath);
            MediaPlayer mediaPlayer = new MediaPlayer(preGameMenuVideo);
            MediaView mediaView = new MediaView(mediaPlayer);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.statusProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == MediaPlayer.Status.READY) {
                    mediaPlayer.play();
                }
            });
            StackPane root = new StackPane();
            root.getChildren().add(mediaView);
            root.getChildren().add(pane);
            root.setCenterShape(true);
            Scene scene = new Scene(root);

            toMainMenu = (Button) scene.lookup("#toMainMenu");
            meFirst = (Text) scene.lookup("#meFirst");
            meSecond = (Text) scene.lookup("#meSecond");
            meThird = (Text) scene.lookup("#meThird");
            enemyFirst = (Text) scene.lookup("#enemyFirst");
            enemySecond = (Text) scene.lookup("#enemySecond");
            enemyThird = (Text) scene.lookup("#enemyThird");
            victoryDefeatPic = (ImageView) scene.lookup("#victoryDefeatPic");
            String victoryDefeatPath = getClass().getResource("/pics/victory.png").toExternalForm();
            victoryDefeatPic.setImage(new Image(victoryDefeatPath));
            try {
                String cssPath = Objects.requireNonNull(LoginMenu.class.getResource("/styles/finishGame.css")).toExternalForm();
                scene.getStylesheets().add(cssPath); // Adding the CSS file to the scene
            } catch (NullPointerException e) {
                System.out.println("CSS file not found.");
            }

            toMainMenu.setOnMouseEntered(e -> animateButton(toMainMenu, 1.1));
            toMainMenu.setOnMouseExited(e -> animateButton(toMainMenu, 1.0));

            toMainMenu.setOnMouseClicked(event -> {
                toMainMenu(stage);
                App.getStage().setFullScreen(true);
            });


            meFirst.setText(Game.getCurrentGame().getRoundsScore().get(0).get(0).toString());
            meSecond.setText(Game.getCurrentGame().getRoundsScore().get(1).get(0).toString());
            meThird.setText(Game.getCurrentGame().getRoundsScore().get(2).get(0).toString());
            enemyFirst.setText(Game.getCurrentGame().getRoundsScore().get(0).get(1).toString());
            enemySecond.setText(Game.getCurrentGame().getRoundsScore().get(1).get(1).toString());
            enemyThird.setText(Game.getCurrentGame().getRoundsScore().get(2).get(1).toString());

            stage.setScene(scene);
            stage.setFullScreen(true);
            mediaView.setFitWidth(1920);
            mediaView.setFitHeight(1080);
            stage.setResizable(false);
            stage.show();
        }
        App.getStage().setFullScreen(true);
    }

    public void toMainMenu(Stage stage) {
        MainMenu mainMenu = new MainMenu();
        try {
            stage.setFullScreen(true);
            mainMenu.start(App.getStage());
            stage.setFullScreen(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        stage.setFullScreen(true);
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
