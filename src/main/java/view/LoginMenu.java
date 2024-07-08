package view;

import controller.LoginMenuController;
import javafx.animation.*;
import javafx.application.Application;
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
import javafx.stage.Stage;
import javafx.util.Duration;
import model.App;
import model.Question;
import model.Result;
import model.User;
import network.GameClient;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


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
    //    @FXML
//    private TextField questionAnswer;
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
    @FXML
    Button forgotPassword;
    @FXML
    ImageView logoButton;
    static Media loginVideo;
    LoginMenuController controller = new LoginMenuController(this);
    StackPane root;
    GameClient gameClient;
    private String verificationCode;
    MainMenu mainMenu;
    Question registeringUserQuestion;

    public LoginMenu(GameClient gameServer) {
        this.gameClient = gameServer;
    }

    public LoginMenu() {

    }

    @Override
    public void start(Stage stage) throws Exception {
        //network:
        App.setGameClient(gameClient);
        //
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
        root = new StackPane();
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
        forgotPassword = (Button) scene.lookup("#forgotPassword");
        logoButton = (ImageView) scene.lookup("#logoButton");
        String logoPath = getClass().getResource("/pics/logo.png").toExternalForm();
        logoButton.setImage(new Image(logoPath));
//        logoButton.setOnMouseClicked(mouseEvent -> reloadVideo(pane));
        forgotPassword.setOnMouseClicked(mouseEvent -> {
            try {
                forgotPassword(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
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

        forgotPassword.setOnMouseEntered(e -> animateButton(forgotPassword, 1.1));
        forgotPassword.setOnMouseExited(e -> animateButton(forgotPassword, 1.0));

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
//                String emailSend = email.getText();
//                String sentCode = EmailVerification.sendVerificationCode(emailSend);
                signUp(root, "1");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        login.setOnMouseClicked(mouseEvent -> {
            try {
                login();
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

    public void login() {
        App.getGameClient().sendMessage("{login(username<" + username.getText() + ">)(password<" + password.getText() + ">)}");
    }

    public void signUp(StackPane root, String sentcode) throws IOException {
        securityQuestion(root, sentcode);
    }

    public void randomPasswordSet() {
        yourRandomPassword.setText(controller.makeRandomPassword());
        yourRandomPassword.setTextAlignment(TextAlignment.RIGHT);
    }

    public void chooseRandomPassword() {
        password.setText(yourRandomPassword.getText());
        passwordConfirm.setText(yourRandomPassword.getText());
    }

    public void securityQuestion(StackPane root, String sentCode) throws IOException {
        // Load the image
        String imagePath = "/pics/security.png"; // Change this to the path of your image file
        Image image = new Image(getClass().getResource(imagePath).toExternalForm());
        ImageView imageView = new ImageView(image);
        root.getChildren().add(imageView);

        // Create buttons and text field
        Button firstQ = new Button(Question.getQuestionByNumber(0).getQuestion());
        Button secondQ = new Button(Question.getQuestionByNumber(1).getQuestion());
        Button thirdQ = new Button(Question.getQuestionByNumber(2).getQuestion());
        Button apply = new Button("Apply");
        TextField questionAnswer = new TextField("");
        TextField verificationEmailedCode = new TextField("");
        questionAnswer.setPromptText("enter your answer");
        verificationEmailedCode.setPromptText("enter the code which is emailed to you.");

        // Set up a timeline for color animation
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> questionAnswer.setStyle("-fx-text-fill: red;")),
                new KeyFrame(Duration.millis(250), e -> questionAnswer.setStyle("-fx-text-fill: orange;")),
                new KeyFrame(Duration.millis(500), e -> questionAnswer.setStyle("-fx-text-fill: yellow;")),
                new KeyFrame(Duration.millis(750), e -> questionAnswer.setStyle("-fx-text-fill: green;")),
                new KeyFrame(Duration.millis(1000), e -> questionAnswer.setStyle("-fx-text-fill: blue;"))
        );
        timeline.setCycleCount(Animation.INDEFINITE); // Repeat indefinitely
// Set up a timeline for color animation
        Timeline timeline1 = new Timeline(
                new KeyFrame(Duration.ZERO, e -> verificationEmailedCode.setStyle("-fx-text-fill: red;")),
                new KeyFrame(Duration.millis(250), e -> verificationEmailedCode.setStyle("-fx-text-fill: orange;")),
                new KeyFrame(Duration.millis(500), e -> verificationEmailedCode.setStyle("-fx-text-fill: yellow;")),
                new KeyFrame(Duration.millis(750), e -> verificationEmailedCode.setStyle("-fx-text-fill: green;")),
                new KeyFrame(Duration.millis(1000), e -> verificationEmailedCode.setStyle("-fx-text-fill: blue;"))
        );
        timeline1.setCycleCount(Animation.INDEFINITE); // Repeat indefinitely
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
        vbox.getChildren().addAll(firstQ, secondQ, thirdQ, questionAnswer, verificationEmailedCode, apply);
        vbox.setMaxWidth(500);

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

            if (sentCode != null) {
                verificationCode = sentCode;
                if (verificationEmailedCode.getText().equals(verificationCode)) {

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
                        registeringUserQuestion = new Question(Question.getQuestionByNumber(selectedQuestionIndex).getQuestion(), answer);
                        App.getGameClient().sendMessage("{register(username<" + username.getText() + ">)(password<" + password.getText() + ">)(confirm<" + passwordConfirm.getText() + ">)(nickname<" + nickname.getText() + ">)(email<" + email.getText() + ">)" + registeringUserQuestion.toJson() + "}");
//                User.registeringUser.setQuestion(registeringUserQuestion);

                        // Remove components from root
                        root.getChildren().removeAll(imageView, vbox);

                        // Additional action (optional)
//                System.out.println(User.registeringUser.getQuestion().getQuestion());
//                System.out.println(User.registeringUser.getQuestion().getAnswer());
                        App.getStage().setFullScreen(true); // Set full screen
                    }
                } else {
                    String videoPath = Objects.requireNonNull(getClass().getResource("/videos/wrongVerificationCode.mp4").toExternalForm());
                    Media media = new Media(videoPath);
                    MediaPlayer mediaPlayer = new MediaPlayer(media);
                    MediaView mediaView = new MediaView(mediaPlayer);
                    root.getChildren().add(mediaView);
                    // Play the video
                    mediaPlayer.play();
                    PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
                    pauseTransition.setOnFinished(actionEvent -> {
                        root.getChildren().remove(mediaView);
                    });
                    pauseTransition.play();
                }
            } else {
                // Handle email sending failure
                System.out.println("Failed to send verification email");
            }

        });
        questionAnswer.setPrefWidth(400);
        // Add VBox to root
        root.getChildren().add(vbox);
    }

    public void forgotPassword(StackPane root) throws IOException {
        // Load the image
        String imagePath = "/pics/forgotPassword.jpg"; // Change this to the path of your image file
        Image image = new Image(getClass().getResource(imagePath).toExternalForm());
        ImageView imageView = new ImageView(image);
        root.getChildren().add(imageView);
        TextField usernameOfForgot = new TextField("");
        usernameOfForgot.setPromptText("enter your username");
        Button submit = new Button("submit");
        Button close = new Button("close");
        submit.setOnMouseEntered(e -> animateButton(submit, 1.1));
        submit.setOnMouseExited(e -> animateButton(submit, 1.0));

        close.setOnMouseEntered(e -> animateButton(close, 1.1));
        close.setOnMouseExited(e -> animateButton(close, 1.0));
        // Create a VBox and add buttons and text field to it
        VBox vbox = new VBox(30); // VBox with 10px spacing
        vbox.setMaxWidth(500);
        vbox.getChildren().addAll(usernameOfForgot, submit, close);
        vbox.setAlignment(Pos.CENTER);
        close.setOnMouseClicked(mouseEvent -> {
            root.getChildren().removeAll(imageView, vbox);
        });
        submit.setOnMouseClicked(mouseEvent -> {
            if (User.getUserByUsername(usernameOfForgot.getText()) != null) {
                // Create buttons and text field
                Button chosenQuestion = new Button(User.getUserByUsername(username.getText()).getQuestion().getQuestion());
                TextField questionAnswer = new TextField("");
                Button viewPassword = new Button("view password");
                viewPassword.setOnMouseEntered(e -> animateButton(viewPassword, 1.1));
                viewPassword.setOnMouseExited(e -> animateButton(viewPassword, 1.0));
                chosenQuestion.setOnMouseEntered(e -> animateButton(chosenQuestion, 1.1));
                chosenQuestion.setOnMouseExited(e -> animateButton(chosenQuestion, 1.0));

                // Create a VBox and add buttons and text field to it
                vbox.getChildren().removeAll(usernameOfForgot, submit);
                vbox.getChildren().addAll(chosenQuestion, questionAnswer, viewPassword);
                vbox.setAlignment(Pos.CENTER);
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
                viewPassword.setOnMouseClicked(mouseEvent1 -> {
                    if (questionAnswer.getText().equals(User.getUserByUsername(username.getText()).getQuestion().getAnswer())) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("your password");
                        alert.setContentText(User.getUserByUsername(username.getText()).getPassword());
                        alert.show();
                        root.getChildren().removeAll(imageView, vbox);
                    } else {
                        wrongAnswerVideoPlay(root);
                    }
                });
            } else {
                noUserWithThisUsernameVideoPlay(root);
            }
        });
        // Set up a timeline for color animation
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> usernameOfForgot.setStyle("-fx-text-fill: red;")),
                new KeyFrame(Duration.millis(250), e -> usernameOfForgot.setStyle("-fx-text-fill: orange;")),
                new KeyFrame(Duration.millis(500), e -> usernameOfForgot.setStyle("-fx-text-fill: yellow;")),
                new KeyFrame(Duration.millis(750), e -> usernameOfForgot.setStyle("-fx-text-fill: green;")),
                new KeyFrame(Duration.millis(1000), e -> usernameOfForgot.setStyle("-fx-text-fill: blue;"))
        );
        timeline.setCycleCount(Animation.INDEFINITE); // Repeat indefinitely

        // Add listener to start/stop animation based on focus
        usernameOfForgot.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                timeline.play();
            } else {
                timeline.stop();
                usernameOfForgot.setStyle(""); // Reset style when not focused
            }
        });
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

    public void emptyFieldVideoPlay() {
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/emptyField.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        root.getChildren().add(mediaView);
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(4));
        pauseTransition.setOnFinished(actionEvent -> {
            root.getChildren().remove(mediaView);
        });
        pauseTransition.play();
    }

    public void thereIsExistUserWithUsernameVideoPlay() {
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/thereIsExistUserWithUsername.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        root.getChildren().add(mediaView);
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(5));
        pauseTransition.setOnFinished(actionEvent -> {
            root.getChildren().remove(mediaView);
        });
        pauseTransition.play();
    }

    public void userCreatedSuccessfullyVideoPlay() {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/userCreatedSuccessfully.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        root.getChildren().add(mediaView);
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(4));
        pauseTransition.setOnFinished(actionEvent -> {
            root.getChildren().remove(mediaView);
        });
        pauseTransition.play();
    }

    public void weakPasswordVideoPlay() {
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/weakPassword.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        root.getChildren().add(mediaView);
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(6));
        pauseTransition.setOnFinished(actionEvent -> {
            root.getChildren().remove(mediaView);
        });
        pauseTransition.play();
    }

    public void wrongEmailFormatVideoPlay() {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/wrongEmailFormat.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        root.getChildren().add(mediaView);
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(4));
        pauseTransition.setOnFinished(actionEvent -> {
            root.getChildren().remove(mediaView);
        });
        pauseTransition.play();
    }

    public void wrongPasswordVideoPlay() {
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/wrongPassword.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        root.getChildren().add(mediaView);
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(6));
        pauseTransition.setOnFinished(actionEvent -> {
            root.getChildren().remove(mediaView);
        });
        pauseTransition.play();
    }

    public void wrongPasswordFormatVideoPlay() {
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/wrongPasswordFormat.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        root.getChildren().add(mediaView);
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(4));
        pauseTransition.setOnFinished(actionEvent -> {
            root.getChildren().remove(mediaView);
        });
        pauseTransition.play();
    }

    public void wrongUsernameFormatVideoPlay() {
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/wrongUsernameFormat.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        root.getChildren().add(mediaView);
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(4));
        pauseTransition.setOnFinished(actionEvent -> {
            root.getChildren().remove(mediaView);
        });
        pauseTransition.play();
    }

    public void confirmPasswordFailedVideoPlay() {
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/confirmPasswordFailed.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        root.getChildren().add(mediaView);
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(6));
        pauseTransition.setOnFinished(actionEvent -> {
            root.getChildren().remove(mediaView);
        });
        pauseTransition.play();
    }

    public void loggedInSuccessfullyVideoPlay() {
        User user = new User(username.getText(), password.getText(), nickname.getText(), email.getText());
        user.setQuestion(registeringUserQuestion);
        User.setLoggedInUser(user);
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/loggedInSuccessfully.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        Stage videoStage = new Stage();
        Image logo = new Image(getClass().getResourceAsStream("/pics/logo.png"));
        videoStage.getIcons().add(logo);
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
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(4));
        pauseTransition.setOnFinished(actionEvent -> {
            videoStage.close();
            mainMenu = new MainMenu();
            try {
                mainMenu.start(App.getStage());
                App.getStage().setFullScreen(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        pauseTransition.play();
    }

    public void noSuchUserExistVideoPlay() {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/noSuchUserExist.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        root.getChildren().add(mediaView);
        // Play the video
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(6));
        pauseTransition.setOnFinished(actionEvent -> {
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

    public void noUserWithThisUsernameVideoPlay(StackPane root) {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/userDoesNotExist.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        root.getChildren().add(mediaView);
        // Play the video
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
        pauseTransition.setOnFinished(actionEvent -> {
            root.getChildren().remove(mediaView);
        });
        pauseTransition.play();
    }

    public void wrongAnswerVideoPlay(StackPane root) {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/wrongAnswer.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        root.getChildren().add(mediaView);
        // Play the video
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
        pauseTransition.setOnFinished(actionEvent -> {
            root.getChildren().remove(mediaView);
        });
        pauseTransition.play();
    }


    public class EmailVerification {

        private static final String FROM_EMAIL = "mmdgwentverift@outlook.com";
        private static final String APP_PASSWORD = "liobggtullouibvp";  // Use the generated app password here
        static String sentCode;

        public static String sendVerificationCode(String toEmail) {
            String code = generateVerificationCode();

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");  // Enable TLS
            props.put("mail.smtp.host", "smtp.office365.com");
            props.put("mail.smtp.port", "587");  // TLS port
            props.put("mail.smtp.ssl.trust", "smtp.office365.com");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
                }
            });

            session.setDebug(true);  // Enable debugging

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(FROM_EMAIL));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
                message.setSubject("Your Verification Code");
                message.setText("Your verification code is: " + code);

                Transport.send(message);

                System.out.println("Verification code sent successfully to " + toEmail);
                return code;
            } catch (MessagingException e) {
                e.printStackTrace();
                System.out.println("Failed to send verification code: " + e.getMessage());
                return null;
            }
        }

        private static String generateVerificationCode() {
            Random rnd = new Random();
            int number = rnd.nextInt(999999);
            return String.format("%06d", number);
        }

        public static String getSentCode() {
            return sentCode;
        }

        public static void setSentCode(String sentCode) {
            EmailVerification.sentCode = sentCode;
        }
    }


    public MainMenu getMainMenu() {
        return mainMenu;
    }
}

