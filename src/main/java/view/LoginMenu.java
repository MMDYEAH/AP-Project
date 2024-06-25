package view;

import controller.LoginMenuController;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.App;
import model.Question;
import model.Result;
import model.User;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginMenu extends Application implements Initializable {
    @FXML
    private Hyperlink firstQ;
    @FXML
    private Hyperlink secondQ;
    @FXML
    private Hyperlink thirdQ;
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
    //    @FXML
//    private ComboBox<String> questionCombo;
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
        password = (PasswordField) scene.lookup("#passwordConfirm");
        nickname = (TextField) scene.lookup("#nickname");
        email = (TextField) scene.lookup("#email");
        yourRandomPassword = (Text) scene.lookup("#yourRandomPassword");
        login = (Button) scene.lookup("#login");

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

    public void login() throws Exception {
        Result result = controller.login(username.getText(), password.getText());
        if (result.isSuccessful()) {
            User.setLoggedInUser(User.registeringUser);
            loggedInSuccessfullyVideoPlay();
        } else if (result.toString().equals("wrong password")) {
            wrongPasswordVideoPlay();
        } else if (result.toString().equals("no such user exist")) {
            noSuchUserExistVideoPlay();
        } else {
            System.out.println(result);
        }
    }

    public void signUp() throws IOException {
        Result result = controller.register(username.getText(), password.getText(), passwordConfirm.getText(), nickname.getText(), email.getText(), null);
        System.out.println(result);
        if (result.isSuccessful()) {
            securityQuestion();
        } else if (result.toString().equals("empty field")) {
            emptyFieldVideoPlay();
        } else if (result.toString().equals("there is exist an user with this username")) {
            thereIsExistUserWithUsernameVideoPlay();
        } else if (result.toString().equals("wrong username format")) {
            wrongUsernameFormatVideoPlay();
        } else if (result.toString().equals("wrong email format")) {
            wrongEmailFormatVideoPlay();
        } else if (result.toString().equals("wrong password format")) {
            wrongPasswordFormatVideoPlay();
        } else if (result.toString().equals("weak password")) {
            weakPasswordVideoPlay();
        } else if (result.toString().equals("user created successfully")) {
            userCreatedSuccessfullyVideoPlay();
        } else if (result.toString().equals("wrong password")) {
            wrongPasswordVideoPlay();
        } else if (result.toString().equals("confirm password failed")) {
            confirmPasswordFailedVideoPlay();
        }
    }

    public void randomPasswordSet() {
        yourRandomPassword.setText(controller.makeRandomPassword());
    }

    public void chooseRandomPassword() {
        password.setText(yourRandomPassword.getText());
        passwordConfirm.setText(yourRandomPassword.getText());
    }

    public void securityQuestion() throws IOException {

//        String questionAnswerChoose = new String();

        FXMLLoader fxmlLoader = new FXMLLoader(LoginMenu.class.getResource("/SecurityQuestion.fxml"));
        Pane paneSecurity = fxmlLoader.load();
        // Create a Scene with the layout
        Scene securityScene = new Scene(paneSecurity, 1000, 800);

        // Create a new Stage
        Stage securityStage = new Stage();
        // Set the Scene on the Stage
        securityStage.setScene(securityScene);
        try {
            String cssPath = Objects.requireNonNull(LoginMenu.class.getResource("/styles/style.css")).toExternalForm();
            securityScene.getStylesheets().add(cssPath); // Adding the CSS file
        } catch (NullPointerException t) {
            System.out.println("CSS file not found.");
        }
        // Set the modality of the new Stage to block events from the main Stage
        securityStage.initModality(Modality.APPLICATION_MODAL);
        securityStage.setMinWidth(1000);
        securityStage.setMinHeight(800);
        securityStage.setMaxWidth(1000);
        securityStage.setMaxHeight(800);
        firstQ = (Hyperlink) securityScene.lookup("#firstQ");
        secondQ = (Hyperlink) securityScene.lookup("#secondQ");
        thirdQ = (Hyperlink) securityScene.lookup("#thirdQ");
        apply = (Button) securityScene.lookup("#apply");

        firstQ.setText(Question.getQuestionByNumber(0).getQuestion());
        secondQ.setText(Question.getQuestionByNumber(1).getQuestion());
        thirdQ.setText(Question.getQuestionByNumber(2).getQuestion());
        questionAnswer = (TextField) securityScene.lookup("#questionAnswer");

        Question question = Question.getQuestionByNumber(Question.getQuestionNumberForRegistration());
        question.setAnswer(questionAnswer.getText());
        User.registeringUser.setQuestion(question);

        // Set the background image
        Image backgroundImage = new Image(getClass().getResource("/pics/security.jpg").toExternalForm());
        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        paneSecurity.setBackground(new Background(background));

        apply.setOnMouseClicked(mouseEvent -> {
            Question registeringUserQuestion = new Question(Question.getQuestionByNumber(Question.getQuestionNumberForRegistration()).getQuestion(), questionAnswer.getText());
            User.registeringUser.setQuestion(registeringUserQuestion);
            System.out.println(User.registeringUser.getQuestion().getQuestion());
            System.out.println(User.registeringUser.getQuestion().getAnswer());
            securityStage.close();
//            questionAnswerChoose = questionAnswer.getText();
        });

        System.out.println(User.registeringUser.getUsername());
        System.out.println(User.registeringUser.getNickname());
        System.out.println(User.registeringUser.getPassword());
        System.out.println(User.registeringUser.getEmail());


        // Set the title of the Stage
        securityStage.setTitle("Security Question");

        // Show the Stage
        securityStage.show();

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

    public void userCreatedSuccessfullyVideoPlay() {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/userCreatedSuccessfully.mp4").toExternalForm());
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Create a new stage (window) for the video
        Stage videoStage = new Stage();
        StackPane root = new StackPane();
        root.getChildren().add(mediaView);
        Scene scene = new Scene(root, 600, 400); // Set the width and height as needed
        videoStage.setMinWidth(600);
        videoStage.setMinHeight(400);
        videoStage.setMaxWidth(600);
        videoStage.setMaxHeight(400);
        videoStage.setScene(scene);
        videoStage.setTitle("user Created Successfully");
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

    public void wrongPasswordVideoPlay() {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/wrongPassword.mp4").toExternalForm());
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
        videoStage.setTitle("wrong Password");
        videoStage.show();

        // Play the video
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(6));
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

    public void confirmPasswordFailedVideoPlay() {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/confirmPasswordFailed.mp4").toExternalForm());
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
        videoStage.setTitle("confirm Password Failed");
        videoStage.show();

        // Play the video
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(6));
        pauseTransition.setOnFinished(actionEvent -> {
            videoStage.close();
        });
        pauseTransition.play();
    }

    public void loggedInSuccessfullyVideoPlay() throws Exception {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/loggedInSuccessfully.mp4").toExternalForm());
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
        videoStage.setTitle("logged In Successfully");
        videoStage.show();

        // Play the video
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(4));
        pauseTransition.setOnFinished(actionEvent -> {
            videoStage.close();
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

    public void noSuchUserExistVideoPlay() {
        // Path to your video file
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/noSuchUserExist.mp4").toExternalForm());
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
        videoStage.setTitle("no Such User Exist");
        videoStage.show();

        // Play the video
        mediaPlayer.play();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(6));
        pauseTransition.setOnFinished(actionEvent -> {
            videoStage.close();
        });
        pauseTransition.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/witcherMenu.mp4")).toExternalForm();
        loginVideo = new Media(videoPath);
    }
}
