package view;

import controller.ProfileMenuController;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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

        // TODO: 6/24/2024 az comment vardar
        username.setText(User.getLoggedInUser().getUsername());
        nickname.setText(User.getLoggedInUser().getNickname());
        email.setText(User.getLoggedInUser().getEmail());
        password.setText("Enter New Password");

        backToMainMenu.setOnMouseClicked(mouseEvent -> {
            MainMenu mainMenu = new MainMenu();
            try {
                mainMenu.start(App.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        changeUsername.setOnMouseClicked(mouseEvent -> {
            Result result = controller.changeUsername(username.getText());
            if(result.toString().equals("it's the same as your current username")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("change username alert");
                alert.setContentText("it's the same as your current username");
                alert.show();
            } else if (result.toString().equals("empty username")) {
                emptyFieldVideoPlay();
            } else if (result.toString().equals("there is exist an user with this username")) {
                thereIsExistUserWithUsernameVideoPlay();
            } else if (result.toString().equals("wrong username format")) {
                wrongUsernameFormatVideoPlay();
            } else if (result.isSuccessful()) {
                changedSuccessfullyVideoPlay();
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
                emptyFieldVideoPlay();
            } else if (result.isSuccessful()) {
                changedSuccessfullyVideoPlay();
            }

        });
        changeEmail.setOnMouseClicked(mouseEvent -> {
            Result result = controller.changeEmail(email.getText());
            if(result.toString().equals("empty email")){
                emptyFieldVideoPlay();
            } else if (result.toString().equals("it's the same as your current email")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("change email alert");
                alert.setContentText("it's the same as your current email");
                alert.show();
            } else if (result.toString().equals("wrong email format")) {
                wrongEmailFormatVideoPlay();
            } else if (result.isSuccessful()) {
                changedSuccessfullyVideoPlay();
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
                emptyFieldVideoPlay();
            } else if (result.toString().equals("wrong password format")) {
                wrongPasswordFormatVideoPlay();
            } else if (result.toString().equals("weak password")) {
                weakPasswordVideoPlay();
            } else if (result.isSuccessful()) {
                changedSuccessfullyVideoPlay();
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
    public void emptyFieldVideoPlay() {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/emptyField.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Create a new stage (window) for the video
        Stage videoStage = new Stage();
        StackPane root = new StackPane();
        root.getChildren().add(mediaView);
        Scene scene = new Scene(root, 800, 600); // Set the width and height as needed
        videoStage.setMinWidth(800);
        videoStage.setMinHeight(600);
        videoStage.setMaxWidth(800);
        videoStage.setMaxHeight(600);
        videoStage.setScene(scene);
        videoStage.setTitle("Empty Field");
        videoStage.show();

        // Play the video
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(4));
        pauseTransition.setOnFinished(actionEvent -> {
            videoStage.close();
        });
        pauseTransition.play();
    }
    public void thereIsExistUserWithUsernameVideoPlay() {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/thereIsExistUserWithUsername.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Create a new stage (window) for the video
        Stage videoStage = new Stage();
        StackPane root = new StackPane();
        root.getChildren().add(mediaView);
        Scene scene = new Scene(root, 900, 600); // Set the width and height as needed
        videoStage.setMinWidth(900);
        videoStage.setMinHeight(600);
        videoStage.setMaxWidth(900);
        videoStage.setMaxHeight(600);
        videoStage.setScene(scene);
        videoStage.setTitle("there Is Exist User With this Username");
        videoStage.show();

        // Play the video
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(4));
        pauseTransition.setOnFinished(actionEvent -> {
            videoStage.close();
        });
        pauseTransition.play();
    }
    public void wrongUsernameFormatVideoPlay() {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/wrongUsernameFormat.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Create a new stage (window) for the video
        Stage videoStage = new Stage();
        StackPane root = new StackPane();
        root.getChildren().add(mediaView);
        Scene scene = new Scene(root, 800, 600); // Set the width and height as needed
        videoStage.setMinWidth(800);
        videoStage.setMinHeight(600);
        videoStage.setMaxWidth(800);
        videoStage.setMaxHeight(600);
        videoStage.setScene(scene);
        videoStage.setTitle("wrong Username Format");
        videoStage.show();

        // Play the video
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(4));
        pauseTransition.setOnFinished(actionEvent -> {
            videoStage.close();
        });
        pauseTransition.play();
    }
    public void changedSuccessfullyVideoPlay() {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/changedSuccessfully.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Create a new stage (window) for the video
        Stage videoStage = new Stage();
        StackPane root = new StackPane();
        root.getChildren().add(mediaView);
        Scene scene = new Scene(root, 1080, 1080); // Set the width and height as needed
        videoStage.setMinWidth(1080);
        videoStage.setMinHeight(1080);
        videoStage.setMaxWidth(1080);
        videoStage.setMaxHeight(1080);
        videoStage.setScene(scene);
        videoStage.setTitle("changed Successfully");
        videoStage.show();

        // Play the video
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
        pauseTransition.setOnFinished(actionEvent -> {
            videoStage.close();
        });
        pauseTransition.play();
    }
    public void wrongEmailFormatVideoPlay() {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/wrongEmailFormat.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Create a new stage (window) for the video
        Stage videoStage = new Stage();
        StackPane root = new StackPane();
        root.getChildren().add(mediaView);
        Scene scene = new Scene(root, 800, 600); // Set the width and height as needed
        videoStage.setMinWidth(800);
        videoStage.setMinHeight(600);
        videoStage.setMaxWidth(800);
        videoStage.setMaxHeight(600);
        videoStage.setScene(scene);
        videoStage.setTitle("wrong Email Format");
        videoStage.show();

        // Play the video
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(4));
        pauseTransition.setOnFinished(actionEvent -> {
            videoStage.close();
        });
        pauseTransition.play();
    }
    public void wrongPasswordFormatVideoPlay() {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/wrongPasswordFormat.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Create a new stage (window) for the video
        Stage videoStage = new Stage();
        StackPane root = new StackPane();
        root.getChildren().add(mediaView);
        Scene scene = new Scene(root, 800, 600); // Set the width and height as needed
        videoStage.setMinWidth(800);
        videoStage.setMinHeight(600);
        videoStage.setMaxWidth(800);
        videoStage.setMaxHeight(600);
        videoStage.setScene(scene);
        videoStage.setTitle("wrong Password Format");
        videoStage.show();

        // Play the video
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(4));
        pauseTransition.setOnFinished(actionEvent -> {
            videoStage.close();
        });
        pauseTransition.play();
    }
    public void weakPasswordVideoPlay() {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/weakPassword.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Create a new stage (window) for the video
        Stage videoStage = new Stage();
        StackPane root = new StackPane();
        root.getChildren().add(mediaView);
        Scene scene = new Scene(root, 800, 500); // Set the width and height as needed
        videoStage.setMinWidth(800);
        videoStage.setMinHeight(500);
        videoStage.setMaxWidth(800);
        videoStage.setMaxHeight(500);
        videoStage.setScene(scene);
        videoStage.setTitle("weak Password");
        videoStage.show();

        // Play the video
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(6));
        pauseTransition.setOnFinished(actionEvent -> {
            videoStage.close();
        });
        pauseTransition.play();
    }
    public void changeUsername() {

    }
}
