package view;

import controller.GameMenuController;
import com.google.gson.Gson;
import controller.PreGameMenuController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class PreGameMenu extends Application {
    public Text nameOfUser;
    @FXML
    private Text numberOfSpecialCards;
    @FXML
    private ImageView buttonOfChangeLeaderCardToPrevious;
    @FXML
    private ImageView buttonOfChangeLeaderCardToNext;
    @FXML
    private Text changeFaction;
    @FXML
    private TilePane leaderCard;
    @FXML
    private Text numberOfHeroCards;
    @FXML
    private Text strengthOfCards;
    @FXML
    private Text slashAnd22;
    @FXML
    private Text numberOfUnitCards;
    @FXML
    private Text totalCardsInDeck;
    @FXML
    private ScrollPane scrollPaneOfCardsInDeck;
    @FXML
    private TilePane tilePaneOfCardsInDeck;
    @FXML
    private ScrollPane scrollPaneOfCardCollection;
    @FXML
    private TilePane tilePaneOfCardCollection;
    @FXML
    private Button startGame;

    PreGameMenuController controller = new PreGameMenuController(this);

    @Override
    public void start(Stage stage) throws IOException {
        App.setStage(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(LoginMenu.class.getResource("/PreGame.fxml"));
        Pane pane = fxmlLoader.load();
        // Set up the background video
        String videoPath = Objects.requireNonNull(getClass().getResource("/videos/preGameMenuVideo.mp4")).toExternalForm();
        Media preGameMenuVideo = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(preGameMenuVideo);
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.statusProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == MediaPlayer.Status.READY) {
                mediaPlayer.play();
            }
        });
        pane.getChildren().add(0, mediaView);
        Scene scene = new Scene(pane);

        startGame = (Button) scene.lookup("#startGame");
        startGame.setOnMouseEntered(e -> animateButton(startGame, 1.1));
        startGame.setOnMouseExited(e -> animateButton(startGame, 1.0));
        startGame.setOnMouseClicked(mouseEvent -> {
            int parsedIntNumberOfSpecialCards = Integer.parseInt(numberOfSpecialCards.getText());
            if (Game.getCurrentGame().getCurrentUser().getPlayBoard().getDeckUnit().getCards().size() < 22) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Your deck must have at least 22 unit cards");
                alert.getDialogPane().getStylesheets().add(PreGameMenu.class.getResource("/styles/AlertStyle.css").toExternalForm());
                alert.show();
            } else if (parsedIntNumberOfSpecialCards > 10) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Your deck most have no more than 10 special cards");
                alert.getDialogPane().getStylesheets().add(PreGameMenu.class.getResource("/styles/AlertStyle.css").toExternalForm());
                alert.show();
            } else {
                try {
                    if (Game.getCurrentGame().getCurrentUser().equals(Game.getCurrentGame().getMe())) {
                        this.stop();
                        System.out.println(Game.getCurrentGame().getCurrentUser().toJson());
                        App.getGameClient().getOut().writeUTF("start game : "+Game.getCurrentGame().getCurrentUser().toJson());
                        Game.getCurrentGame().setCurrentUser(Game.getCurrentGame().getEnemy());
                        Game.getCurrentGame().setNextUser(Game.getCurrentGame().getMe());
//                        PreGameMenu preGameMenu = new PreGameMenu();
//                        preGameMenu.start(App.getStage());
                    } else {
                        this.stop();
                        Game.getCurrentGame().setCurrentUser(Game.getCurrentGame().getMe());
                        Game.getCurrentGame().setNextUser(Game.getCurrentGame().getEnemy());
                        goToGame();
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        try {
            String cssPath = Objects.requireNonNull(LoginMenu.class.getResource("/styles/preGameMenu.css")).toExternalForm();
            scene.getStylesheets().add(cssPath); // Adding the CSS file to the scene
        } catch (NullPointerException e) {
            System.out.println("CSS file not found.");
        }

        stage.setScene(scene);
        controller.initialize();
        initializeComponents(scene, pane);
        nameOfUser.setText("user: " + Game.getCurrentGame().getCurrentUser().getNickname());
        stage.setFullScreen(true);
        mediaView.setFitWidth(1920);
        mediaView.setFitHeight(1080);
        stage.setResizable(false);
        stage.show();
    }

    private void initializeComponents(Scene scene, Pane pane) {
        scrollPaneOfCardsInDeck = (ScrollPane) scene.lookup("#scrollPaneOfCardsInDeck");
        tilePaneOfCardsInDeck = (TilePane) scrollPaneOfCardsInDeck.getContent().lookup("#tilePaneOfCardsInDeck");
        scrollPaneOfCardCollection = (ScrollPane) scene.lookup("#scrollPaneOfCardCollection");
        tilePaneOfCardCollection = (TilePane) scrollPaneOfCardCollection.getContent().lookup("#tilePaneOfCardCollection");
        totalCardsInDeck = (Text) scene.lookup("#totalCardsInDeck");
        totalCardsInDeck.setOnMouseClicked(mouseEvent -> {
            try {
                goToGame();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        numberOfUnitCards = (Text) scene.lookup("#numberOfUnitCards");
        slashAnd22 = (Text) scene.lookup("#slashAnd22");
        strengthOfCards = (Text) scene.lookup("#strengthOfCards");
        numberOfHeroCards = (Text) scene.lookup("#numberOfHeroCards");
        leaderCard = (TilePane) scene.lookup("#leaderCard");
        changeFaction = (Text) scene.lookup("#changeFaction");
        buttonOfChangeLeaderCardToNext = (ImageView) scene.lookup("#buttonOfChangeLeaderCardToNext");
        buttonOfChangeLeaderCardToPrevious = (ImageView) scene.lookup("#buttonOfChangeLeaderCardToPrevious");
        numberOfSpecialCards = (Text) scene.lookup("#numberOfSpecialCards");
        nameOfUser = (Text) scene.lookup("#nameOfUser");
        addUnitCardsOfNorthernRealmsFactionToScrollPane();
        addLeaderCardsOfNorthernRealmsFactionToScrollPane();
        addButtonOfChangeLeaderCard();
        writeOnMouseClickedFunctionsForFactionCards(pane, App.getRealmsNorthenFaction());
        writeOnMouseClickedFunctionToChangeLeaderCard();
        writeOnMouseClickedFunctionsToChangeFaction(pane);
        Game.getCurrentGame().getCurrentUser().setFaction(App.getRealmsNorthenFaction());
    }


    private void addUnitCardsOfNorthernRealmsFactionToScrollPane() {
        tilePaneOfCardCollection.getChildren().clear();
        tilePaneOfCardsInDeck.getChildren().clear();
        Game.getCurrentGame().getCurrentUser().getPlayBoard().getDeckUnit().getCards().clear();
        for (Card card : App.getRealmsNorthenFaction().getUnitCards()) {
            tilePaneOfCardCollection.getChildren().add(card);
            card.setOnMouseEntered(e -> animateButton(card, 1.1));
            card.setOnMouseExited(e -> animateButton(card, 1.0));
        }

    }

    private void addUnitCardsOfMonstersFactionToScrollPane() {
        Game.getCurrentGame().getCurrentUser().getPlayBoard().getDeckUnit().getCards().clear();
        tilePaneOfCardCollection.getChildren().clear();
        tilePaneOfCardsInDeck.getChildren().clear();
        for (Card card : App.getMonstersFaction().getUnitCards()) {
            //TODO: id does not work. solve it :(
            tilePaneOfCardCollection.getChildren().add(card);
            card.setOnMouseEntered(e -> animateButton(card, 1.1));
            card.setOnMouseExited(e -> animateButton(card, 1.0));
        }
    }

    private void addUnitCardsOfScoiaTaelFactionToScrollPane() {
        Game.getCurrentGame().getCurrentUser().getPlayBoard().getDeckUnit().getCards().clear();
        tilePaneOfCardCollection.getChildren().clear();
        tilePaneOfCardsInDeck.getChildren().clear();
        for (Card card : App.getScoiataelFaction().getUnitCards()) {
            tilePaneOfCardCollection.getChildren().add(card);
            card.setOnMouseEntered(e -> animateButton(card, 1.1));
            card.setOnMouseExited(e -> animateButton(card, 1.0));
        }
    }

    private void addUnitCardsOfSkelligeFactionToScrollPane() {
        Game.getCurrentGame().getCurrentUser().getPlayBoard().getDeckUnit().getCards().clear();
        tilePaneOfCardCollection.getChildren().clear();
        tilePaneOfCardsInDeck.getChildren().clear();
        for (Card card : App.getSkelligeFaction().getUnitCards()) {
            tilePaneOfCardCollection.getChildren().add(card);
            card.setOnMouseEntered(e -> animateButton(card, 1.1));
            card.setOnMouseExited(e -> animateButton(card, 1.0));
        }
    }

    private void addUnitCardsOfEmpireNilfGaardianFactionToScrollPane() {
        Game.getCurrentGame().getCurrentUser().getPlayBoard().getDeckUnit().getCards().clear();
        tilePaneOfCardCollection.getChildren().clear();
        tilePaneOfCardsInDeck.getChildren().clear();
        for (Card card : App.getEmpireNilfgaardianFaction().getUnitCards()) {
            tilePaneOfCardCollection.getChildren().add(card);
            card.setOnMouseEntered(e -> animateButton(card, 1.1));
            card.setOnMouseExited(e -> animateButton(card, 1.0));
        }
    }

    private void addLeaderCardsOfNorthernRealmsFactionToScrollPane() {
        App.getRealmsNorthenFaction().getFactionLeaderCards().get(0).setPrefWidth(leaderCard.getPrefWidth());
        App.getRealmsNorthenFaction().getFactionLeaderCards().get(0).setPrefHeight(leaderCard.getPrefHeight());
        leaderCard.getChildren().clear();
        leaderCard.getChildren().add(App.getRealmsNorthenFaction().getFactionLeaderCards().get(0));
        leaderCard.setOnMouseEntered(e -> animateButton(leaderCard, 1.1));
        leaderCard.setOnMouseExited(e -> animateButton(leaderCard, 1.0));
        Game.getCurrentGame().getCurrentUser().setFactionLeaderCard(App.getRealmsNorthenFaction().getFactionLeaderCards().get(0));
    }

    private void addLeaderCardsOfSkelligeFactionToScrollPane() {
        App.getSkelligeFaction().getFactionLeaderCards().get(0).setPrefWidth(leaderCard.getPrefWidth());
        App.getRealmsNorthenFaction().getFactionLeaderCards().get(0).setPrefHeight(leaderCard.getPrefHeight());
        leaderCard.getChildren().clear();
        leaderCard.getChildren().add(App.getSkelligeFaction().getFactionLeaderCards().get(0));
        leaderCard.setOnMouseEntered(e -> animateButton(leaderCard, 1.1));
        leaderCard.setOnMouseExited(e -> animateButton(leaderCard, 1.0));
        Game.getCurrentGame().getCurrentUser().setFactionLeaderCard(App.getSkelligeFaction().getFactionLeaderCards().get(0));
    }

    private void addLeaderCardsOfEmpireNilfGaardianFactionToScrollPane() {
        App.getRealmsNorthenFaction().getFactionLeaderCards().get(0).setPrefWidth(leaderCard.getPrefWidth());
        App.getRealmsNorthenFaction().getFactionLeaderCards().get(0).setPrefHeight(leaderCard.getPrefHeight());
        leaderCard.getChildren().clear();
        leaderCard.getChildren().add(App.getRealmsNorthenFaction().getFactionLeaderCards().get(0));
        leaderCard.setOnMouseEntered(e -> animateButton(leaderCard, 1.1));
        leaderCard.setOnMouseExited(e -> animateButton(leaderCard, 1.0));
        Game.getCurrentGame().getCurrentUser().setFactionLeaderCard(App.getEmpireNilfgaardianFaction().getFactionLeaderCards().get(0));
    }

    private void addLeaderCardsOfScoiataelFactionToScrollPane() {
        App.getScoiataelFaction().getFactionLeaderCards().get(0).setPrefWidth(leaderCard.getPrefWidth());
        App.getScoiataelFaction().getFactionLeaderCards().get(0).setPrefHeight(leaderCard.getPrefHeight());
        leaderCard.getChildren().clear();
        leaderCard.getChildren().add(App.getScoiataelFaction().getFactionLeaderCards().get(0));
        leaderCard.setOnMouseEntered(e -> animateButton(leaderCard, 1.1));
        leaderCard.setOnMouseExited(e -> animateButton(leaderCard, 1.0));
        Game.getCurrentGame().getCurrentUser().setFactionLeaderCard(App.getScoiataelFaction().getFactionLeaderCards().get(0));
    }

    private void addLeaderCardsOfMonstersFactionToScrollPane() {
        App.getMonstersFaction().getFactionLeaderCards().get(0).setPrefWidth(leaderCard.getPrefWidth());
        App.getMonstersFaction().getFactionLeaderCards().get(0).setPrefHeight(leaderCard.getPrefHeight());
        leaderCard.getChildren().clear();
        leaderCard.getChildren().add(App.getMonstersFaction().getFactionLeaderCards().get(0));
        leaderCard.setOnMouseEntered(e -> animateButton(leaderCard, 1.1));
        leaderCard.setOnMouseExited(e -> animateButton(leaderCard, 1.0));
        Game.getCurrentGame().getCurrentUser().setFactionLeaderCard(App.getMonstersFaction().getFactionLeaderCards().get(0));
    }

    private void addButtonOfChangeLeaderCard() {
        Image image = new Image(LoginMenu.class.getResource("/pics/button/buttonOfChangeLeaderCard.jpg").toExternalForm());
        buttonOfChangeLeaderCardToNext.setImage(image);
        buttonOfChangeLeaderCardToPrevious.setImage(image);
        buttonOfChangeLeaderCardToPrevious.setScaleX(-1);
    }

    private void writeOnMouseClickedFunctionToChangeLeaderCard() {
        buttonOfChangeLeaderCardToNext.setOnMouseClicked(e -> {
            Faction currentFaction = Game.getCurrentGame().getCurrentUser().getFaction();
            Card currentLeaderCard = Game.getCurrentGame().getCurrentUser().getFactionLeaderCard();
            int index = currentFaction.getFactionLeaderCards().indexOf(currentLeaderCard);
            if (index == currentFaction.getFactionLeaderCards().size() - 1) index = 0;
            else index++;
            leaderCard.getChildren().clear();
            Game.getCurrentGame().getCurrentUser().setFactionLeaderCard(currentFaction.getFactionLeaderCards().get(index));
            currentFaction.getFactionLeaderCards().get(index).setPrefWidth(leaderCard.getPrefWidth());
            currentFaction.getFactionLeaderCards().get(index).setPrefHeight(leaderCard.getPrefHeight());
            leaderCard.getChildren().add(currentFaction.getFactionLeaderCards().get(index));
        });
        buttonOfChangeLeaderCardToPrevious.setOnMouseClicked(e -> {
            Faction currentFaction = Game.getCurrentGame().getCurrentUser().getFaction();
            Card currentLeaderCard = Game.getCurrentGame().getCurrentUser().getFactionLeaderCard();
            int index = currentFaction.getFactionLeaderCards().indexOf(currentLeaderCard);
            if (index == 0) index = currentFaction.getFactionLeaderCards().size() - 1;
            else index--;
            leaderCard.getChildren().clear();
            currentFaction.getFactionLeaderCards().get(index).setPrefWidth(leaderCard.getPrefWidth());
            currentFaction.getFactionLeaderCards().get(index).setPrefHeight(leaderCard.getPrefHeight());
            leaderCard.getChildren().add(currentFaction.getFactionLeaderCards().get(index));
            Game.getCurrentGame().getCurrentUser().setFactionLeaderCard(currentFaction.getFactionLeaderCards().get(index));
        });
    }

    private void writeOnMouseClickedFunctionsForFactionCards(Pane pane, Faction faction) {
        for (Card card : faction.getUnitCards()) {
            card.setPrefWidth(tilePaneOfCardCollection.getPrefWidth() / 3);
            card.setPrefHeight(tilePaneOfCardCollection.getPrefHeight());
            card.setOnMouseClicked(e -> {
                if (tilePaneOfCardCollection.getChildren().contains(card))
                    doFunctionsForCardCollection(card, pane);
                else if (tilePaneOfCardsInDeck.getChildren().contains(card))
                    doFunctionsForCardsInDeck(card, pane);
            });

        }
    }

    private void doFunctionsForCardCollection(Card card, Pane pane) {
        PlayBoard currentPlayBoard = Game.getCurrentGame().getCurrentUser().getPlayBoard();
        currentPlayBoard.getDeckUnit().addCardToUnit(card);
        tilePaneOfCardsInDeck.getChildren().add(card);
        totalCardsInDeck.setText(String.valueOf(currentPlayBoard.getDeckUnit().getCards().size()));
        if ((card instanceof UnitCard)) {
            int parsedIntNumberOfUnitCards = Integer.parseInt(numberOfUnitCards.getText());
            parsedIntNumberOfUnitCards += 1;
            numberOfUnitCards.setText(String.valueOf(parsedIntNumberOfUnitCards));
            if (parsedIntNumberOfUnitCards > 22) pane.getChildren().remove(slashAnd22);
            int parsedIntStrengthOfCards = Integer.parseInt(strengthOfCards.getText());
            parsedIntStrengthOfCards += ((UnitCard) card).getPower();
            strengthOfCards.setText(String.valueOf(parsedIntStrengthOfCards));
            if (((UnitCard) card).isLegendary()) {
                int parsedIntnumberOfHeroCards = Integer.parseInt(numberOfHeroCards.getText());
                parsedIntnumberOfHeroCards += 1;
                numberOfHeroCards.setText(String.valueOf(parsedIntnumberOfHeroCards));
            }
        }

    }

    private void doFunctionsForCardsInDeck(Card card, Pane pane) {
        PlayBoard currentPlayBoard = Game.getCurrentGame().getCurrentUser().getPlayBoard();
        currentPlayBoard.getDeckUnit().removeCardFromUnit(card);
        tilePaneOfCardCollection.getChildren().add(card);
        totalCardsInDeck.setText(String.valueOf(currentPlayBoard.getDeckUnit().getCards().size()));
        if ((card instanceof UnitCard)) {
            int parsedIntNumberOfUnitCards = Integer.parseInt(numberOfUnitCards.getText());
            parsedIntNumberOfUnitCards -= 1;
            numberOfUnitCards.setText(String.valueOf(parsedIntNumberOfUnitCards));
            if (parsedIntNumberOfUnitCards == 22) pane.getChildren().add(slashAnd22);
            int parsedIntStrengthOfCards = Integer.parseInt(strengthOfCards.getText());
            parsedIntStrengthOfCards -= ((UnitCard) card).getPower();
            strengthOfCards.setText(String.valueOf(parsedIntStrengthOfCards));
            if (((UnitCard) card).isLegendary()) {
                int parsedIntnumberOfHeroCards = Integer.parseInt(numberOfHeroCards.getText());
                parsedIntnumberOfHeroCards -= 1;
                numberOfHeroCards.setText(String.valueOf(parsedIntnumberOfHeroCards));
            }
        }

    }

    public void writeOnMouseClickedFunctionsToChangeFaction(Pane pane) {
        changeFaction.setOnMouseClicked(e -> {
            Image imageOfMonstersFaction = new Image(LoginMenu.class.getResource("/pics/monsters/faction/MonstersFaction.jpg").toExternalForm());
            ImageView imageViewMonstersFaction = new ImageView(imageOfMonstersFaction);
            imageViewMonstersFaction.setX(pane.getWidth() / 2 - imageOfMonstersFaction.getWidth() / 2 - 50);
            imageViewMonstersFaction.setY(pane.getHeight() / 2 - imageOfMonstersFaction.getHeight() / 2);
            imageViewMonstersFaction.setOnMouseEntered(e1 -> animateButton(imageViewMonstersFaction, 1.1));
            imageViewMonstersFaction.setOnMouseExited(e1 -> animateButton(imageViewMonstersFaction, 1.0));
            pane.getChildren().add(imageViewMonstersFaction);
            Image imageOfNilfGaardFaction = new Image(LoginMenu.class.getResource("/pics/nilfgaard/faction/NilfgaardFaction.jpg").toExternalForm());
            ImageView imageViewOfNilfGaardFaction = new ImageView(imageOfNilfGaardFaction);
            imageViewOfNilfGaardFaction.setX(imageViewMonstersFaction.getX() + imageOfNilfGaardFaction.getWidth() + 100);
            imageViewOfNilfGaardFaction.setY(imageViewMonstersFaction.getY());
            imageViewOfNilfGaardFaction.setOnMouseEntered(e2 -> animateButton(imageViewOfNilfGaardFaction, 1.1));
            imageViewOfNilfGaardFaction.setOnMouseExited(e2 -> animateButton(imageViewOfNilfGaardFaction, 1.0));
            pane.getChildren().add(imageViewOfNilfGaardFaction);
            Image imageOfNorthenRealms = new Image(LoginMenu.class.getResource("/pics/northenRealms/faction/NorthenRealmsFaction.jpg").toExternalForm());
            ImageView imageViewOfNorthenRealms = new ImageView(imageOfNorthenRealms);
            imageViewOfNorthenRealms.setX(imageViewMonstersFaction.getX() - imageOfNorthenRealms.getWidth() - 100);
            imageViewOfNorthenRealms.setY(imageViewOfNilfGaardFaction.getY());
            imageViewOfNorthenRealms.setOnMouseEntered(e3 -> animateButton(imageViewOfNorthenRealms, 1.1));
            imageViewOfNorthenRealms.setOnMouseExited(e3 -> animateButton(imageViewOfNorthenRealms, 1.0));
            pane.getChildren().add(imageViewOfNorthenRealms);
            Image imageOfScoiatael = new Image(LoginMenu.class.getResource("/pics/scoiatael/faction/scoiataelFaction.jpg").toExternalForm());
            ImageView imageViewOfScoiatael = new ImageView(imageOfScoiatael);
            imageViewOfScoiatael.setX(imageViewOfNilfGaardFaction.getX() + imageOfScoiatael.getWidth() + 100);
            imageViewOfScoiatael.setY(imageViewOfNilfGaardFaction.getY());
            imageViewOfScoiatael.setOnMouseEntered(e4 -> animateButton(imageViewOfScoiatael, 1.1));
            imageViewOfScoiatael.setOnMouseExited(e4 -> animateButton(imageViewOfScoiatael, 1.0));
            pane.getChildren().add(imageViewOfScoiatael);
            Image imageOfSkellige = new Image(LoginMenu.class.getResource("/pics/skellige/faction/SkelligeFaction.jpg").toExternalForm());
            ImageView imageViewOfSkellige = new ImageView(imageOfSkellige);
            imageViewOfSkellige.setX(imageViewOfNorthenRealms.getX() - imageOfSkellige.getWidth() - 100);
            imageViewOfSkellige.setY(imageViewOfNorthenRealms.getY());
            imageViewOfSkellige.setOnMouseEntered(e5 -> animateButton(imageViewOfSkellige, 1.1));
            imageViewOfSkellige.setOnMouseExited(e5 -> animateButton(imageViewOfSkellige, 1.0));
            pane.getChildren().add(imageViewOfSkellige);
            imageViewOfSkellige.setOnMouseClicked(e2 -> {
                addUnitCardsOfSkelligeFactionToScrollPane();
                addLeaderCardsOfSkelligeFactionToScrollPane();
                writeOnMouseClickedFunctionsForFactionCards(pane, App.getSkelligeFaction());
                Game.getCurrentGame().getCurrentUser().setFaction(App.getSkelligeFaction());
                pane.getChildren().remove(imageViewMonstersFaction);
                pane.getChildren().remove(imageViewOfNilfGaardFaction);
                pane.getChildren().remove(imageViewOfNorthenRealms);
                pane.getChildren().remove(imageViewOfSkellige);
                pane.getChildren().remove(imageViewOfScoiatael);
            });
            imageViewOfScoiatael.setOnMouseClicked(e3 -> {
                addUnitCardsOfScoiaTaelFactionToScrollPane();
                addLeaderCardsOfScoiataelFactionToScrollPane();
                writeOnMouseClickedFunctionsForFactionCards(pane, App.getScoiataelFaction());
                Game.getCurrentGame().getCurrentUser().setFaction(App.getScoiataelFaction());
                pane.getChildren().remove(imageViewMonstersFaction);
                pane.getChildren().remove(imageViewOfNilfGaardFaction);
                pane.getChildren().remove(imageViewOfNorthenRealms);
                pane.getChildren().remove(imageViewOfSkellige);
                pane.getChildren().remove(imageViewOfScoiatael);
            });
            imageViewMonstersFaction.setOnMouseClicked(e4 -> {
                addUnitCardsOfMonstersFactionToScrollPane();
                addLeaderCardsOfMonstersFactionToScrollPane();
                writeOnMouseClickedFunctionsForFactionCards(pane, App.getMonstersFaction());
                Game.getCurrentGame().getCurrentUser().setFaction(App.getMonstersFaction());
                pane.getChildren().remove(imageViewMonstersFaction);
                pane.getChildren().remove(imageViewOfNilfGaardFaction);
                pane.getChildren().remove(imageViewOfNorthenRealms);
                pane.getChildren().remove(imageViewOfSkellige);
                pane.getChildren().remove(imageViewOfScoiatael);
            });
            imageViewOfNilfGaardFaction.setOnMouseClicked(e5 -> {
                addUnitCardsOfEmpireNilfGaardianFactionToScrollPane();
                addLeaderCardsOfEmpireNilfGaardianFactionToScrollPane();
                writeOnMouseClickedFunctionsForFactionCards(pane, App.getEmpireNilfgaardianFaction());
                Game.getCurrentGame().getCurrentUser().setFaction(App.getEmpireNilfgaardianFaction());
                pane.getChildren().remove(imageViewMonstersFaction);
                pane.getChildren().remove(imageViewOfNilfGaardFaction);
                pane.getChildren().remove(imageViewOfNorthenRealms);
                pane.getChildren().remove(imageViewOfSkellige);
                pane.getChildren().remove(imageViewOfScoiatael);
            });
            imageViewOfNorthenRealms.setOnMouseClicked(e6 -> {
                addUnitCardsOfNorthernRealmsFactionToScrollPane();
                addLeaderCardsOfNorthernRealmsFactionToScrollPane();
                writeOnMouseClickedFunctionsForFactionCards(pane, App.getRealmsNorthenFaction());
                Game.getCurrentGame().getCurrentUser().setFaction(App.getRealmsNorthenFaction());
                pane.getChildren().remove(imageViewMonstersFaction);
                pane.getChildren().remove(imageViewOfNilfGaardFaction);
                pane.getChildren().remove(imageViewOfNorthenRealms);
                pane.getChildren().remove(imageViewOfSkellige);
                pane.getChildren().remove(imageViewOfScoiatael);
            });
        });
    }


    private void animateButton(Card button, double scale) {
        Timeline timeline = new Timeline();
        KeyValue kvX = new KeyValue(button.scaleXProperty(), scale);
        KeyValue kvY = new KeyValue(button.scaleYProperty(), scale);
        KeyFrame kf = new KeyFrame(Duration.millis(300), kvX, kvY);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    private void animateButton(TilePane button, double scale) {
        Timeline timeline = new Timeline();
        KeyValue kvX = new KeyValue(button.scaleXProperty(), scale);
        KeyValue kvY = new KeyValue(button.scaleYProperty(), scale);
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

    private void animateButton(ImageView button, double scale) {
        Timeline timeline = new Timeline();
        KeyValue kvX = new KeyValue(button.scaleXProperty(), scale);
        KeyValue kvY = new KeyValue(button.scaleYProperty(), scale);
        KeyFrame kf = new KeyFrame(Duration.millis(300), kvX, kvY);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void goToGame() throws Exception {
        GameMenu gameMenu = new GameMenu();
        this.stop();
        gameMenu.start(App.getStage());
    }
}
