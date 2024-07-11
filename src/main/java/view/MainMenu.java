package view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import controller.LoginMenuController;
import controller.MainMenuController;
import javafx.animation.*;
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
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


public class MainMenu extends Application {
    public Button eliminationCup;

    public Button friendRequest;
    public Button randomGame;
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
    FriendRequestMenu friendRequestMenu;
    TilePane tilePaneRanking;

    Game onlineGame;

//    MainMenuController controller = new MainMenuController(this);

    @Override
    public void start(Stage stage) throws Exception {
        stage.setFullScreen(true);
        App.setStage(stage);
        App.getGameClient().getLoginMenu().setMainMenu(this);
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
            String cssPath = Objects.requireNonNull(LoginMenu.class.getResource("/styles/mainMenu.css")).toExternalForm();
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
        friendRequest = (Button) scene.lookup("#friendRequest");
        logout = (Button) scene.lookup("#logout");
        randomGame = (Button) scene.lookup("#randomGame");
        eliminationCup = (Button) scene.lookup("#eliminationCup");

        eliminationCup.setOnMouseEntered(e -> animateButton(eliminationCup, 1.1));
        eliminationCup.setOnMouseExited(e -> animateButton(eliminationCup, 1.0));

        randomGame.setOnMouseEntered(e -> animateButton(randomGame, 1.1));
        randomGame.setOnMouseExited(e -> animateButton(randomGame, 1.0));

        logout.setOnMouseEntered(e -> animateButton(logout, 1.1));
        logout.setOnMouseExited(e -> animateButton(logout, 1.0));

        start.setOnMouseEntered(e -> animateButton(start, 1.1));
        start.setOnMouseExited(e -> animateButton(start, 1.0));

        profile.setOnMouseEntered(e -> animateButton(profile, 1.1));
        profile.setOnMouseExited(e -> animateButton(profile, 1.0));

        pointChart.setOnMouseEntered(e -> animateButton(pointChart, 1.1));
        pointChart.setOnMouseExited(e -> animateButton(pointChart, 1.0));

        friendRequest.setOnMouseEntered(e -> animateButton(friendRequest, 1.1));
        friendRequest.setOnMouseExited(e -> animateButton(friendRequest, 1.0));

        eliminationCup.setOnMouseClicked(event -> {
            toEliminationCup(stage);
        });

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
            App.getGameClient().sendMessage("show ranking");
        });
        friendRequest.setOnMouseClicked(mouseEvent -> {
            Stage stage1 = new Stage();
            Image logo = new Image(getClass().getResourceAsStream("/pics/logo.png"));
            stage1.getIcons().add(logo);
            friendRequestMenu = new FriendRequestMenu();
            try {
                friendRequestMenu.start(stage1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        randomGame.setOnMouseClicked(mouseEvent -> {
            waitForRandomGame();
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

    private void waitForRandomGame() {
        App.getGameClient().sendMessage("random game");
        Stage stage = new Stage();
        Image logo = new Image(getClass().getResourceAsStream("/pics/logo.png"));
        stage.getIcons().add(logo);
        Pane pane = new Pane();
        Text text = new Text("wait for another player");
        Button cancel = new Button("cancel");
        VBox vBox = new VBox(text, cancel);
        pane.getChildren().add(vBox);
        cancel.setOnMouseClicked(mouseEvent -> {
            App.getGameClient().sendMessage("cancel random game");
            stage.close();
        });
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
        pauseTransition.setOnFinished(actionEvent -> stage.close());
        pauseTransition.play();
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
    public void toEliminationCup(Stage stage) {
        EliminationCup eliminationCup1 = new EliminationCup();
        try {
            eliminationCup1.start(App.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        stage.setFullScreen(true);
    }

    public void logout(Stage stage) {
        App.getGameClient().sendMessage("logout");
        User.setLoggedInUser(null);
        LoginMenu loginMenu = new LoginMenu(App.getGameClient());
        try {
            this.stop();
            loginMenu.start(App.getStage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        stage.setFullScreen(true);
    }

    public void toGame(Stage stage) {
        // Load the image
        String imagePath = "/pics/startGame.png"; // Change this to the path of your image file
        Image image = new Image(getClass().getResource(imagePath).toExternalForm());
        ImageView imageView = new ImageView(image);
        root.getChildren().add(imageView);
        TextField chosenUsername = new TextField("write enemy username");
        // Set up a timeline for color animation
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> chosenUsername.setStyle("-fx-text-fill: red;")),
                new KeyFrame(Duration.millis(250), e -> chosenUsername.setStyle("-fx-text-fill: orange;")),
                new KeyFrame(Duration.millis(500), e -> chosenUsername.setStyle("-fx-text-fill: yellow;")),
                new KeyFrame(Duration.millis(750), e -> chosenUsername.setStyle("-fx-text-fill: green;")),
                new KeyFrame(Duration.millis(1000), e -> chosenUsername.setStyle("-fx-text-fill: blue;"))
        );
        timeline.setCycleCount(Animation.INDEFINITE); // Repeat indefinitely

        // Add listener to start/stop animation based on focus
        chosenUsername.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                timeline.play();
            } else {
                timeline.stop();
                chosenUsername.setStyle(""); // Reset style when not focused
            }
        });
        chosenUsername.setAlignment(Pos.CENTER);
        chosenUsername.setOnMouseClicked(mouseEvent -> {
            if (chosenUsername.getText().equals("write enemy username"))
                chosenUsername.setText("");
        });
        Button pub = new Button("public");
        pub.setOnMouseEntered(e -> animateButton(pub, 1.1));
        pub.setOnMouseExited(e -> animateButton(pub, 1.0));
        Button priv = new Button("private");
        priv.setOnMouseEntered(e -> animateButton(priv, 1.1));
        priv.setOnMouseExited(e -> animateButton(priv, 1.0));
        Button backToMainMenu = new Button("back");
        backToMainMenu.setOnMouseEntered(e -> animateButton(backToMainMenu, 1.1));
        backToMainMenu.setOnMouseExited(e -> animateButton(backToMainMenu, 1.0));
        Button button = new Button("let's go");
        button.setOnMouseEntered(e -> animateButton(button, 1.1));
        button.setOnMouseExited(e -> animateButton(button, 1.0));
        VBox vBox = new VBox(15);
        vBox.getChildren().addAll(pub, priv);
        vBox.setAlignment(Pos.CENTER);
        root.getChildren().add(vBox);
        vBox.setAlignment(Pos.CENTER);
        pub.setOnMouseClicked(event -> {

            // TODO: 7/11/2024 set game public

            vBox.getChildren().removeAll(pub,priv);
            vBox.getChildren().addAll(chosenUsername, button, backToMainMenu);
            vBox.setSpacing(15);
            vBox.setMaxWidth(500);
            vBox.setAlignment(Pos.CENTER);
            initialize();
            backToMainMenu.setOnMouseClicked(event1 -> {
                root.getChildren().removeAll(vBox, imageView);
            });
            button.setOnMouseClicked(mouseEvent -> {
                root.getChildren().removeAll(vBox, imageView);
                App.getGameClient().sendMessage("{request game(username<" + chosenUsername.getText() + ">)}");
//            PreGameMenu preGameMenu = new PreGameMenu();
//            try {
//                preGameMenu.start(App.getStage());
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//            stage.setFullScreen(true);
            });
        });
        priv.setOnMouseClicked(event -> {

            // TODO: 7/11/2024 set game private

            vBox.getChildren().removeAll(pub,priv);
            vBox.getChildren().addAll(chosenUsername, button, backToMainMenu);
            vBox.setSpacing(15);
            vBox.setMaxWidth(500);
            vBox.setAlignment(Pos.CENTER);
            initialize();
            backToMainMenu.setOnMouseClicked(event1 -> {
                root.getChildren().removeAll(vBox, imageView);
            });
            button.setOnMouseClicked(mouseEvent -> {
                root.getChildren().removeAll(vBox, imageView);
                App.getGameClient().sendMessage("{request game(username<" + chosenUsername.getText() + ">)}");
//            PreGameMenu preGameMenu = new PreGameMenu();
//            try {
//                preGameMenu.start(App.getStage());
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//            stage.setFullScreen(true);
            });
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
        for (User user : App.getRankedUsers()) {
            VBox userBox = new VBox();
            userBox.getStyleClass().add("user-box");

            Label rankLabel = new Label((i + 1) + ". " + user.getUsername());
            rankLabel.getStyleClass().add("rank-label");

            Label scoreLabel = new Label("Score: " + user.getScore());
            scoreLabel.getStyleClass().add("score-label");
            Circle online = new Circle(5,Color.GREEN);
            Circle offline = new Circle(5,Color.RED);
            if (user.isOnline()) userBox.getChildren().add(online);
            else userBox.getChildren().add(offline);
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
            App.getRankedUsers().clear();
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
        // Load the image
        String imagePath = "/pics/verify.png"; // Change this to the path of your image file
        Image image = new Image(getClass().getResource(imagePath).toExternalForm());
        ImageView imageView = new ImageView(image);
        root.getChildren().add(imageView);
        Button reject = new Button("reject");
        Button accept = new Button("accept");
        reject.setOnMouseEntered(e -> animateButton(reject, 1.1));
        reject.setOnMouseExited(e -> animateButton(reject, 1.0));
        accept.setOnMouseEntered(e -> animateButton(accept, 1.1));
        accept.setOnMouseExited(e -> animateButton(accept, 1.0));
        Text text = new Text(message);
        text.setFill(Color.GOLD);
        VBox vBox = new VBox(text, accept, reject);
        vBox.setSpacing(15);
        vBox.setMaxWidth(500);
        vBox.setAlignment(Pos.CENTER);
        root.getChildren().add(vBox);
        reject.setOnMouseClicked(mouseEvent -> {
            App.getGameClient().sendMessage("reject");
            root.getChildren().removeAll(vBox, imageView);
        });
        accept.setOnMouseClicked(mouseEvent -> {
            App.getGameClient().sendMessage("accept");
            root.getChildren().removeAll(vBox, imageView);
            preGameMenu = new PreGameMenu();
            try {
                this.stop();
                initialize();
                preGameMenu.start(App.getStage());
            } catch (Exception e) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PreGameMenu getPreGameMenu() {
        return preGameMenu;
    }

    public Game getOnlineGame() {
        return onlineGame;
    }

    public FriendRequestMenu getFriendRequestMenu() {
        return friendRequestMenu;
    }

    public StackPane getRoot() {
        return root;
    }
}
