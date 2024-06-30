package view;

import controller.ProfileMenuController;
import javafx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.App;
import model.Result;
import model.User;

import java.util.Objects;
import java.util.Scanner;

public class ProfileMenu extends Application {

    @FXML
    private TextField username;
    @FXML
    Button changeUsername;
    @FXML
    private TextField nickname;
    @FXML
    Button changeNickname;
    @FXML
    private TextField password;
    @FXML
    Button changePassword;
    @FXML
    private TextField email;
    @FXML
    Button changeEmail;
    @FXML
    private TextField oldPassword;
    ProfileMenuController controller = new ProfileMenuController(this);
    @FXML
    Button backToMainMenu;

    @Override
    public void start(Stage stage) throws Exception {
//        controller.initialize();
        App.setStage(stage);
        // Load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(LoginMenu.class.getResource("/profileMenu.fxml"));
        Pane pane = fxmlLoader.load();

        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/witcherBG.mp4")).toExternalForm();
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



        //add text fields
        username = (TextField) scene.lookup("#username");
        // TODO: 6/24/2024 az comment vardar 
        password = (TextField) scene.lookup("#password");
        email = (TextField) scene.lookup("#email");
        nickname = (TextField) scene.lookup("#nickname");
        changeUsername = (Button) scene.lookup("#changeUsername");
        changePassword = (Button) scene.lookup("#changePassword");
        changeNickname = (Button) scene.lookup("#changeNickname");
        changeEmail = (Button) scene.lookup("#changeEmail");
        oldPassword = (TextField) scene.lookup("#oldPassword");
        backToMainMenu = (Button) scene.lookup("#backToMainMenu");

        // Set up a timeline for color animation
        Timeline timelineNickname = new Timeline(
                new KeyFrame(Duration.ZERO, e -> nickname.setStyle("-fx-text-fill: red;")),
                new KeyFrame(Duration.millis(250), e -> nickname.setStyle("-fx-text-fill: orange;")),
                new KeyFrame(Duration.millis(500), e -> nickname.setStyle("-fx-text-fill: yellow;")),
                new KeyFrame(Duration.millis(750), e -> nickname.setStyle("-fx-text-fill: green;")),
                new KeyFrame(Duration.millis(1000), e -> nickname.setStyle("-fx-text-fill: blue;"))
        );
        timelineNickname.setCycleCount(Animation.INDEFINITE); // Repeat indefinitely

        // Add listener to start/stop animation based on focus
        nickname.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                timelineNickname.play();
            } else {
                timelineNickname.stop();
                nickname.setStyle(""); // Reset style when not focused
            }
        });

        // Set up a timeline for color animation
        Timeline timelineUser = new Timeline(
                new KeyFrame(Duration.ZERO, e -> username.setStyle("-fx-text-fill: red;")),
                new KeyFrame(Duration.millis(250), e -> username.setStyle("-fx-text-fill: orange;")),
                new KeyFrame(Duration.millis(500), e -> username.setStyle("-fx-text-fill: yellow;")),
                new KeyFrame(Duration.millis(750), e -> username.setStyle("-fx-text-fill: green;")),
                new KeyFrame(Duration.millis(1000), e -> username.setStyle("-fx-text-fill: blue;"))
        );
        timelineUser.setCycleCount(Animation.INDEFINITE); // Repeat indefinitely

        // Add listener to start/stop animation based on focus
        username.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                timelineUser.play();
            } else {
                timelineUser.stop();
                username.setStyle(""); // Reset style when not focused
            }
        });

        // Set up a timeline for color animation
        Timeline timelineEmail = new Timeline(
                new KeyFrame(Duration.ZERO, e -> email.setStyle("-fx-text-fill: red;")),
                new KeyFrame(Duration.millis(250), e -> email.setStyle("-fx-text-fill: orange;")),
                new KeyFrame(Duration.millis(500), e -> email.setStyle("-fx-text-fill: yellow;")),
                new KeyFrame(Duration.millis(750), e -> email.setStyle("-fx-text-fill: green;")),
                new KeyFrame(Duration.millis(1000), e -> email.setStyle("-fx-text-fill: blue;"))
        );
        timelineEmail.setCycleCount(Animation.INDEFINITE); // Repeat indefinitely

        // Add listener to start/stop animation based on focus
        email.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                timelineEmail.play();
            } else {
                timelineEmail.stop();
                email.setStyle(""); // Reset style when not focused
            }
        });

        // Set up a timeline for color animation
        Timeline timelinePassword = new Timeline(
                new KeyFrame(Duration.ZERO, e -> password.setStyle("-fx-text-fill: red;")),
                new KeyFrame(Duration.millis(250), e -> password.setStyle("-fx-text-fill: orange;")),
                new KeyFrame(Duration.millis(500), e -> password.setStyle("-fx-text-fill: yellow;")),
                new KeyFrame(Duration.millis(750), e -> password.setStyle("-fx-text-fill: green;")),
                new KeyFrame(Duration.millis(1000), e -> password.setStyle("-fx-text-fill: blue;"))
        );
        timelinePassword.setCycleCount(Animation.INDEFINITE); // Repeat indefinitely

        // Add listener to start/stop animation based on focus
        password.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                timelinePassword.play();
            } else {
                timelinePassword.stop();
                password.setStyle(""); // Reset style when not focused
            }
        });

        // Set up a timeline for color animation
        Timeline timelineOldPassword = new Timeline(
                new KeyFrame(Duration.ZERO, e -> oldPassword.setStyle("-fx-text-fill: red;")),
                new KeyFrame(Duration.millis(250), e -> oldPassword.setStyle("-fx-text-fill: orange;")),
                new KeyFrame(Duration.millis(500), e -> oldPassword.setStyle("-fx-text-fill: yellow;")),
                new KeyFrame(Duration.millis(750), e -> oldPassword.setStyle("-fx-text-fill: green;")),
                new KeyFrame(Duration.millis(1000), e -> oldPassword.setStyle("-fx-text-fill: blue;"))
        );
        timelineOldPassword.setCycleCount(Animation.INDEFINITE); // Repeat indefinitely

        // Add listener to start/stop animation based on focus
        oldPassword.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                timelineOldPassword.play();
            } else {
                timelineOldPassword.stop();
                oldPassword.setStyle(""); // Reset style when not focused
            }
        });


        // TODO: 6/24/2024 az comment vardar
        username.setText(User.getLoggedInUser().getUsername());
        nickname.setText(User.getLoggedInUser().getNickname());
        email.setText(User.getLoggedInUser().getEmail());
        password.setPromptText("Enter New Password");

        backToMainMenu.setOnMouseEntered(e -> animateButton(backToMainMenu, 1.1));
        backToMainMenu.setOnMouseExited(e -> animateButton(backToMainMenu, 1.0));

        changeUsername.setOnMouseEntered(e -> animateButton(changeUsername, 1.1));
        changeUsername.setOnMouseExited(e -> animateButton(changeUsername, 1.0));

        changeEmail.setOnMouseEntered(e -> animateButton(changeEmail, 1.1));
        changeEmail.setOnMouseExited(e -> animateButton(changeEmail, 1.0));

        changePassword.setOnMouseEntered(e -> animateButton(changePassword, 1.1));
        changePassword.setOnMouseExited(e -> animateButton(changePassword, 1.0));

        changeNickname.setOnMouseEntered(e -> animateButton(changeNickname, 1.1));
        changeNickname.setOnMouseExited(e -> animateButton(changeNickname, 1.0));

        backToMainMenu.setOnMouseClicked(mouseEvent -> {
            MainMenu mainMenu = new MainMenu();
            try {
                mainMenu.start(App.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            App.getStage().setFullScreen(true);
        });

        changeUsername.setOnMouseClicked(mouseEvent -> {
            Result result = controller.changeUsername(username.getText());
            if(result.toString().equals("it's the same as your current username")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("change username alert");
                alert.setContentText("it's the same as your current username");
                alert.show();
            } else if (result.toString().equals("empty username")) {
                emptyFieldVideoPlay(root);
            } else if (result.toString().equals("there is exist an user with this username")) {
                thereIsExistUserWithUsernameVideoPlay(root);
            } else if (result.toString().equals("wrong username format")) {
                wrongUsernameFormatVideoPlay(root);
            } else if (result.isSuccessful()) {
                changedSuccessfullyVideoPlay(root);
            }
        });
        changeNickname.setOnMouseClicked(mouseEvent -> {
            Result result = controller.changeNickname(nickname.getText());
            if(result.toString().equals("it's the same as your current nickname")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("same nickname");
                alert.setContentText("it's the same as your current nickname");
                alert.show();
            } else if (result.toString().equals("empty nickname")) {
                emptyFieldVideoPlay(root);
            } else if (result.isSuccessful()) {
                changedSuccessfullyVideoPlay(root);
            }

        });
        changeEmail.setOnMouseClicked(mouseEvent -> {
            Result result = controller.changeEmail(email.getText());
            if(result.toString().equals("empty email")){
                emptyFieldVideoPlay(root);
            } else if (result.toString().equals("it's the same as your current email")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("change email alert");
                alert.setContentText("it's the same as your current email");
                alert.show();
            } else if (result.toString().equals("wrong email format")) {
                wrongEmailFormatVideoPlay(root);
            } else if (result.isSuccessful()) {
                changedSuccessfullyVideoPlay(root);
            }
        });
        changePassword.setOnMouseClicked(mouseEvent -> {
            Result result = controller.changePassword(oldPassword.getText(), password.getText());
            if(result.toString().equals("old password in wrong")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("old password in wrong");
                alert.setContentText("old password in wrong");
                alert.show();
            }else if(result.toString().equals("it's the same as your current password")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("same password");
                alert.setContentText("it's the same as your current password");
                alert.show();
            } else if (result.toString().equals("empty password")) {
                emptyFieldVideoPlay(root);
            } else if (result.toString().equals("wrong password format")) {
                wrongPasswordFormatVideoPlay(root);
            } else if (result.toString().equals("weak password")) {
                weakPasswordVideoPlay(root);
            } else if (result.isSuccessful()) {
                changedSuccessfullyVideoPlay(root);
            }
        });

        mediaView.setFitWidth(stage.getWidth());
        mediaView.setFitHeight(stage.getHeight());
        mediaView.setPreserveRatio(false);
        stage.setFullScreen(true);
        stage.setResizable(false);

        stage.setTitle("Profile Menu");
        stage.setScene(scene);
        stage.show();
    }
    public void emptyFieldVideoPlay(StackPane root) {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/emptyField.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Create a new stage (window) for the video
//        Stage videoStage = new Stage();
//        StackPane root = new StackPane();
        root.getChildren().add(mediaView);
//        Scene scene = new Scene(root, 800, 600); // Set the width and height as needed
//        videoStage.setMinWidth(800);
//        videoStage.setMinHeight(600);
//        videoStage.setMaxWidth(800);
//        videoStage.setMaxHeight(600);
//        videoStage.setScene(scene);
//        videoStage.setTitle("Empty Field");
//        videoStage.show();

        // Play the video
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(4));
        pauseTransition.setOnFinished(actionEvent -> {
//            videoStage.close();
            root.getChildren().remove(mediaView);
        });
        pauseTransition.play();
    }
    public void thereIsExistUserWithUsernameVideoPlay(StackPane root) {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/thereIsExistUserWithUsername.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Create a new stage (window) for the video
//        Stage videoStage = new Stage();
//        StackPane root = new StackPane();
        root.getChildren().add(mediaView);
//        Scene scene = new Scene(root, 900, 600); // Set the width and height as needed
//        videoStage.setMinWidth(900);
//        videoStage.setMinHeight(600);
//        videoStage.setMaxWidth(900);
//        videoStage.setMaxHeight(600);
//        videoStage.setScene(scene);
//        videoStage.setTitle("there Is Exist User With this Username");
//        videoStage.show();

        // Play the video
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(4));
        pauseTransition.setOnFinished(actionEvent -> {
//            videoStage.close();
            root.getChildren().remove(mediaView);
        });
        pauseTransition.play();
    }
    public void wrongUsernameFormatVideoPlay(StackPane root) {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/wrongUsernameFormat.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Create a new stage (window) for the video
//        Stage videoStage = new Stage();
//        StackPane root = new StackPane();
        root.getChildren().add(mediaView);
//        Scene scene = new Scene(root, 800, 600); // Set the width and height as needed
//        videoStage.setMinWidth(800);
//        videoStage.setMinHeight(600);
//        videoStage.setMaxWidth(800);
//        videoStage.setMaxHeight(600);
//        videoStage.setScene(scene);
//        videoStage.setTitle("wrong Username Format");
//        videoStage.show();

        // Play the video
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(4));
        pauseTransition.setOnFinished(actionEvent -> {
//            videoStage.close();
            root.getChildren().remove(mediaView);
        });
        pauseTransition.play();
    }
    public void changedSuccessfullyVideoPlay(StackPane root) {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/changedSuccessfully.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Create a new stage (window) for the video
//        Stage videoStage = new Stage();
//        StackPane root = new StackPane();
        root.getChildren().add(mediaView);
//        Scene scene = new Scene(root, 1080, 1080); // Set the width and height as needed
//        videoStage.setMinWidth(1080);
//        videoStage.setMinHeight(1080);
//        videoStage.setMaxWidth(1080);
//        videoStage.setMaxHeight(1080);
//        videoStage.setScene(scene);
//        videoStage.setTitle("changed Successfully");
//        videoStage.show();

        // Play the video
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
        pauseTransition.setOnFinished(actionEvent -> {
//            videoStage.close();
            root.getChildren().remove(mediaView);
        });
        pauseTransition.play();
    }
    public void wrongEmailFormatVideoPlay(StackPane root) {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/wrongEmailFormat.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Create a new stage (window) for the video
//        Stage videoStage = new Stage();
//        StackPane root = new StackPane();
        root.getChildren().add(mediaView);
//        Scene scene = new Scene(root, 800, 600); // Set the width and height as needed
//        videoStage.setMinWidth(800);
//        videoStage.setMinHeight(600);
//        videoStage.setMaxWidth(800);
//        videoStage.setMaxHeight(600);
//        videoStage.setScene(scene);
//        videoStage.setTitle("wrong Email Format");
//        videoStage.show();

        // Play the video
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(4));
        pauseTransition.setOnFinished(actionEvent -> {
//            videoStage.close();
            root.getChildren().remove(mediaView);
        });
        pauseTransition.play();
    }
    public void wrongPasswordFormatVideoPlay(StackPane root) {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/wrongPasswordFormat.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Create a new stage (window) for the video
//        Stage videoStage = new Stage();
//        StackPane root = new StackPane();
        root.getChildren().add(mediaView);
//        Scene scene = new Scene(root, 800, 600); // Set the width and height as needed
//        videoStage.setMinWidth(800);
//        videoStage.setMinHeight(600);
//        videoStage.setMaxWidth(800);
//        videoStage.setMaxHeight(600);
//        videoStage.setScene(scene);
//        videoStage.setTitle("wrong Password Format");
//        videoStage.show();

        // Play the video
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(4));
        pauseTransition.setOnFinished(actionEvent -> {
//            videoStage.close();
            root.getChildren().remove(mediaView);
        });
        pauseTransition.play();
    }
    public void weakPasswordVideoPlay(StackPane root) {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/weakPassword.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Create a new stage (window) for the video
//        Stage videoStage = new Stage();
//        StackPane root = new StackPane();
        root.getChildren().add(mediaView);
//        Scene scene = new Scene(root, 800, 500); // Set the width and height as needed
//        videoStage.setMinWidth(800);
//        videoStage.setMinHeight(500);
//        videoStage.setMaxWidth(800);
//        videoStage.setMaxHeight(500);
//        videoStage.setScene(scene);
//        videoStage.setTitle("weak Password");
//        videoStage.show();

        // Play the video
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(6));
        pauseTransition.setOnFinished(actionEvent -> {
//            videoStage.close();
            root.getChildren().remove(mediaView);
        });
        pauseTransition.play();
    }
    private void animateButton(Button button, double scale) {
        Timeline timeline = new Timeline();
        KeyValue kvX = new KeyValue(button.scaleXProperty(), scale);
        KeyValue kvY = new KeyValue(button.scaleYProperty(), scale);
        KeyFrame kf = new KeyFrame(Duration.millis(300), kvX, kvY);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
    public void changeUsername() {

    }
}
