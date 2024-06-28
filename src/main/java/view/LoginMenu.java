package view;

import controller.LoginMenuController;
import javafx.animation.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.App;
import model.Question;
import model.Result;
import model.User;


import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginMenu extends Application implements Initializable {
    @FXML
    private Button firstQ;
    @FXML
    private Button secondQ;
    @FXML
    private Button thirdQ;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordConfirm;
    @FXML
    private TextField nickname;
    @FXML
    private TextField email;
    @FXML
    private TextField questionAnswer;
    @FXML
    private Text yourRandomPassword;
    @FXML
    Button login;
    @FXML
    Button apply;
    @FXML
    Button signUp;
    @FXML
    Button chooseRandomPassword;
    @FXML
    Button randomPassword;
    static Media loginVideo;
    LoginMenuController controller = new LoginMenuController(this);

    @Override
    public void start(Stage stage) throws Exception {
        Image logo = new Image(getClass().getResourceAsStream("/pics/logo.png"));
        // Set the logo image as the window icon
        stage.getIcons().add(logo);
        controller.initialize();
        App.setStage(stage);
        // Load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(LoginMenu.class.getResource("/loginMenu.fxml"));
        Pane pane = fxmlLoader.load();

        // Set up the background video
        MediaPlayer mediaPlayer = new MediaPlayer(loginVideo);
        MediaView mediaView = new MediaView();
        mediaView.setMediaPlayer(mediaPlayer);
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
        root.getChildren().add(pane); // Add the FXML content on top of the video

        Scene scene = new Scene(root);
        try {
            String cssPath = Objects.requireNonNull(LoginMenu.class.getResource("/styles/style.css")).toExternalForm();
            root.getStylesheets().add(cssPath); // Adding the CSS file
        } catch (NullPointerException t) {
            System.out.println("CSS file not found.");
        }
        stage.setScene(scene);


        //add text fields
        username = (TextField) scene.lookup("#username");
        password = (PasswordField) scene.lookup("#password");
        passwordConfirm = (PasswordField) scene.lookup("#passwordConfirm");
        nickname = (TextField) scene.lookup("#nickname");
        email = (TextField) scene.lookup("#email");
        yourRandomPassword = (Text) scene.lookup("#yourRandomPassword");
        login = (Button) scene.lookup("#login");
        signUp = (Button) scene.lookup("#signUp");
        randomPassword = (Button) scene.lookup("#randomPassword");
        chooseRandomPassword = (Button) scene.lookup("#chooseRandomPassword");

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
        Timeline timelinePasswordConfirm = new Timeline(
                new KeyFrame(Duration.ZERO, e -> passwordConfirm.setStyle("-fx-text-fill: red;")),
                new KeyFrame(Duration.millis(250), e -> passwordConfirm.setStyle("-fx-text-fill: orange;")),
                new KeyFrame(Duration.millis(500), e -> passwordConfirm.setStyle("-fx-text-fill: yellow;")),
                new KeyFrame(Duration.millis(750), e -> passwordConfirm.setStyle("-fx-text-fill: green;")),
                new KeyFrame(Duration.millis(1000), e -> passwordConfirm.setStyle("-fx-text-fill: blue;"))
        );
        timelinePasswordConfirm.setCycleCount(Animation.INDEFINITE); // Repeat indefinitely

        // Add listener to start/stop animation based on focus
        passwordConfirm.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                timelinePasswordConfirm.play();
            } else {
                timelinePasswordConfirm.stop();
                passwordConfirm.setStyle(""); // Reset style when not focused
            }
        });
        
        chooseRandomPassword.setOnMouseEntered(e -> animateButton(chooseRandomPassword, 1.1));
        chooseRandomPassword.setOnMouseExited(e -> animateButton(chooseRandomPassword, 1.0));

        randomPassword.setOnMouseEntered(e -> animateButton(randomPassword, 1.1));
        randomPassword.setOnMouseExited(e -> animateButton(randomPassword, 1.0));

        signUp.setOnMouseEntered(e -> animateButton(signUp, 1.1));
        signUp.setOnMouseExited(e -> animateButton(signUp, 1.0));

        login.setOnMouseEntered(e -> animateButton(login, 1.1));
        login.setOnMouseExited(e -> animateButton(login, 1.0));

        signUp.setOnMouseClicked(mouseEvent -> {
            try {
                signUp(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        login.setOnMouseClicked(mouseEvent -> {
            try {
                login(root);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // Ensure the video fits the stage
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            mediaView.setFitWidth(newVal.doubleValue());
        });
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            mediaView.setFitHeight(newVal.doubleValue());
        });

        mediaView.setFitWidth(stage.getWidth());
        mediaView.setFitHeight(stage.getHeight());
        mediaView.setPreserveRatio(false);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setTitle("Login Menu");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void login(StackPane root) throws Exception {
        Result result = controller.login(username.getText(), password.getText());
        if (result.isSuccessful()) {
            User.setLoggedInUser(User.registeringUser);
            loggedInSuccessfullyVideoPlay();
        } else if (result.toString().equals("wrong password")) {
            wrongPasswordVideoPlay(root);
        } else if (result.toString().equals("no such user exist")) {
            noSuchUserExistVideoPlay(root);
        } else {
            System.out.println(result);
        }
    }

    public void signUp(StackPane root) throws IOException {
        Result result = controller.register(username.getText(), password.getText(), passwordConfirm.getText(), nickname.getText(), email.getText(), null);
        System.out.println(result);
        if (result.isSuccessful()) {
            securityQuestion(root);
        } else if (result.toString().equals("empty field")) {
            emptyFieldVideoPlay(root);
        } else if (result.toString().equals("there is exist an user with this username")) {
            thereIsExistUserWithUsernameVideoPlay(root);
        } else if (result.toString().equals("wrong username format")) {
            wrongUsernameFormatVideoPlay(root);
        } else if (result.toString().equals("wrong email format")) {
            wrongEmailFormatVideoPlay(root);
        } else if (result.toString().equals("wrong password format")) {
            wrongPasswordFormatVideoPlay(root);
        } else if (result.toString().equals("weak password")) {
            weakPasswordVideoPlay(root);
        } else if (result.toString().equals("user created successfully")) {
            userCreatedSuccessfullyVideoPlay(root);
        } else if (result.toString().equals("wrong password")) {
            wrongPasswordVideoPlay(root);
        } else if (result.toString().equals("confirm password failed")) {
            confirmPasswordFailedVideoPlay(root);
        }
    }

    public void randomPasswordSet() {
        yourRandomPassword.setText(controller.makeRandomPassword());
        yourRandomPassword.setTextAlignment(TextAlignment.RIGHT);
    }

    public void chooseRandomPassword() {
        password.setText(yourRandomPassword.getText());
        passwordConfirm.setText(yourRandomPassword.getText());
    }

    public void securityQuestion(StackPane root) throws IOException {
        // Load the image
        String imagePath = "/pics/security.jpg"; // Change this to the path of your image file
        Image image = new Image(getClass().getResource(imagePath).toExternalForm());
        ImageView imageView = new ImageView(image);
        root.getChildren().add(imageView);

        // Create buttons and text field
        Button firstQ = new Button(Question.getQuestionByNumber(0).getQuestion());
        Button secondQ = new Button(Question.getQuestionByNumber(1).getQuestion());
        Button thirdQ = new Button(Question.getQuestionByNumber(2).getQuestion());
        Button apply = new Button("Apply");
        TextField questionAnswer = new TextField("Enter your answer");

        // Set up a timeline for color animation
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> questionAnswer.setStyle("-fx-text-fill: red;")),
                new KeyFrame(Duration.millis(250), e -> questionAnswer.setStyle("-fx-text-fill: orange;")),
                new KeyFrame(Duration.millis(500), e -> questionAnswer.setStyle("-fx-text-fill: yellow;")),
                new KeyFrame(Duration.millis(750), e -> questionAnswer.setStyle("-fx-text-fill: green;")),
                new KeyFrame(Duration.millis(1000), e -> questionAnswer.setStyle("-fx-text-fill: blue;"))
        );
        timeline.setCycleCount(Animation.INDEFINITE); // Repeat indefinitely

        // Add listener to start/stop animation based on focus
        questionAnswer.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                timeline.play();
            } else {
                timeline.stop();
                questionAnswer.setStyle(""); // Reset style when not focused
            }
        });

        // Create a VBox and add buttons and text field to it
        VBox vbox = new VBox(30); // VBox with 10px spacing
        vbox.getChildren().addAll(firstQ, secondQ, thirdQ, questionAnswer, apply);

        vbox.setAlignment(Pos.CENTER);
        firstQ.setOnMouseEntered(e -> animateButton(firstQ, 1.1));
        firstQ.setOnMouseExited(e -> animateButton(firstQ, 1.0));

        secondQ.setOnMouseEntered(e -> animateButton(secondQ, 1.1));
        secondQ.setOnMouseExited(e -> animateButton(secondQ, 1.0));

        thirdQ.setOnMouseEntered(e -> animateButton(thirdQ, 1.1));
        thirdQ.setOnMouseExited(e -> animateButton(thirdQ, 1.0));

        apply.setOnMouseEntered(e -> animateButton(apply, 1.1));
        apply.setOnMouseExited(e -> animateButton(apply, 1.0));
        // Button actions
        firstQ.setOnAction(event -> {
            questionAnswer.setPromptText(Question.getQuestionByNumber(0).getQuestion());
        });

        secondQ.setOnAction(event -> {
            questionAnswer.setPromptText(Question.getQuestionByNumber(1).getQuestion());
        });

        thirdQ.setOnAction(event -> {
            questionAnswer.setPromptText(Question.getQuestionByNumber(2).getQuestion());
        });

        // Apply button action
        apply.setOnAction(event -> {
            String answer = questionAnswer.getText();
            int selectedQuestionIndex = -1;

            if (firstQ.getText().equals(questionAnswer.getPromptText())) {
                selectedQuestionIndex = 0;
            } else if (secondQ.getText().equals(questionAnswer.getPromptText())) {
                selectedQuestionIndex = 1;
            } else if (thirdQ.getText().equals(questionAnswer.getPromptText())) {
                selectedQuestionIndex = 2;
            }

            if (selectedQuestionIndex != -1) {
                Question registeringUserQuestion = new Question(Question.getQuestionByNumber(selectedQuestionIndex).getQuestion(), answer);
                User.registeringUser.setQuestion(registeringUserQuestion);

                // Remove components from root
                root.getChildren().removeAll(imageView, vbox);

                // Additional action (optional)
                System.out.println(User.registeringUser.getQuestion().getQuestion());
                System.out.println(User.registeringUser.getQuestion().getAnswer());
                App.getStage().setFullScreen(true); // Set full screen
            }
        });
        questionAnswer.setPrefWidth(400);
        // Add VBox to root
        root.getChildren().add(vbox);
    }




    public void firstQ() {
        Question.setQuestionNumberForRegistration(0);
    }

    public void secondQ() {
        Question.setQuestionNumberForRegistration(1);
    }

    public void thirdQ() {
        Question.setQuestionNumberForRegistration(2);
    }

    public String acceptRandomPassword(String password) {
        password = yourRandomPassword.getText();
        /**
         * this function show a scene with password made
         * and get accept or user back to menu or request another random password
         * @param string: a random password
         * @return if accept : "accept" if request another password : "again" if back to menu : "back"
         */
        return null; // delete this and write code
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
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(5));
        pauseTransition.setOnFinished(actionEvent -> {
//            videoStage.close();
            root.getChildren().remove(mediaView);
        });
        pauseTransition.play();
    }

    public void userCreatedSuccessfullyVideoPlay(StackPane root) {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/userCreatedSuccessfully.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Create a new stage (window) for the video
//        Stage videoStage = new Stage();
//        StackPane root = new StackPane();
        root.getChildren().add(mediaView);
//        Scene scene = new Scene(root, 600, 400); // Set the width and height as needed
//        videoStage.setMinWidth(600);
//        videoStage.setMinHeight(400);
//        videoStage.setMaxWidth(600);
//        videoStage.setMaxHeight(400);
//        videoStage.setScene(scene);
//        videoStage.setTitle("user Created Successfully");
//        videoStage.show();
//
//        // Play the video
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

//        // Create a new stage (window) for the video
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

    public void wrongEmailFormatVideoPlay(StackPane root) {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/wrongEmailFormat.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

//        // Create a new stage (window) for the video
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
//
//        // Play the video
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(4));
        pauseTransition.setOnFinished(actionEvent -> {
//            videoStage.close();
            root.getChildren().remove(mediaView);
        });
        pauseTransition.play();
    }

    public void wrongPasswordVideoPlay(StackPane root) {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/wrongPassword.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

//        // Create a new stage (window) for the video
//        Stage videoStage = new Stage();
//        StackPane root = new StackPane();
        root.getChildren().add(mediaView);
//        Scene scene = new Scene(root, 800, 500); // Set the width and height as needed
//        videoStage.setMinWidth(800);
//        videoStage.setMinHeight(500);
//        videoStage.setMaxWidth(800);
//        videoStage.setMaxHeight(500);
//        videoStage.setScene(scene);
//        videoStage.setTitle("wrong Password");
//        videoStage.show();
//
//        // Play the video
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(6));
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

//        // Create a new stage (window) for the video
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
//
//        // Play the video
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

    public void confirmPasswordFailedVideoPlay(StackPane root) {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/confirmPasswordFailed.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

//        // Create a new stage (window) for the video
//        Stage videoStage = new Stage();
//        StackPane root = new StackPane();
        root.getChildren().add(mediaView);
//        Scene scene = new Scene(root, 800, 500); // Set the width and height as needed
//        videoStage.setMinWidth(800);
//        videoStage.setMinHeight(500);
//        videoStage.setMaxWidth(800);
//        videoStage.setMaxHeight(500);
//        videoStage.setScene(scene);
//        videoStage.setTitle("confirm Password Failed");
//        videoStage.show();
//
//        // Play the video
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(6));
        pauseTransition.setOnFinished(actionEvent -> {
//            videoStage.close();
            root.getChildren().remove(mediaView);
        });
        pauseTransition.play();
    }

    public void loggedInSuccessfullyVideoPlay() throws Exception {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/loggedInSuccessfully.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

//        // Create a new stage (window) for the video
        Stage videoStage = new Stage();
        StackPane root = new StackPane();
        root.getChildren().add(mediaView);
        Scene scene = new Scene(root, 800, 500); // Set the width and height as needed
        videoStage.setMinWidth(800);
        videoStage.setMinHeight(500);
        videoStage.setMaxWidth(800);
        videoStage.setMaxHeight(500);
        videoStage.setScene(scene);
        videoStage.setTitle("logged In Successfully");
        videoStage.show();

        // Play the video
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(4));
        pauseTransition.setOnFinished(actionEvent -> {
            videoStage.close();
//            root.getChildren().remove(mediaView);
            MainMenu mainMenu = new MainMenu();
            try {
                mainMenu.start(App.getStage());
                App.getStage().setFullScreen(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        pauseTransition.play();
    }

    public void noSuchUserExistVideoPlay(StackPane root) {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/noSuchUserExist.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

//        // Create a new stage (window) for the video
//        Stage videoStage = new Stage();
//        StackPane root = new StackPane();
        root.getChildren().add(mediaView);
//        Scene scene = new Scene(root, 800, 500); // Set the width and height as needed
//        videoStage.setMinWidth(800);
//        videoStage.setMinHeight(500);
//        videoStage.setMaxWidth(800);
//        videoStage.setMaxHeight(500);
//        videoStage.setScene(scene);
//        videoStage.setTitle("no Such User Exist");
//        videoStage.show();
//
//        // Play the video
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/witcherMenu.mp4")).toExternalForm();
        loginVideo = new Media(videoPath);
    }
}
