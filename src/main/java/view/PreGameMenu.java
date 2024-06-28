package view;

import controller.PreGameMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class PreGameMenu extends Application {  @FXML
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
        //TODO: delete it
        totalCardsInDeck.setOnMouseClicked(mouseEvent -> {
            try {
                goToGame();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        //TODO:
        numberOfUnitCards = (Text) scene.lookup("#numberOfUnitCards");
        slashAnd22 = (Text) scene.lookup("#slashAnd22");
        strengthOfCards = (Text) scene.lookup("#strengthOfCards");
        numberOfHeroCards = (Text) scene.lookup("#numberOfHeroCards");
        leaderCard = (TilePane) scene.lookup("#leaderCard");
        changeFaction = (Text) scene.lookup("#changeFaction");
        addUnitCardsOfNorthernRealmsFactionToScrollPane();
        addLeaderCardsOfNorthernRealmsFactionToScrollPane();
        writeOnMouseClickedFunctionsForFactionCards(pane,App.getRealmsNorthenFaction());
        writeOnMouseClickedFunctionsToChangeFaction(pane);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.show();
    }

    private void addUnitCardsOfNorthernRealmsFactionToScrollPane() {
        tilePaneOfCardCollection.getChildren().clear();
        tilePaneOfCardsInDeck.getChildren().clear();
        //TODO : you should run the code i commented when other menus completed
        //User.getLoggedInUser().getPlayBoard().getDeckUnit().getCards().clear();
        for (Card card : App.getRealmsNorthenFaction().getUnitCards()) {
            tilePaneOfCardCollection.getChildren().add(card);
        }

    }
    private void addUnitCardsOfMonstersFactionToScrollPane(){
        //TODO : you should run the code i commented when other menus completed
        //User.getLoggedInUser().getPlayBoard().getDeckUnit().getCards().clear();
        tilePaneOfCardCollection.getChildren().clear();
        tilePaneOfCardsInDeck.getChildren().clear();
        for(Card card : App.getMonstersFaction().getUnitCards()){
            //TODO: id does not work. solve it :(
            tilePaneOfCardCollection.getChildren().add(card);
        }
    }
    private void addUnitCardsOfScoiaTaelFactionToScrollPane(){
        //TODO : you should run the code i commented when other menus completed
        //User.getLoggedInUser().getPlayBoard().getDeckUnit().getCards().clear();
        tilePaneOfCardCollection.getChildren().clear();
        tilePaneOfCardsInDeck.getChildren().clear();
        for(Card card : App.getScoiataelFaction().getUnitCards()){
            tilePaneOfCardCollection.getChildren().add(card);
        }
    }
    private void addUnitCardsOfSkelligeFactionToScrollPane(){
        //TODO : you should run the code i commented when other menus completed
        //User.getLoggedInUser().getPlayBoard().getDeckUnit().getCards().clear();
        tilePaneOfCardCollection.getChildren().clear();
        tilePaneOfCardsInDeck.getChildren().clear();
        for(Card card : App.getSkelligeFaction().getUnitCards()){
            tilePaneOfCardCollection.getChildren().add(card);
        }
    }
    private void addUnitCardsOfEmpireNilfGaardianFactionToScrollPane(){
        //TODO : you should run the code i commented when other menus completed
        //User.getLoggedInUser().getPlayBoard().getDeckUnit().getCards().clear();
        tilePaneOfCardCollection.getChildren().clear();
        tilePaneOfCardsInDeck.getChildren().clear();
        for(Card card : App.getEmpireNilfgaardianFaction().getUnitCards()){
            tilePaneOfCardCollection.getChildren().add(card);
        }
    }
    private void addLeaderCardsOfNorthernRealmsFactionToScrollPane() {
        App.getRealmsNorthenFaction().getFactionLeaderCards().get(0).setPrefWidth(leaderCard.getPrefWidth());
        App.getRealmsNorthenFaction().getFactionLeaderCards().get(0).setPrefHeight(leaderCard.getPrefHeight());
        leaderCard.getChildren().clear();
        leaderCard.getChildren().add(App.getRealmsNorthenFaction().getFactionLeaderCards().get(0));
        User.getLoggedInUser().setFactionLeaderCard(App.getRealmsNorthenFaction().getFactionLeaderCards().get(0));
    }
    private void addLeaderCardsOfSkelligeFactionToScrollPane() {
        App.getSkelligeFaction().getFactionLeaderCards().get(0).setPrefWidth(leaderCard.getPrefWidth());
        App.getRealmsNorthenFaction().getFactionLeaderCards().get(0).setPrefHeight(leaderCard.getPrefHeight());
        leaderCard.getChildren().clear();
        leaderCard.getChildren().add(App.getSkelligeFaction().getFactionLeaderCards().get(0));
        User.getLoggedInUser().setFactionLeaderCard(App.getSkelligeFaction().getFactionLeaderCards().get(0));
    }
    private void addLeaderCardsOfEmpireNilfGaardianFactionToScrollPane() {
        App.getRealmsNorthenFaction().getFactionLeaderCards().get(0).setPrefWidth(leaderCard.getPrefWidth());
        App.getRealmsNorthenFaction().getFactionLeaderCards().get(0).setPrefHeight(leaderCard.getPrefHeight());
        leaderCard.getChildren().clear();
        leaderCard.getChildren().add(App.getRealmsNorthenFaction().getFactionLeaderCards().get(0));
        User.getLoggedInUser().setFactionLeaderCard(App.getEmpireNilfgaardianFaction().getFactionLeaderCards().get(0));
    }
    private void addLeaderCardsOfScoiataelFactionToScrollPane() {
        App.getScoiataelFaction().getFactionLeaderCards().get(0).setPrefWidth(leaderCard.getPrefWidth());
        App.getScoiataelFaction().getFactionLeaderCards().get(0).setPrefHeight(leaderCard.getPrefHeight());
        leaderCard.getChildren().clear();
        leaderCard.getChildren().add(App.getScoiataelFaction().getFactionLeaderCards().get(0));
        User.getLoggedInUser().setFactionLeaderCard(App.getScoiataelFaction().getFactionLeaderCards().get(0));
    }
    private void addLeaderCardsOfMonstersFactionToScrollPane() {
        App.getMonstersFaction().getFactionLeaderCards().get(0).setPrefWidth(leaderCard.getPrefWidth());
        App.getMonstersFaction().getFactionLeaderCards().get(0).setPrefHeight(leaderCard.getPrefHeight());
        leaderCard.getChildren().clear();
        leaderCard.getChildren().add(App.getMonstersFaction().getFactionLeaderCards().get(0));
        User.getLoggedInUser().setFactionLeaderCard(App.getMonstersFaction().getFactionLeaderCards().get(0));
    }
    private void writeOnMouseClickedFunctionsForFactionCards(Pane pane, Faction faction) {
        for (Card card : faction.getUnitCards()) {
            card.setPrefWidth(tilePaneOfCardCollection.getPrefWidth()/3);
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
        //TODO : you should run the code i commented when other menus completed
        //User.getLoggedInUser().getPlayBoard().getDeckUnit().addCardToUnit(card);
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
        //TODO : you should run the code i commented when other menus completed
        //User.getLoggedInUser().getPlayBoard().getDeckUnit().removeCardFromUnit(card);
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
            imageViewMonstersFaction.setX(pane.getWidth()/2-imageOfMonstersFaction.getWidth()/2-100);
            imageViewMonstersFaction.setY(pane.getHeight()/2-imageOfMonstersFaction.getHeight()/2);
            pane.getChildren().add(imageViewMonstersFaction);
            Image imageOfNilfGaardFaction = new Image(LoginMenu.class.getResource("/pics/nilfgaard/faction/NilfgaardFaction.jpg").toExternalForm());
            ImageView imageViewOfNilfGaardFaction = new ImageView(imageOfNilfGaardFaction);
            imageViewOfNilfGaardFaction.setX(imageViewMonstersFaction.getX()+imageOfNilfGaardFaction.getWidth()+50);
            imageViewOfNilfGaardFaction.setY(imageViewMonstersFaction.getY());
            pane.getChildren().add(imageViewOfNilfGaardFaction);
            Image imageOfNorthenRealms = new Image(LoginMenu.class.getResource("/pics/northenRealms/faction/NorthenRealmsFaction.jpg").toExternalForm());
            ImageView imageViewOfNorthenRealms = new ImageView(imageOfNorthenRealms);
            imageViewOfNorthenRealms.setX(imageViewMonstersFaction.getX()-imageOfNorthenRealms.getWidth()-50);
            imageViewOfNorthenRealms.setY(imageViewOfNilfGaardFaction.getY());
            pane.getChildren().add(imageViewOfNorthenRealms);
            Image imageOfScoiatael = new Image(LoginMenu.class.getResource("/pics/scoiatael/faction/scoiataelFaction.jpg").toExternalForm());
            ImageView imageViewOfScoiatael = new ImageView(imageOfScoiatael);
            imageViewOfScoiatael.setX(imageViewOfNilfGaardFaction.getX()+imageOfScoiatael.getWidth()+50);
            imageViewOfScoiatael.setY(imageViewOfNilfGaardFaction.getY());
            pane.getChildren().add(imageViewOfScoiatael);
            Image imageOfSkellige = new Image(LoginMenu.class.getResource("/pics/skellige/faction/SkelligeFaction.jpg").toExternalForm());
            ImageView imageViewOfSkellige = new ImageView(imageOfSkellige);
            imageViewOfSkellige.setX(imageViewOfNorthenRealms.getX()-imageOfSkellige.getWidth()-50);
            imageViewOfSkellige.setY(imageViewOfNorthenRealms.getY());
            pane.getChildren().add(imageViewOfSkellige);
            imageViewOfSkellige.setOnMouseClicked(e2 -> {
                addUnitCardsOfSkelligeFactionToScrollPane();
                addLeaderCardsOfSkelligeFactionToScrollPane();
                writeOnMouseClickedFunctionsForFactionCards(pane,App.getSkelligeFaction());
                User.getLoggedInUser().setFaction(App.getSkelligeFaction());
                pane.getChildren().remove(imageViewMonstersFaction);
                pane.getChildren().remove(imageViewOfNilfGaardFaction);
                pane.getChildren().remove(imageViewOfNorthenRealms);
                pane.getChildren().remove(imageViewOfSkellige);
                pane.getChildren().remove(imageViewOfScoiatael);
            });
            imageViewOfScoiatael.setOnMouseClicked(e3 ->{
                addUnitCardsOfScoiaTaelFactionToScrollPane();
                addLeaderCardsOfScoiataelFactionToScrollPane();
                writeOnMouseClickedFunctionsForFactionCards(pane,App.getScoiataelFaction());
                User.getLoggedInUser().setFaction(App.getScoiataelFaction());
                pane.getChildren().remove(imageViewMonstersFaction);
                pane.getChildren().remove(imageViewOfNilfGaardFaction);
                pane.getChildren().remove(imageViewOfNorthenRealms);
                pane.getChildren().remove(imageViewOfSkellige);
                pane.getChildren().remove(imageViewOfScoiatael);
            });
            imageViewMonstersFaction.setOnMouseClicked(e4 ->{
                addUnitCardsOfMonstersFactionToScrollPane();
                addLeaderCardsOfMonstersFactionToScrollPane();
                writeOnMouseClickedFunctionsForFactionCards(pane,App.getMonstersFaction());
                User.getLoggedInUser().setFaction(App.getMonstersFaction());
                pane.getChildren().remove(imageViewMonstersFaction);
                pane.getChildren().remove(imageViewOfNilfGaardFaction);
                pane.getChildren().remove(imageViewOfNorthenRealms);
                pane.getChildren().remove(imageViewOfSkellige);
                pane.getChildren().remove(imageViewOfScoiatael);
            });
            imageViewOfNilfGaardFaction.setOnMouseClicked(e5 ->{
                addUnitCardsOfEmpireNilfGaardianFactionToScrollPane();
                addLeaderCardsOfEmpireNilfGaardianFactionToScrollPane();
                writeOnMouseClickedFunctionsForFactionCards(pane,App.getEmpireNilfgaardianFaction());
                User.getLoggedInUser().setFaction(App.getEmpireNilfgaardianFaction());
                pane.getChildren().remove(imageViewMonstersFaction);
                pane.getChildren().remove(imageViewOfNilfGaardFaction);
                pane.getChildren().remove(imageViewOfNorthenRealms);
                pane.getChildren().remove(imageViewOfSkellige);
                pane.getChildren().remove(imageViewOfScoiatael);
            });
            imageViewOfNorthenRealms.setOnMouseClicked(e6 ->{
                addUnitCardsOfNorthernRealmsFactionToScrollPane();
                addLeaderCardsOfNorthernRealmsFactionToScrollPane();
                writeOnMouseClickedFunctionsForFactionCards(pane,App.getRealmsNorthenFaction());
                User.getLoggedInUser().setFaction(App.getRealmsNorthenFaction());
                pane.getChildren().remove(imageViewMonstersFaction);
                pane.getChildren().remove(imageViewOfNilfGaardFaction);
                pane.getChildren().remove(imageViewOfNorthenRealms);
                pane.getChildren().remove(imageViewOfSkellige);
                pane.getChildren().remove(imageViewOfScoiatael);
            });
        });
    }
    private void writeOnMouseClickedFunctionToChangeLeaderCard(Pane pane){
        leaderCard.setOnMouseClicked(e ->{

        });
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void goToGame() throws Exception {
        DeckUnit deckUnit = new DeckUnit();
        deckUnit.getCards().addAll(App.getRealmsNorthenFaction().getUnitCards());
        PlayBoard current = new PlayBoard();
        current.setCloseCombatUnit(new CloseCombatUnit());
        current.setDiscardPileUnit(new DiscardPileUnit());
        current.setRangedCombatUnit(new RangedCombatUnit());
        current.setSiegeUnit(new SiegeUnit());
        current.setHandUnit(new HandUnit());
        PlayBoard next = new PlayBoard();
        next.setCloseCombatUnit(new CloseCombatUnit());
        next.setDiscardPileUnit(new DiscardPileUnit());
        next.setRangedCombatUnit(new RangedCombatUnit());
        next.setSiegeUnit(new SiegeUnit());
        next.setHandUnit(new HandUnit());
        User.getLoggedInUser().setPlayBoard(current);
        User.getLoggedInUser().getPlayBoard().setDeckUnit(deckUnit);
        User enemy = new User("a", "a", "a", "a");
        enemy.setPlayBoard(next);
        enemy.getPlayBoard().setDeckUnit(deckUnit);
        Game.setCurrentGame(new Game(User.getLoggedInUser(), enemy, new Date()));
        Game.getCurrentGame().setSpellUnit(new SpellUnit());
        Game.getCurrentGame().setCurrentUser(User.getLoggedInUser());
        Game.getCurrentGame().setNextUser(enemy);
        //TODO: remove up code and write it correctly
        GameMenu gameMenu = new GameMenu();
        this.stop();
        gameMenu.start(App.getStage());
    }
}

