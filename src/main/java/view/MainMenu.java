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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;


public class MainMenu extends Application {
    //    @FXML
//    Button exitPointChart;
//    @FXML
//    public ScrollPane scrollPaneRanking;
//    @FXML
//    public TilePane tilePaneRanking;
    @FXML
    Button start;
    @FXML
    Button profile;
    @FXML
    Button pointChart;
    @FXML
    Button logout;
    StackPane root;

    PreGameMenu preGameMenu;

    Game onlineGame;
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
        root = new StackPane();
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
        logout = (Button) scene.lookup("#logout");

        logout.setOnMouseEntered(e -> animateButton(logout, 1.1));
        logout.setOnMouseExited(e -> animateButton(logout, 1.0));

        start.setOnMouseEntered(e -> animateButton(start, 1.1));
        start.setOnMouseExited(e -> animateButton(start, 1.0));

        profile.setOnMouseEntered(e -> animateButton(profile, 1.1));
        profile.setOnMouseExited(e -> animateButton(profile, 1.0));

        pointChart.setOnMouseEntered(e -> animateButton(pointChart, 1.1));
        pointChart.setOnMouseExited(e -> animateButton(pointChart, 1.0));

        logout.setOnMouseClicked(event -> {
            logout(stage);
        });

        start.setOnMouseClicked(mouseEvent -> {
            toGame(stage);
        });
        profile.setOnMouseClicked(mouseEvent -> {
            toProfile(stage);
        });
        pointChart.setOnMouseClicked(mouseEvent -> {
            try {
                pointChart(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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

    public void logout(Stage stage) {
        LoginMenu loginMenu = new LoginMenu();
        try {
            loginMenu.start(App.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        User.setLoggedInUser(null);
        stage.setFullScreen(true);
    }

    public void toGame(Stage stage) {
        TextField chosenUsername = new TextField("write enemy username");
        chosenUsername.setAlignment(Pos.CENTER);
        chosenUsername.setOnMouseClicked(mouseEvent -> {
            if (chosenUsername.getText().equals("write enemy username"))
                chosenUsername.setText("");
        });
        Button button = new Button("let's go");
        VBox vBox = new VBox(chosenUsername, button);
        vBox.setMaxWidth(500);
        vBox.setAlignment(Pos.CENTER);
        root.getChildren().add(vBox);
        initialize();
        button.setOnMouseClicked(mouseEvent -> {
            App.getGameClient().sendMessage("{request game(username<"+chosenUsername.getText()+">)}");
//            PreGameMenu preGameMenu = new PreGameMenu();
//            try {
//                preGameMenu.start(App.getStage());
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//            stage.setFullScreen(true);
        });
    }

    private void initialize() {
        DeckUnit deckUnit = new DeckUnit();
        DeckUnit deckUnit2 = new DeckUnit();
        PlayBoard currentPlayBoard = new PlayBoard();
        currentPlayBoard.setCloseCombatUnit(new CloseCombatUnit());
        currentPlayBoard.setDiscardPileUnit(new DiscardPileUnit());
        currentPlayBoard.setRangedCombatUnit(new RangedCombatUnit());
        currentPlayBoard.setSiegeUnit(new SiegeUnit());
        currentPlayBoard.setHandUnit(new HandUnit());
        PlayBoard next = new PlayBoard();
        next.setCloseCombatUnit(new CloseCombatUnit());
        next.setDiscardPileUnit(new DiscardPileUnit());
        next.setRangedCombatUnit(new RangedCombatUnit());
        next.setSiegeUnit(new SiegeUnit());
        next.setHandUnit(new HandUnit());
        User.getLoggedInUser().setPlayBoard(currentPlayBoard);
        User.getLoggedInUser().getPlayBoard().setDeckUnit(deckUnit);
        User enemy = new User("a", "a", "a", "a"); //TODO: change it
        enemy.setPlayBoard(next);
        enemy.getPlayBoard().setDeckUnit(deckUnit2);
        Game.setCurrentGame(new Game(User.getLoggedInUser(), enemy, LocalDateTime.now()));//TODO: change date
        Game.getCurrentGame().setSpellUnit(new SpellUnit());
        Game.getCurrentGame().setCurrentUser(User.getLoggedInUser());
        Game.getCurrentGame().setNextUser(enemy);
        Game.getCurrentGame().setMe(User.getLoggedInUser());
        Game.getCurrentGame().setEnemy(enemy);
        onlineGame = Game.getCurrentGame();
    }

    public void pointChart(StackPane root) throws IOException {
        // Load the image
        String imagePathOIP = "/pics/OIP.jpg"; // Change this to the path of your image file
        Image imageOIP = new Image(getClass().getResource(imagePathOIP).toExternalForm());
        ImageView imageViewOIP = new ImageView(imageOIP);
        root.getChildren().add(imageViewOIP);

        Button exitPointChart = new Button("exit point chart");
        TilePane tilePaneRanking = new TilePane();

        exitPointChart.setOnMouseEntered(e -> animateButton(exitPointChart, 1.1));
        exitPointChart.setOnMouseExited(e -> animateButton(exitPointChart, 1.0));

        VBox vbox = new VBox(30); // VBox with 30px spacing
        vbox.getChildren().addAll(exitPointChart, tilePaneRanking);

        vbox.setAlignment(Pos.CENTER);

        try {
            String cssPath = Objects.requireNonNull(LoginMenu.class.getResource("/styles/style.css")).toExternalForm();
            tilePaneRanking.getStylesheets().add(cssPath);
        } catch (NullPointerException t) {
            System.out.println("CSS file not found.");
        }

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
                String imagePath = Objects.requireNonNull(getClass().getResource("/pics/gold.png")).toExternalForm();
                Image image = new Image(imagePath);
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(55);
                imageView.setFitWidth(50);
                userBox.getChildren().add(imageView);
            } else if (i == 1) {
                String imagePath = Objects.requireNonNull(getClass().getResource("/pics/silver.png")).toExternalForm();
                Image image = new Image(imagePath);
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(55);
                imageView.setFitWidth(50);
                userBox.getChildren().add(imageView);
            } else if (i == 2) {
                String imagePath = Objects.requireNonNull(getClass().getResource("/pics/bronze.png")).toExternalForm();
                Image image = new Image(imagePath);
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(55);
                imageView.setFitWidth(50);
                userBox.getChildren().add(imageView);
            }

            userBox.setOnMouseEntered(e -> animateBox(userBox, 1.1));
            userBox.setOnMouseExited(e -> animateBox(userBox, 1.0));
            userBox.getChildren().addAll(rankLabel, scoreLabel);
            tilePaneRanking.getChildren().add(userBox);
            i++;
        }

        exitPointChart.setOnMouseClicked(mouseEvent -> {
            root.getChildren().remove(imageViewOIP);
            root.getChildren().remove(vbox);
        });

        root.getChildren().add(vbox);
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
    public void showNotAccept() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("user not online or not exist");
        alert.show();
    }

    public void alertRequest(String message) {
        Button reject = new Button("reject");
        Button accept = new Button("accept");
        Text text = new Text(message);
        text.setFill(Color.GOLD);
        VBox vBox = new VBox(text, accept, reject);
        vBox.setMaxWidth(500);
        vBox.setAlignment(Pos.CENTER);
        root.getChildren().add(vBox);
        reject.setOnMouseClicked(mouseEvent -> {
            App.getGameClient().sendMessage("reject");
            root.getChildren().remove(vBox);
        });
        accept.setOnMouseClicked(mouseEvent -> {
            App.getGameClient().sendMessage("accept");
            root.getChildren().remove(vBox);
            preGameMenu = new PreGameMenu();
            try {
                this.stop();
                initialize();
                preGameMenu.start(App.getStage());
            } catch (Exception e){
                e.printStackTrace();
            }
        });

    }

    public void alertReject() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("reject");
        alert.show();
    }

    public void acceptGame() {
        preGameMenu = new PreGameMenu();
        try {
            this.stop();
            initialize();
            preGameMenu.start(App.getStage());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public PreGameMenu getPreGameMenu() {
        return preGameMenu;
    }

    public Game getOnlineGame() {
        return onlineGame;
    }
}
