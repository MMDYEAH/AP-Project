package view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.App;
import model.User;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.Objects;

public class FriendRequestMenu extends Application {
    @FXML
    Button close;
    public ScrollPane scrollPane;
    public VBox vBox;
    static StackPane root;
    private Pane pane;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginMenu.class.getResource("/friendRequest.fxml"));
        pane = fxmlLoader.load();
        root = new StackPane();
        Scene scene = new Scene(root);
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
        root.getChildren().add(mediaView);
        root.getChildren().add(pane);
        Image logo = new Image(getClass().getResourceAsStream("/pics/logo.png"));
        stage.getIcons().add(logo);
        close = (Button) scene.lookup("#close");
        scrollPane = (ScrollPane) scene.lookup("#scrollPane");
        vBox = (VBox) scrollPane.getContent().lookup("#vBox");
        App.getGameClient().sendMessage("get users");

        close.setOnMouseEntered(e -> animateButton(close, 1.1));
        close.setOnMouseExited(e -> animateButton(close, 1.0));

        close.setOnMouseClicked(event -> {
            stage.close();
        });
        try {
            String cssPath = Objects.requireNonNull(LoginMenu.class.getResource("/styles/friendRequest.css")).toExternalForm();
            root.getStylesheets().add(cssPath); // Adding the CSS file
        } catch (NullPointerException t) {
            System.out.println("CSS file not found.");
        }
        mediaView.setFitWidth(1920);
        mediaView.setFitHeight(1080);
        stage.setResizable(false);
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }

    public void addUser(User user) {
        System.out.println("hi");
        VBox vBox1 = new VBox();
        Text text = new Text("username : " +  user.getUsername());


        text.setOnMouseClicked(mouseEvent -> {
            // Load the image
            String imagePath = "/pics/friendRequest.png"; // Change this to the path of your image file
            Image image = new Image(getClass().getResource(imagePath).toExternalForm());
            ImageView imageView = new ImageView(image);
            root.getChildren().add(imageView);
            Text info = new Text("nick name: "+user.getNickname()+" score: "+user.getScore());
            Button send = new Button("send request");

            send.setOnMouseEntered(e -> animateButton(send, 1.1));
            send.setOnMouseExited(e -> animateButton(send, 1.0));
            Button back = new Button("back");

            back.setOnMouseEntered(e -> animateButton(back, 1.1));
            back.setOnMouseExited(e -> animateButton(back, 1.0));
            VBox userInfo = new VBox(info,send,back);
            userInfo.setAlignment(Pos.CENTER);
//            userInfo.setLayoutX(600);
//            userInfo.setLayoutY(43);
            userInfo.setSpacing(10);
            send.setOnMouseClicked(mouseEvent1 -> {
                App.getGameClient().sendMessage("friend request:"+user.getUsername());
                root.getChildren().removeAll(userInfo,imageView);
            });
            userInfo.setMaxWidth(500);
            back.setOnMouseClicked(mouseEvent1 -> {
                root.getChildren().removeAll(userInfo,imageView);
            });

            userInfo.setAlignment(Pos.CENTER);
            root.getChildren().add(userInfo);
        });
        vBox.getChildren().add(text);
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
