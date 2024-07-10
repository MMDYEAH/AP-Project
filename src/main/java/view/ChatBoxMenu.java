package view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.App;
import model.User;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class ChatBoxMenu extends Application {
    public TextField message;
    public Button send;
    public ScrollPane scrollPane;
    public VBox vBox;

    private Scene scene;

    private Pane pane;

    private GameMenu gameMenu;


    @Override
    public void start(Stage stage) throws Exception {
        Image logo = new Image(getClass().getResourceAsStream("/pics/logo.png"));
        stage.getIcons().add(logo);
        FXMLLoader fxmlLoader = new FXMLLoader(ChatBoxMenu.class.getResource("/chatBox.fxml"));
        pane = fxmlLoader.load();
        StackPane root = new StackPane();
        root.getChildren().add(pane);
        scene = new Scene(root);
        stage.setTitle("chat box");
        try {
            String cssPath = Objects.requireNonNull(LoginMenu.class.getResource("/styles/style.css")).toExternalForm();
            root.getStylesheets().add(cssPath); // Adding the CSS file
        } catch (NullPointerException t) {
            System.out.println("CSS file not found.");
        }

        initialize();
        sendOnMouseClick();
        stage.setScene(scene);
        stage.show();
    }

    private void sendOnMouseClick() {
        send.setOnMouseClicked(mouseEvent -> {
            if (message.getText().equals("") || message.getText().length() > 20 || message.getText().contains("&")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("short or long message (or invalid message:{you can't use \"&\" in chat})");
            } else {
                String time = LocalTime.now().toString();
                App.getGameClient().sendMessage("chat:" + User.getLoggedInUser().getUsername() + "&"
                        + message.getText() + "&" + time);
                addMessage(User.getLoggedInUser().getUsername() + " : " + message.getText() + " ( " + time + " )");
            }

        });
    }

    private void initialize() {
        message = (TextField) scene.lookup("#message");
        // Set up a timeline for color animation
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> message.setStyle("-fx-text-fill: red;")),
                new KeyFrame(Duration.millis(250), e -> message.setStyle("-fx-text-fill: orange;")),
                new KeyFrame(Duration.millis(500), e -> message.setStyle("-fx-text-fill: yellow;")),
                new KeyFrame(Duration.millis(750), e -> message.setStyle("-fx-text-fill: green;")),
                new KeyFrame(Duration.millis(1000), e -> message.setStyle("-fx-text-fill: blue;"))
        );
        timeline.setCycleCount(Animation.INDEFINITE); // Repeat indefinitely
        // Add listener to start/stop animation based on focus
        message.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                timeline.play();
            } else {
                timeline.stop();
                message.setStyle(""); // Reset style when not focused
            }
        });
        send = (Button) scene.lookup("#send");
        send.setOnMouseEntered(e -> animateButton(send, 1.1));
        send.setOnMouseExited(e -> animateButton(send, 1.0));
        scrollPane = (ScrollPane) scene.lookup("#scrollPane");
        vBox = (VBox) scrollPane.getContent().lookup("#vBox");
        for (String message : gameMenu.getMessages()) {
            Text text = new Text(message);
            vBox.getChildren().add(text);
        }
    }

    public void addMessage(String message) {
        gameMenu.getMessages().add(message);
        Text text = new Text(message);
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

    public void setGameMenu(GameMenu gameMenu) {
        this.gameMenu = gameMenu;
    }
}
