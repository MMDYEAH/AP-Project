package view;

import controller.PreGameMenuController;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.Date;

public class PreGameMenu extends Application {
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

    PreGameMenuController controller = new PreGameMenuController(this);

    @Override
    public void start(Stage stage) throws IOException {
        App.setStage(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(LoginMenu.class.getResource("/PreGame.fxml"));
        Pane pane = fxmlLoader.load();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        controller.initialize();
        scrollPaneOfCardsInDeck = (ScrollPane) scene.lookup("#scrollPaneOfCardsInDeck");
        tilePaneOfCardsInDeck = (TilePane) scrollPaneOfCardsInDeck.getContent().lookup("#tilePaneOfCardsInDeck");
        scrollPaneOfCardCollection = (ScrollPane) scene.lookup("#scrollPaneOfCardCollection");
        tilePaneOfCardCollection = (TilePane) scrollPaneOfCardCollection.getContent().lookup("#tilePaneOfCardCollection");
        totalCardsInDeck = (Text) scene.lookup("#totalCardsInDeck");
        numberOfUnitCards = (Text) scene.lookup("#numberOfUnitCards");
        slashAnd22 = (Text) scene.lookup("#slashAnd22");
        strengthOfCards = (Text) scene.lookup("#strengthOfCards");
        numberOfHeroCards = (Text) scene.lookup("#numberOfHeroCards");
        leaderCard = (TilePane) scene.lookup("#leaderCard");
        changeFaction = (Text) scene.lookup("#changeFaction");
        buttonOfChangeLeaderCardToNext = (ImageView) scene.lookup("#buttonOfChangeLeaderCardToNext");
        buttonOfChangeLeaderCardToPrevious = (ImageView) scene.lookup("#buttonOfChangeLeaderCardToPrevious");
        numberOfSpecialCards = (Text) scene.lookup("#numberOfSpecialCards");
        addUnitCardsOfNorthernRealmsFactionToScrollPane();
        addLeaderCardsOfNorthernRealmsFactionToScrollPane();
        addButtonOfChangeLeaderCard();
        writeOnMouseClickedFunctionsForFactionCards(pane, App.getRealmsNorthenFaction());
        writeOnMouseClickedFunctionToChangeLeaderCard();
        writeOnMouseClickedFunctionsToChangeFaction(pane);
        User.getLoggedInUser().setFaction(App.getRealmsNorthenFaction());
        createButtonOfStartGame(pane);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.show();
    }

    private void addUnitCardsOfNorthernRealmsFactionToScrollPane() {
        tilePaneOfCardCollection.getChildren().clear();
        tilePaneOfCardsInDeck.getChildren().clear();
        User.getLoggedInUser().getPlayBoard().getDeckUnit().getCards().clear();
        for (Card card : App.getRealmsNorthenFaction().getUnitCards()) {
            tilePaneOfCardCollection.getChildren().add(card);
        }

    }

    private void addUnitCardsOfMonstersFactionToScrollPane() {
        User.getLoggedInUser().getPlayBoard().getDeckUnit().getCards().clear();
        tilePaneOfCardCollection.getChildren().clear();
        tilePaneOfCardsInDeck.getChildren().clear();
        for (Card card : App.getMonstersFaction().getUnitCards()) {
            //TODO: id does not work. solve it :(
            tilePaneOfCardCollection.getChildren().add(card);
        }
    }

    private void addUnitCardsOfScoiaTaelFactionToScrollPane() {
        User.getLoggedInUser().getPlayBoard().getDeckUnit().getCards().clear();
        tilePaneOfCardCollection.getChildren().clear();
        tilePaneOfCardsInDeck.getChildren().clear();
        for (Card card : App.getScoiataelFaction().getUnitCards()) {
            tilePaneOfCardCollection.getChildren().add(card);
        }
    }

    private void addUnitCardsOfSkelligeFactionToScrollPane() {
        User.getLoggedInUser().getPlayBoard().getDeckUnit().getCards().clear();
        tilePaneOfCardCollection.getChildren().clear();
        tilePaneOfCardsInDeck.getChildren().clear();
        for (Card card : App.getSkelligeFaction().getUnitCards()) {
            tilePaneOfCardCollection.getChildren().add(card);
        }
    }

    private void addUnitCardsOfEmpireNilfGaardianFactionToScrollPane() {
        User.getLoggedInUser().getPlayBoard().getDeckUnit().getCards().clear();
        tilePaneOfCardCollection.getChildren().clear();
        tilePaneOfCardsInDeck.getChildren().clear();
        for (Card card : App.getEmpireNilfgaardianFaction().getUnitCards()) {
            tilePaneOfCardCollection.getChildren().add(card);
        }
    }

    private void addLeaderCardsOfNorthernRealmsFactionToScrollPane() {
        App.getRealmsNorthenFaction().getFactionLeaderCards().get(0).setWidth(leaderCard.getPrefWidth());
        App.getRealmsNorthenFaction().getFactionLeaderCards().get(0).setHeight(leaderCard.getPrefHeight());
        leaderCard.getChildren().clear();
        leaderCard.getChildren().add(App.getRealmsNorthenFaction().getFactionLeaderCards().get(0));
        User.getLoggedInUser().setFactionLeaderCard(App.getRealmsNorthenFaction().getFactionLeaderCards().get(0));
    }

    private void addLeaderCardsOfSkelligeFactionToScrollPane() {
        App.getSkelligeFaction().getFactionLeaderCards().get(0).setWidth(leaderCard.getPrefWidth());
        App.getSkelligeFaction().getFactionLeaderCards().get(0).setHeight(leaderCard.getPrefHeight());
        leaderCard.getChildren().clear();
        leaderCard.getChildren().add(App.getSkelligeFaction().getFactionLeaderCards().get(0));
        User.getLoggedInUser().setFactionLeaderCard(App.getSkelligeFaction().getFactionLeaderCards().get(0));
    }

    private void addLeaderCardsOfEmpireNilfGaardianFactionToScrollPane() {
        App.getRealmsNorthenFaction().getFactionLeaderCards().get(0).setWidth(leaderCard.getPrefWidth());
        App.getRealmsNorthenFaction().getFactionLeaderCards().get(0).setHeight(leaderCard.getPrefHeight());
        leaderCard.getChildren().clear();
        leaderCard.getChildren().add(App.getRealmsNorthenFaction().getFactionLeaderCards().get(0));
        User.getLoggedInUser().setFactionLeaderCard(App.getEmpireNilfgaardianFaction().getFactionLeaderCards().get(0));
    }

    private void addLeaderCardsOfScoiataelFactionToScrollPane() {
        App.getScoiataelFaction().getFactionLeaderCards().get(0).setWidth(leaderCard.getPrefWidth());
        App.getScoiataelFaction().getFactionLeaderCards().get(0).setHeight(leaderCard.getPrefHeight());
        leaderCard.getChildren().clear();
        leaderCard.getChildren().add(App.getScoiataelFaction().getFactionLeaderCards().get(0));
        User.getLoggedInUser().setFactionLeaderCard(App.getScoiataelFaction().getFactionLeaderCards().get(0));
    }

    private void addLeaderCardsOfMonstersFactionToScrollPane() {
        App.getMonstersFaction().getFactionLeaderCards().get(0).setWidth(leaderCard.getPrefWidth());
        App.getMonstersFaction().getFactionLeaderCards().get(0).setHeight(leaderCard.getPrefHeight());
        leaderCard.getChildren().clear();
        leaderCard.getChildren().add(App.getMonstersFaction().getFactionLeaderCards().get(0));
        User.getLoggedInUser().setFactionLeaderCard(App.getMonstersFaction().getFactionLeaderCards().get(0));
    }

    private void addButtonOfChangeLeaderCard() {
        Image image = new Image(LoginMenu.class.getResource("/pics/button/buttonOfChangeLeaderCard.jpg").toExternalForm());
        buttonOfChangeLeaderCardToNext.setImage(image);
        buttonOfChangeLeaderCardToPrevious.setImage(image);
        buttonOfChangeLeaderCardToPrevious.setScaleX(-1);
    }

    private void writeOnMouseClickedFunctionToChangeLeaderCard() {
        buttonOfChangeLeaderCardToNext.setOnMouseClicked(e -> {
            Faction currentFaction = User.getLoggedInUser().getFaction();
            Card currentLeaderCard = User.getLoggedInUser().getFactionLeaderCard();
            int index = currentFaction.getFactionLeaderCards().indexOf(currentLeaderCard);
            if (index == currentFaction.getFactionLeaderCards().size() - 1) index = 0;
            else index++;
            leaderCard.getChildren().clear();
            User.getLoggedInUser().setFactionLeaderCard(currentFaction.getFactionLeaderCards().get(index));
            currentFaction.getFactionLeaderCards().get(index).setWidth(leaderCard.getPrefWidth());
            currentFaction.getFactionLeaderCards().get(index).setHeight(leaderCard.getPrefHeight());
            leaderCard.getChildren().add(currentFaction.getFactionLeaderCards().get(index));
        });
        buttonOfChangeLeaderCardToPrevious.setOnMouseClicked(e -> {
            Faction currentFaction = User.getLoggedInUser().getFaction();
             Card currentLeaderCard = User.getLoggedInUser().getFactionLeaderCard();
            int index = currentFaction.getFactionLeaderCards().indexOf(currentLeaderCard);
            if (index == 0) index = currentFaction.getFactionLeaderCards().size() - 1;
            else index--;
            leaderCard.getChildren().clear();
            currentFaction.getFactionLeaderCards().get(index).setWidth(leaderCard.getPrefWidth());
            currentFaction.getFactionLeaderCards().get(index).setHeight(leaderCard.getPrefHeight());
            leaderCard.getChildren().add(currentFaction.getFactionLeaderCards().get(index));
            User.getLoggedInUser().setFactionLeaderCard(currentFaction.getFactionLeaderCards().get(index));
        });
    }

    private void writeOnMouseClickedFunctionsForFactionCards(Pane pane, Faction faction) {
        for (Card card : faction.getUnitCards()) {
            card.setWidth(tilePaneOfCardCollection.getPrefWidth() / 3);
            card.setHeight(tilePaneOfCardCollection.getPrefHeight());
            card.setOnMouseClicked(e -> {
                if (tilePaneOfCardCollection.getChildren().contains(card))
                    doFunctionsForCardCollection(card, pane);
                else if (tilePaneOfCardsInDeck.getChildren().contains(card))
                    doFunctionsForCardsInDeck(card, pane);
            });

        }
    }

    private void doFunctionsForCardCollection(Card card, Pane pane) {
        User.getLoggedInUser().getPlayBoard().getDeckUnit().addCardToUnit(card);
        tilePaneOfCardsInDeck.getChildren().add(card);
        int numberOfTotalCards = Integer.parseInt(totalCardsInDeck.getText());
        numberOfTotalCards += 1;
        totalCardsInDeck.setText(String.valueOf(numberOfTotalCards));
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
        User.getLoggedInUser().getPlayBoard().getDeckUnit().removeCardFromUnit(card);
        tilePaneOfCardCollection.getChildren().add(card);
        int numberOfTotalCards = Integer.parseInt(totalCardsInDeck.getText());
        numberOfTotalCards -= 1;
        totalCardsInDeck.setText(String.valueOf(numberOfTotalCards));
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
            pane.getChildren().add(imageViewMonstersFaction);
            Image imageOfNilfGaardFaction = new Image(LoginMenu.class.getResource("/pics/nilfgaard/faction/NilfgaardFaction.jpg").toExternalForm());
            ImageView imageViewOfNilfGaardFaction = new ImageView(imageOfNilfGaardFaction);
            imageViewOfNilfGaardFaction.setX(imageViewMonstersFaction.getX() + imageOfNilfGaardFaction.getWidth() + 100);
            imageViewOfNilfGaardFaction.setY(imageViewMonstersFaction.getY());
            pane.getChildren().add(imageViewOfNilfGaardFaction);
            Image imageOfNorthenRealms = new Image(LoginMenu.class.getResource("/pics/northenRealms/faction/NorthenRealmsFaction.jpg").toExternalForm());
            ImageView imageViewOfNorthenRealms = new ImageView(imageOfNorthenRealms);
            imageViewOfNorthenRealms.setX(imageViewMonstersFaction.getX() - imageOfNorthenRealms.getWidth() - 100);
            imageViewOfNorthenRealms.setY(imageViewOfNilfGaardFaction.getY());
            pane.getChildren().add(imageViewOfNorthenRealms);
            Image imageOfScoiatael = new Image(LoginMenu.class.getResource("/pics/scoiatael/faction/scoiataelFaction.jpg").toExternalForm());
            ImageView imageViewOfScoiatael = new ImageView(imageOfScoiatael);
            imageViewOfScoiatael.setX(imageViewOfNilfGaardFaction.getX() + imageOfScoiatael.getWidth() + 100);
            imageViewOfScoiatael.setY(imageViewOfNilfGaardFaction.getY());
            pane.getChildren().add(imageViewOfScoiatael);
            Image imageOfSkellige = new Image(LoginMenu.class.getResource("/pics/skellige/faction/SkelligeFaction.jpg").toExternalForm());
            ImageView imageViewOfSkellige = new ImageView(imageOfSkellige);
            imageViewOfSkellige.setX(imageViewOfNorthenRealms.getX() - imageOfSkellige.getWidth() - 100);
            imageViewOfSkellige.setY(imageViewOfNorthenRealms.getY());
            pane.getChildren().add(imageViewOfSkellige);
            imageViewOfSkellige.setOnMouseClicked(e2 -> {
                addUnitCardsOfSkelligeFactionToScrollPane();
                addLeaderCardsOfSkelligeFactionToScrollPane();
                writeOnMouseClickedFunctionsForFactionCards(pane, App.getSkelligeFaction());
                User.getLoggedInUser().setFaction(App.getSkelligeFaction());
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
                User.getLoggedInUser().setFaction(App.getScoiataelFaction());
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
                User.getLoggedInUser().setFaction(App.getMonstersFaction());
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
                User.getLoggedInUser().setFaction(App.getEmpireNilfgaardianFaction());
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
                User.getLoggedInUser().setFaction(App.getRealmsNorthenFaction());
                pane.getChildren().remove(imageViewMonstersFaction);
                pane.getChildren().remove(imageViewOfNilfGaardFaction);
                pane.getChildren().remove(imageViewOfNorthenRealms);
                pane.getChildren().remove(imageViewOfSkellige);
                pane.getChildren().remove(imageViewOfScoiatael);
            });
        });
    }
    private void createButtonOfStartGame(Pane pane){
        Button startGameButton = new Button("Start game");
        startGameButton.setStyle("-fx-background-color: gray; -fx-border-color: white; -fx-border-width: 2px;");
        startGameButton.setLayoutX(655);
        startGameButton.setLayoutY(736);
        pane.getChildren().add(startGameButton);
        writeOnMouseClickedFunctionTpStartGame(startGameButton);
    }
    private void writeOnMouseClickedFunctionTpStartGame(Button startGameButton){
        startGameButton.setOnMouseClicked(e ->{
            int parsedIntNumberOfSpecialCards = Integer.parseInt(numberOfSpecialCards.getText());
            if(User.getLoggedInUser().getPlayBoard().getDeckUnit().getCards().size()<22){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Your deck must have at least 22 unit cards");
                alert.getDialogPane().getStylesheets().add(PreGameMenu.class.getResource("styles/AlertStyle.css").toExternalForm());
                alert.show();
            }
            else if(parsedIntNumberOfSpecialCards>10){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Your deck most have no more than 10 special cards");
                alert.getDialogPane().getStylesheets().add(PreGameMenu.class.getResource("styles/AlertStyle.css").toExternalForm());
                alert.show();
            }
            else {
                try {
                    goToGame();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
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

