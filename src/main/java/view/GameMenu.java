package view;

import controller.GameMenuController;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameMenu extends Application {
    public ImageView img;
    public Pane pane;
    public AnchorPane myLeader;
    public AnchorPane spell;
    public AnchorPane enemyLeader;
    public AnchorPane mySiege;
    public AnchorPane myRanged;
    public AnchorPane myClose;
    public AnchorPane enemyClose;
    public AnchorPane enemyRanged;
    public AnchorPane enemySiege;
    public AnchorPane enemyRangedSpell;
    public AnchorPane enemyCloseSpell;
    public AnchorPane enemySiegeSpell;
    public AnchorPane myCloseSpell;
    public AnchorPane myRangedSpell;
    public AnchorPane mySiegeSpell;
    public AnchorPane myDiscard;
    public AnchorPane enemyDiscard;
    public AnchorPane enemyDeck;
    public AnchorPane myDeck;
    public TextField enemyTotalPower;
    public TextField enemyClosePower;
    public TextField enemyRangedPower;
    public TextField enemySiegePower;
    public TextField myClosePower;
    public TextField myRangedPower;
    public TextField mySiegePower;
    public TextField myTotalPower;
    public ScrollPane scrollPane;
    public TilePane tilePane;
    public TilePane mySiegeTile;
    public TilePane myRangedTile;
    public TilePane myCloseTile;
    public TilePane enemyCloseTile;
    public TilePane enemyRangedTile;
    public TilePane enemySiegeTile;
    public TilePane spellTile;
    public Button pass;
    public Text myLives;
    public Text enemyLives;
    public ImageView niceImg;
    public Text nicePlay;
    public Text notGood;
    public ImageView badImg;
    public Button chatBox;
    public Button buttonOfCheatMenu;

    private ImageView bad;

    private ImageView nice;

    private ObservableList<Card> currentHand;
    private ObservableList<Card> currentClose;
    private ObservableList<Card> currentRanged;
    private ObservableList<Card> currentSiege;
    private ObservableList<Card> nextClose;
    private ObservableList<Card> nextRanged;
    private ObservableList<Card> nextSiege;

    private ObservableList<Card> weatherUnit;

    GameMenuController controller = new GameMenuController(this);
    ChatBoxMenu chatBoxMenu = new ChatBoxMenu();

    ArrayList<String> messages = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(GameMenu.class.getResource("/game.fxml"));
        pane = fxmlLoader.load();
        Scene scene = new Scene(pane);
        Game.getCurrentGame().setTurnNumber(0);
        initializeElements(scene);
        transparentTextFields();
        setMessagesMouseClick();
        setChatOnMouseClick();
        scrollPane.setBackground(Background.fill(Color.rgb(89, 45, 6)));
        tilePane.setHgap(10);
        tilePane.setBackground(Background.fill(Color.rgb(89, 45, 6)));
        addListeners();
        startingOfGame();
        addPassing();
        fillingUnits();
        scrollPane.setContent(new Group(tilePane));
        stage.setTitle("GWENT-GAME");
        stage.setScene(scene);
        stage.show();
    }

    private void setChatOnMouseClick() {
        chatBox.setOnMouseClicked(mouseEvent -> {
            Stage stage = new Stage();
            try {
                chatBoxMenu.setGameMenu(this);
                chatBoxMenu.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void setMessagesMouseClick() {
        badImg.setOnMouseClicked(mouseEvent -> {
            App.getGameClient().sendMessage("bad img");
            makeEmojiOfBadImg();
        });
        niceImg.setOnMouseClicked(mouseEvent -> {
            App.getGameClient().sendMessage("nice img");
            makeEmojiOfNiceImg();
        });
        nicePlay.setOnMouseClicked(mouseEvent -> {
            App.getGameClient().sendMessage("nice play");
            popUpText(new Text("nice play!"));
        });
        notGood.setOnMouseClicked(mouseEvent -> {
            App.getGameClient().sendMessage("not good");
            popUpText(new Text("not good"));
        });
    }

    public void makeEmojiOfBadImg() {
        popUpEmoji(bad);
    }

    public void makeEmojiOfNiceImg() {
        popUpEmoji(nice);
    }

    private void popUpEmoji(ImageView imageView) {
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setX((pane.getWidth() - imageView.getFitWidth()) / 2); // Center horizontally
        imageView.setY(pane.getHeight() - imageView.getFitHeight()); // Position at the bottom

        // Add the ImageView to the Pane
        pane.getChildren().add(imageView);

        // Create a TranslateTransition to move the ImageView up
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(5), imageView);
        translateTransition.setFromY(pane.getHeight() - imageView.getFitWidth());
        translateTransition.setToY(-pane.getHeight());
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        translateTransition.setOnFinished(actionEvent -> pane.getChildren().remove(imageView));
        // Start the animation
        translateTransition.play();
    }

    public void popUpText(Text text) {
        text.setWrappingWidth(100);
        text.setFill(Color.WHITE);
        text.setX((pane.getWidth() - text.getWrappingWidth()) / 2); // Center horizontally
        text.setY(pane.getHeight()); // Position at the bottom

        // Add the ImageView to the Pane
        pane.getChildren().add(text);

        // Create a TranslateTransition to move the ImageView up
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(5), text);
        translateTransition.setFromY(pane.getHeight() - text.getWrappingWidth());
        translateTransition.setToY(-pane.getHeight());
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        translateTransition.setOnFinished(actionEvent -> pane.getChildren().remove(text));
        // Start the animation
        translateTransition.play();
    }


    private void startingOfGame() {
        PlayBoard currentPlayBoard = Game.getCurrentGame().getCurrentUser().getPlayBoard();
        PlayBoard nextPlayBoard = Game.getCurrentGame().getNextUser().getPlayBoard();
        for (Card card : currentPlayBoard.getDeckUnit().getCards()) {
            card.setPrefWidth(50);
            card.setPrefHeight(90);
            setCardOnMouseClicked(card);
        }
        for (Card card : nextPlayBoard.getDeckUnit().getCards()) {
            card.setPrefWidth(50);
            card.setPrefHeight(90);
            setCardOnMouseClicked(card);
        }
        Collections.shuffle(currentPlayBoard.getDeckUnit().getCards());
        Collections.shuffle(nextPlayBoard.getDeckUnit().getCards());
        int number = 0;
        if (Game.getCurrentGame().getTurnNumber() == 0) {
            for (Card card : currentPlayBoard.getDeckUnit().getCards()) {
                if (number == 10) break;
                currentPlayBoard.getHandUnit().addCardToUnit(card);
                number++;
            }
//            number = 0;
//            for (Card card : nextPlayBoard.getDeckUnit().getCards()) {
//                if (number == 10) break;
//                nextPlayBoard.getHandUnit().addCardToUnit(card);
//                number++;
//            }
        }
        FactionLeaderCard myFactionLead = Game.getCurrentGame().getCurrentUser().getFactionLeaderCard();
        myFactionLead.setPrefHeight(myLeader.getPrefHeight());
        myFactionLead.setPrefWidth(myLeader.getPrefWidth());
//        FactionLeaderCard enemyFactionLead = Game.getCurrentGame().getNextUser().getFactionLeaderCard();
//        enemyFactionLead.setPrefHeight(myLeader.getPrefHeight());
//        enemyFactionLead.setPrefWidth(myLeader.getPrefWidth());
        myLeader.getChildren().add(myFactionLead);
//        enemyLeader.getChildren().add(enemyFactionLead);
        myLeader.setOnMouseClicked(mouseEvent -> {
            myFactionLead.apply();
            goNextTurn();
        });
//        enemyLeader.setOnMouseClicked(mouseEvent -> {
//            myFactionLead.apply();
//            goNextRound();
//        });
        setDeckImage(Game.getCurrentGame().getCurrentUser().getFaction(), myDeck);
//        setDeckImage(Game.getCurrentGame().getNextUser().getFaction(), enemyDeck);
    }

    public void setDeckImage(Faction faction, AnchorPane pane) {
        ImageView imageView = new ImageView();
        if (faction instanceof MonstersFaction) {
            imageView = new ImageView(new Image(
                    GameMenu.class.getResource("/pics/monsters/faction/MonstersFaction.jpg").toExternalForm()));
        } else if (faction instanceof EmpireNilfgaardianFaction) {
            imageView = new ImageView(new Image(
                    GameMenu.class.getResource("/pics/nilfgaard/faction/NilfgaardFaction.jpg").toExternalForm()));
        } else if (faction instanceof RealmsNorthenFaction) {
            imageView = new ImageView(new Image(
                    GameMenu.class.getResource("/pics/northenRealms/faction/NorthenRealmsFaction.jpg").toExternalForm()));
        } else if (faction instanceof SkelligeFaction) {
            imageView = new ImageView(new Image(
                    GameMenu.class.getResource("/pics/skellige/faction/SkelligeFaction.jpg").toExternalForm()));
        } else if (faction instanceof ScoiataelFaction) {
            imageView = new ImageView(new Image(
                    GameMenu.class.getResource("/pics/scoiatael/faction/scoiataelFaction.jpg").toExternalForm()));
        }
        imageView.setFitHeight(pane.getPrefHeight());
        imageView.setFitWidth(pane.getPrefWidth());
        pane.getChildren().add(imageView);
    }

    public void fillingUnits() {
        updateCards(currentHand);
        updateCards(weatherUnit);
        updateCards(currentClose);
        updateCards(currentRanged);
        updateCards(currentSiege);
        updateCards(nextClose);
        updateCards(nextRanged);
        updateCards(nextSiege);
    }

    private void updateCards(ObservableList<Card> cards) {
        if (cards.size() == 0) return;
        ArrayList<Card> historyCards = new ArrayList<>();
        for (Card card : cards) {
            historyCards.add(card);
        }
        for (int i = cards.size() - 1; i >= 0; i--) {
            cards.remove(cards.get(i));
        }
        for (Card card : historyCards) {
            System.out.println(card.getName());
        }
        System.out.println(historyCards);
        for (Card card : historyCards) {
            cards.add(card);
        }
    }

    public void updatePowerText() {
        PlayBoard enemyPlayboard = Game.getCurrentGame().getNextUser().getPlayBoard();
        enemyClosePower.setText(Integer.toString(enemyPlayboard.getCloseCombatUnit().getUnitPower()));
        enemyRangedPower.setText(Integer.toString(enemyPlayboard.getRangedCombatUnit().getUnitPower()));
        enemySiegePower.setText(Integer.toString(enemyPlayboard.getSiegeUnit().getUnitPower()));
        enemyTotalPower.setText(Integer.toString(Integer.parseInt(enemyClosePower.getText()) +
                Integer.parseInt(enemyRangedPower.getText()) + Integer.parseInt(enemySiegePower.getText())));
    }

    private void addPassing() {
        pass.setOnMouseClicked(mouseEvent -> {
            if (Game.getCurrentGame().getTurnNumber() % 2 == 0) {
                Game.getCurrentGame().setMePassRound(true);
                if (Game.getCurrentGame().isEnemyPassRound()) {
                    int myPower = Integer.parseInt(myTotalPower.getText());
                    int enemyPower = Integer.parseInt(enemyTotalPower.getText());
                    int size = Game.getCurrentGame().getRoundsScore().size();
                    ArrayList<Integer> scores = new ArrayList<>();
                    scores.add(myPower);
                    scores.add(enemyPower);
                    System.out.println("size:" + size + ",arr:" + scores);
                    Game.getCurrentGame().getRoundsScore().put(size, scores);
                    if (myPower > enemyPower) {
                        Game.getCurrentGame().setEnemyLive(Game.getCurrentGame().getEnemyLive() - 1);
                        checkLives(enemyLives);
                    } else if (enemyPower > myPower) {
                        Game.getCurrentGame().setMylive(Game.getCurrentGame().getMylive() - 1);
                        checkLives(myLives);
                    }
                    if (Game.getCurrentGame().getRoundsScore().size() == 3) {
                        try {
                            this.stop();
                            FinishGameMenu finishGameMenu = new FinishGameMenu();
                            finishGameMenu.start(App.getStage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        finishRoundForMe(Game.getCurrentGame().getCurrentUser().getPlayBoard().getCloseCombatUnit());
                        finishRoundForMe(Game.getCurrentGame().getCurrentUser().getPlayBoard().getRangedCombatUnit());
                        finishRoundForMe(Game.getCurrentGame().getCurrentUser().getPlayBoard().getSiegeUnit());
                        finishRoundForEnemy(Game.getCurrentGame().getNextUser().getPlayBoard().getCloseCombatUnit());
                        finishRoundForEnemy(Game.getCurrentGame().getNextUser().getPlayBoard().getRangedCombatUnit());
                        finishRoundForEnemy(Game.getCurrentGame().getNextUser().getPlayBoard().getSiegeUnit());
                        settingPowersToZero(myTotalPower, mySiegePower, myRangedPower,
                                myClosePower, enemyTotalPower, enemySiegePower, enemyRangedPower, enemyClosePower);
                        emptySpell();
                        Game.getCurrentGame().setMePassRound(false);
                        Game.getCurrentGame().setEnemyPassRound(false);
                    }
                }
                goNextTurn();
                App.getGameClient().sendMessage("pass");
            }
        });
    }

    private void emptySpell() {
        Game.getCurrentGame().getSpellUnit().getCards().clear();
    }

    public void settingPowersToZero(TextField myTotalPower, TextField mySiegePower, TextField myRangedPower,
                                    TextField myClosePower, TextField enemyTotalPower, TextField enemySiegePower,
                                    TextField enemyRangedPower, TextField enemyClosePower) {
        myTotalPower.setText("0");
        mySiegePower.setText("0");
        myRangedPower.setText("0");
        myClosePower.setText("0");
        enemyTotalPower.setText("0");
        enemySiegePower.setText("0");
        enemyRangedPower.setText("0");
        enemyClosePower.setText("0");
    }

    public void finishRoundForMe(Unit unit) {
        for (Card card : unit.getCards()) {
            card.setUnit(Game.getCurrentGame().getCurrentUser().getPlayBoard().getDiscardPileUnit());
            Game.getCurrentGame().getCurrentUser().getPlayBoard().getDiscardPileUnit().addCardToUnit(card);
            if (!myDiscard.getChildren().contains(card)) myDiscard.getChildren().add(card);
        }
        unit.getCards().clear();
    }

    public void finishRoundForEnemy(Unit unit) {
        for (Card card : unit.getCards()) {
            card.setUnit(Game.getCurrentGame().getNextUser().getPlayBoard().getDiscardPileUnit());
            Game.getCurrentGame().getNextUser().getPlayBoard().getDiscardPileUnit().addCardToUnit(card);
            if (!enemyDiscard.getChildren().contains(card)) enemyDiscard.getChildren().add(card);
        }
        unit.getCards().clear();
    }

    private void checkLives(Text lives) {
        int live = Integer.parseInt(lives.getText().substring(8));
        if (live == 2) lives.setText("lives : 1");
        else if (live == 1) {
            try {
                if (Game.getCurrentGame().getRoundsScore().size() == 2) {
                    Game.getCurrentGame().getRoundsScore().put(2, new ArrayList<>(List.of(new Integer[]{0, 0})));
                }
                App.getGameClient().sendMessage("pass");
                this.stop();
                FinishGameMenu finishGameMenu = new FinishGameMenu();
                finishGameMenu.start(App.getStage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addListeners() {
        PlayBoard currentPlayBoard = Game.getCurrentGame().getCurrentUser().getPlayBoard();
        PlayBoard nextPlayBoard = Game.getCurrentGame().getNextUser().getPlayBoard();
        weatherUnit = Game.getCurrentGame().getSpellUnit().getCards();
        currentHand = currentPlayBoard.getHandUnit().getCards();
        currentClose = currentPlayBoard.getCloseCombatUnit().getCards();
        currentRanged = currentPlayBoard.getRangedCombatUnit().getCards();
        currentSiege = currentPlayBoard.getSiegeUnit().getCards();
        nextClose = nextPlayBoard.getCloseCombatUnit().getCards();
        nextRanged = nextPlayBoard.getRangedCombatUnit().getCards();
        nextSiege = nextPlayBoard.getSiegeUnit().getCards();
        currentHand.addListener((ListChangeListener.Change<? extends Card> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Card card : change.getAddedSubList()) {
                        // Debugging to check instances
                        System.out.println("Adding card: " + card + " with hashCode: " + card.hashCode());
                        if (!tilePane.getChildren().contains(card)) {
                            tilePane.getChildren().add(card);
                        } else {
                            System.out.println("Duplicate card not added: " + card);
                        }
                    }
                } else if (change.wasRemoved()) {
                    tilePane.getChildren().removeAll(change.getRemoved());
                }
            }
        });
        currentClose.addListener((ListChangeListener.Change<? extends Card> change) -> {
            System.out.println("======");
            while (change.next()) {
                if (change.wasAdded()) {
                    System.out.println("->" + change.getAddedSubList());
                    for (Card card : change.getAddedSubList()) {
                        // Debugging to check instances
                        System.out.println("Adding card: " + card + " with hashCode: " + card.hashCode());
                        if (!myCloseTile.getChildren().contains(card)) {
                            myCloseTile.getChildren().add(card);
                        } else {
                            System.out.println("Duplicate card not added: " + card);
                        }
                    }
                } else if (change.wasRemoved()) {
                    myCloseTile.getChildren().removeAll(change.getRemoved());
                }
                myClosePower.setText(Integer.toString(currentPlayBoard.getCloseCombatUnit().getUnitPower()));
            }
            System.out.println("======");
        });
        currentRanged.addListener((ListChangeListener.Change<? extends Card> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Card card : change.getAddedSubList()) {
                        // Debugging to check instances
                        System.out.println("Adding card: " + card + " with hashCode: " + card.hashCode());
                        if (!myRangedTile.getChildren().contains(card)) {
                            myRangedTile.getChildren().add(card);
                        } else {
                            System.out.println("Duplicate card not added: " + card);
                        }
                    }
                } else if (change.wasRemoved()) {
                    myRangedTile.getChildren().removeAll(change.getRemoved());
                }
            }
        });
        currentSiege.addListener((ListChangeListener.Change<? extends Card> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Card card : change.getAddedSubList()) {
                        // Debugging to check instances
                        System.out.println("Adding card: " + card + " with hashCode: " + card.hashCode());
                        if (!mySiegeTile.getChildren().contains(card)) {
                            mySiegeTile.getChildren().add(card);
                        } else {
                            System.out.println("Duplicate card not added: " + card);
                        }
                    }
                } else if (change.wasRemoved()) {
                    mySiegeTile.getChildren().removeAll(change.getRemoved());
                }
            }
        });
        nextClose.addListener((ListChangeListener.Change<? extends Card> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Card card : change.getAddedSubList()) {
                        // Debugging to check instances
                        System.out.println("Adding card: " + card + " with hashCode: " + card.hashCode());
                        if (!enemyCloseTile.getChildren().contains(card)) {
                            enemyCloseTile.getChildren().add(card);
                        } else {
                            System.out.println("Duplicate card not added: " + card);
                        }
                    }
                } else if (change.wasRemoved()) {
                    enemyCloseTile.getChildren().removeAll(change.getRemoved());
                }
            }
        });
        nextRanged.addListener((ListChangeListener.Change<? extends Card> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Card card : change.getAddedSubList()) {
                        // Debugging to check instances
                        System.out.println("Adding card: " + card + " with hashCode: " + card.hashCode());
                        if (!enemyRangedTile.getChildren().contains(card)) {
                            enemyRangedTile.getChildren().add(card);
                        } else {
                            System.out.println("Duplicate card not added: " + card);
                        }
                    }
                } else if (change.wasRemoved()) {
                    enemyRangedTile.getChildren().removeAll(change.getRemoved());
                }
            }
        });
        nextSiege.addListener((ListChangeListener.Change<? extends Card> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Card card : change.getAddedSubList()) {
                        // Debugging to check instances
                        System.out.println("Adding card: " + card + " with hashCode: " + card.hashCode());
                        if (!enemySiegeTile.getChildren().contains(card)) {
                            enemySiegeTile.getChildren().add(card);
                        } else {
                            System.out.println("Duplicate card not added: " + card);
                        }
                    }
                } else if (change.wasRemoved()) {
                    enemySiegeTile.getChildren().removeAll(change.getRemoved());
                }
            }
        });
        weatherUnit.addListener((ListChangeListener.Change<? extends Card> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Card card : change.getAddedSubList()) {
                        // Debugging to check instances
                        System.out.println("Adding card: " + card + " with hashCode: " + card.hashCode());
                        if (!spellTile.getChildren().contains(card)) {
                            spellTile.getChildren().add(card);
                        } else {
                            System.out.println("Duplicate card not added: " + card);
                        }
                    }
                } else if (change.wasRemoved()) {
                    spellTile.getChildren().removeAll(change.getRemoved());
                }
            }
        });

    }

    private void setCardOnMouseClicked(Card card) {
        PlayBoard currentPlayBoard = Game.getCurrentGame().getCurrentUser().getPlayBoard();
        PlayBoard nextPlayBoard = Game.getCurrentGame().getNextUser().getPlayBoard();
        card.setOnMouseReleased(mouseEvent -> {
            System.out.println(card.getUnit() != null);
            System.out.println(!(card.getUnit() instanceof WarUnit));
            System.out.println(Game.getCurrentGame().getTurnNumber() % 2 == 0);
            System.out.println(!Game.getCurrentGame().isMePassRound());
            if (card.getUnit() != null && !(card.getUnit() instanceof WarUnit)
                    && Game.getCurrentGame().getTurnNumber() % 2 == 0 && !Game.getCurrentGame().isMePassRound()) {
                System.out.println(mouseEvent.getSceneX());
                System.out.println(mouseEvent.getSceneY());
                String type = card.getName().substring(card.getName().indexOf("<"));
                type = type.substring(1, type.length() - 1);
                System.out.println(type);
                boolean isSpy = card instanceof Spy;
                boolean isDecoy = card instanceof Decoy;
                boolean isMedic = card instanceof Medic;
                if (isDecoy) {
                    decoy(card, mouseEvent, currentPlayBoard.getCloseCombatUnit());
                    decoy(card, mouseEvent, currentPlayBoard.getRangedCombatUnit());
                    decoy(card, mouseEvent, currentPlayBoard.getSiegeUnit());
                } else if (isMedic) {
                    medic(card, mouseEvent, currentPlayBoard.getCloseCombatUnit());
                    medic(card, mouseEvent, currentPlayBoard.getRangedCombatUnit());
                    medic(card, mouseEvent, currentPlayBoard.getSiegeUnit());
                } else if (!isSpy) {
                    if (type.equals("close")) {
                        if (mouseEvent.getSceneX() > 530 && mouseEvent.getSceneX() < 1130
                                && mouseEvent.getSceneY() > 330 && mouseEvent.getSceneY() < 410) {
                            controller.putCard(card, currentPlayBoard.getCloseCombatUnit(), true);
                            if (!Game.getCurrentGame().isEnemyPassRound())
                                goNextTurn();
                        }
                    } else if (type.equals("ranged")) {
                        if (mouseEvent.getSceneX() > 530 && mouseEvent.getSceneX() < 1130
                                && mouseEvent.getSceneY() > 420 && mouseEvent.getSceneY() < 500) {
                            controller.putCard(card, currentPlayBoard.getRangedCombatUnit(), true);
                            if (!Game.getCurrentGame().isEnemyPassRound())
                                goNextTurn();
                        }
                    } else if (type.equals("siege")) {
                        if (mouseEvent.getSceneX() > 530 && mouseEvent.getSceneX() < 1130
                                && mouseEvent.getSceneY() > 510 && mouseEvent.getSceneY() < 590) {
                            controller.putCard(card, currentPlayBoard.getSiegeUnit(), true);
                            if (!Game.getCurrentGame().isEnemyPassRound())
                                goNextTurn();
                        }
                    } else if (type.equals("agile")) {
                        if (mouseEvent.getSceneX() > 530 && mouseEvent.getSceneX() < 1130
                                && mouseEvent.getSceneY() > 330 && mouseEvent.getSceneY() < 410) {
                            controller.putCard(card, currentPlayBoard.getCloseCombatUnit(), true);
                            if (!Game.getCurrentGame().isEnemyPassRound())
                                goNextTurn();
                        } else if (mouseEvent.getSceneX() > 530 && mouseEvent.getSceneX() < 1130
                                && mouseEvent.getSceneY() > 420 && mouseEvent.getSceneY() < 500) {
                            controller.putCard(card, currentPlayBoard.getRangedCombatUnit(), true);
                            if (!Game.getCurrentGame().isEnemyPassRound())
                                goNextTurn();
                        }
                    } else if (type.equals("weather")) {
                        if (mouseEvent.getSceneX() > 110 && mouseEvent.getSceneX() < 310
                                && mouseEvent.getSceneY() > 340 && mouseEvent.getSceneY() < 430) {
                            tilePane.getChildren().remove(card);
                            if (spellTile.getChildren().size() == 3) {
                                spellTile.getChildren().remove(0);
                            }
                            controller.putCard(card, Game.getCurrentGame().getSpellUnit(), true);
                            if (!Game.getCurrentGame().isEnemyPassRound())
                                goNextTurn();
                        }
                    }
                } else {
                    if (type.equals("close")) {
                        if (mouseEvent.getSceneX() > 530 && mouseEvent.getSceneX() < 1130
                                && mouseEvent.getSceneY() > 220 && mouseEvent.getSceneY() < 300) {
                            controller.putCard(card, nextPlayBoard.getCloseCombatUnit(), false);
                            if (!Game.getCurrentGame().isEnemyPassRound())
                                goNextTurn();
                        }
                    } else if (type.equals("ranged")) {
                        if (mouseEvent.getSceneX() > 530 && mouseEvent.getSceneX() < 1130
                                && mouseEvent.getSceneY() > 130 && mouseEvent.getSceneY() < 210) {
                            controller.putCard(card, nextPlayBoard.getRangedCombatUnit(), false);
                            if (!Game.getCurrentGame().isEnemyPassRound())
                                goNextTurn();
                        }
                    } else if (type.equals("siege")) {
                        if (mouseEvent.getSceneX() > 530 && mouseEvent.getSceneX() < 1130
                                && mouseEvent.getSceneY() > 40 && mouseEvent.getSceneY() < 120) {
                            controller.putCard(card, nextPlayBoard.getSiegeUnit(), false);
                            if (!Game.getCurrentGame().isEnemyPassRound())
                                goNextTurn();
                        }
                    } else if (type.equals("agile")) {
                        if (mouseEvent.getSceneX() > 530 && mouseEvent.getSceneX() < 1130
                                && mouseEvent.getSceneY() > 220 && mouseEvent.getSceneY() < 300) {
                            controller.putCard(card, nextPlayBoard.getCloseCombatUnit(), false);
                            if (!Game.getCurrentGame().isEnemyPassRound())
                                goNextTurn();
                        } else if (mouseEvent.getSceneX() > 530 && mouseEvent.getSceneX() < 1130
                                && mouseEvent.getSceneY() > 130 && mouseEvent.getSceneY() < 210) {
                            controller.putCard(card, nextPlayBoard.getRangedCombatUnit(), false);
                            if (!Game.getCurrentGame().isEnemyPassRound())
                                goNextTurn();
                        }
                    }
                }
            }
        });
    }

    private void goNextTurn() {
        //TODO: if one of player pss round
        App.getGameClient().sendMessage("ready for game:" + Game.getCurrentGame().getCurrentUser().toJson());
        App.getGameClient().sendMessage("enemy update:"+ Game.getCurrentGame().getNextUser().toJson());
        App.getGameClient().sendMessage("spell update:(spellUnit<"+Game.getCurrentGame().getSpellUnit().arrayToJson()+">)");
        Game.getCurrentGame().setTurnNumber(Game.getCurrentGame().getTurnNumber() + 1);
//        Alert alert = new Alert(Alert.AlertType.WARNING);
//        alert.setContentText("please give the game to the next user{ Dont look at next hand :)}");
//        alert.setOnCloseRequest(dialogEvent -> {
//            try {
//                this.stop();
//                Game.getCurrentGame().setTurnNumber(Game.getCurrentGame().getTurnNumber() + 1);
//                if (Game.getCurrentGame().getTurnNumber() % 2 == 0) {
//                    Game.getCurrentGame().setCurrentUser(Game.getCurrentGame().getMe());
//                    Game.getCurrentGame().setNextUser(Game.getCurrentGame().getEnemy());
//                } else {
//                    Game.getCurrentGame().setCurrentUser(Game.getCurrentGame().getEnemy());
//                    Game.getCurrentGame().setNextUser(Game.getCurrentGame().getMe());
//                }
//                GameMenu gameMenu = new GameMenu();
//                gameMenu.start(App.getStage());
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });
//        alert.show();
    }

    private void decoy(Card card, MouseEvent mouseEvent, Unit unit) {
        for (Card card1 : unit.getCards()) {
            System.out.println(card1.getName() + " " + mouseEvent.getSceneX() + " " + mouseEvent.getSceneY()
                    + " " + card1.localToScene(card1.getBoundsInLocal()).getMinX() + " " +
                    card1.localToScene(card1.getBoundsInLocal()).getMinY());
            if (mouseEvent.getSceneX() < card1.localToScene(card1.getBoundsInLocal()).getMinX() + card1.getPrefWidth()
                    && mouseEvent.getSceneX() > card1.localToScene(card1.getBoundsInLocal()).getMinX()
                    && mouseEvent.getSceneY() < card1.localToScene(card1.getBoundsInLocal()).getMinY() + card1.getPrefHeight()
                    && mouseEvent.getSceneY() > card1.localToScene(card1.getBoundsInLocal()).getMinY()) {
                System.out.println(card1.getName());
                ((Decoy) card).setCardShouldBeReplaced(card1);
                controller.putCard(card, unit, true);
                break;
            }
        }
    }

    private void medic(Card card, MouseEvent mouseEvent, Unit unit) {
        for (Card card1 : unit.getCards()) {
            System.out.println(card1.getName() + " " + mouseEvent.getSceneX() + " " + mouseEvent.getSceneY()
                    + " " + card1.localToScene(card1.getBoundsInLocal()).getMinX() + " " +
                    card1.localToScene(card1.getBoundsInLocal()).getMinY());
            if (mouseEvent.getSceneX() < card1.localToScene(card1.getBoundsInLocal()).getMinX() + card1.getPrefWidth()
                    && mouseEvent.getSceneX() > card1.localToScene(card1.getBoundsInLocal()).getMinX()
                    && mouseEvent.getSceneY() < card1.localToScene(card1.getBoundsInLocal()).getMinY() + card1.getPrefHeight()
                    && mouseEvent.getSceneY() > card1.localToScene(card1.getBoundsInLocal()).getMinY()) {
                System.out.println(card1.getName());
                ((Medic) card).setChosenCard(card1);
                controller.putCard(card, unit, true);
                break;
            }
        }
    }

    private void transparentTextFields() {
        enemyClosePower.setBackground(Background.EMPTY);
        enemyRangedPower.setBackground(Background.EMPTY);
        enemySiegePower.setBackground(Background.EMPTY);
        enemyTotalPower.setBackground(Background.EMPTY);
        myTotalPower.setBackground(Background.EMPTY);
        myRangedPower.setBackground(Background.EMPTY);
        myClosePower.setBackground(Background.EMPTY);
        mySiegePower.setBackground(Background.EMPTY);
        enemyTotalPower.setEditable(false);
        enemySiegePower.setEditable(false);
        enemyRangedPower.setEditable(false);
        enemyClosePower.setEditable(false);
        myTotalPower.setEditable(false);
        mySiegePower.setEditable(false);
        myRangedPower.setEditable(false);
        myTotalPower.setEditable(false);
        //TODO : change power
        settingPowersToZero(enemyClosePower, enemyRangedPower, enemySiegePower, enemyTotalPower, myTotalPower, myClosePower, myRangedPower, mySiegePower);
    }


    private void initializeElements(Scene scene) {
        pass = (Button) scene.lookup("#pass");
        enemyClosePower = (TextField) scene.lookup("#enemyClosePower");
        enemyRangedPower = (TextField) scene.lookup("#enemyRangedPower");
        enemySiegePower = (TextField) scene.lookup("#enemySiegePower");
        myClosePower = (TextField) scene.lookup("#myClosePower");
        myRangedPower = (TextField) scene.lookup("#myRangedPower");
        mySiegePower = (TextField) scene.lookup("#mySiegePower");
        enemyTotalPower = (TextField) scene.lookup("#enemyTotalPower");
        myTotalPower = (TextField) scene.lookup("#myTotalPower");
        myDeck = (AnchorPane) scene.lookup("#myDeck");
        enemyDeck = (AnchorPane) scene.lookup("#enemyDeck");
        enemyDiscard = (AnchorPane) scene.lookup("#enemyDiscard");
        myDiscard = (AnchorPane) scene.lookup("#myDiscard");
        mySiegeSpell = (AnchorPane) scene.lookup("#mySiegeSpell");
        myRangedSpell = (AnchorPane) scene.lookup("#myRangedSpell");
        myCloseSpell = (AnchorPane) scene.lookup("#myCloseSpell");
        enemySiegeSpell = (AnchorPane) scene.lookup("#enemySiegeSpell");
        enemyCloseSpell = (AnchorPane) scene.lookup("#enemyCloseSpell");
        enemyRangedSpell = (AnchorPane) scene.lookup("#enemyRangedSpell");
        enemySiege = (AnchorPane) scene.lookup("#enemySiege");
        enemyRanged = (AnchorPane) scene.lookup("#enemyRanged");
        enemyClose = (AnchorPane) scene.lookup("#enemyClose");
        myClose = (AnchorPane) scene.lookup("#myClose");
        myRanged = (AnchorPane) scene.lookup("#myRanged");
        mySiege = (AnchorPane) scene.lookup("#mySiege");
        enemyLeader = (AnchorPane) scene.lookup("#enemyLeader");
        myLeader = (AnchorPane) scene.lookup("#myLeader");
        spell = (AnchorPane) scene.lookup("#spell");
        scrollPane = (ScrollPane) scene.lookup("#scrollPane");
        tilePane = (TilePane) scrollPane.getContent().lookup("#tilePane");
        spellTile = (TilePane) spell.getChildren().get(0);
        enemyCloseTile = (TilePane) enemyClose.getChildren().get(0);
        enemyRangedTile = (TilePane) enemyRanged.getChildren().get(0);
        enemySiegeTile = (TilePane) enemySiege.getChildren().get(0);
        mySiegeTile = (TilePane) mySiege.getChildren().get(0);
        myCloseTile = (TilePane) myClose.getChildren().get(0);
        myRangedTile = (TilePane) myRanged.getChildren().get(0);
        myLives = (Text) scene.lookup("#myLives");
        enemyLives = (Text) scene.lookup("#enemyLives");
        myLives.setText("lives : 2");
        enemyLives.setText("lives : 2");
        Game.getCurrentGame().setEnemyLive(2);
        Game.getCurrentGame().setMylive(2);
        niceImg = (ImageView) scene.lookup("#niceImg");
        badImg = (ImageView) scene.lookup("#badImg");
        nicePlay = (Text) scene.lookup("#nicePlay");
        notGood = (Text) scene.lookup("#notGood");
        chatBox = (Button) scene.lookup("#chatBox");
        buttonOfCheatMenu = (Button) scene.lookup("#buttonOfCheatMenu");
        writeOnMouseClickedFunctionForButtonOfCheatMenu();
        bad = new ImageView(new Image(GameMenu.class.getResource("/pics/bad.png").toExternalForm()));
        nice = new ImageView(new Image(GameMenu.class.getResource("/pics/nice.png").toExternalForm()));
    }
    private void writeOnMouseClickedFunctionForButtonOfCheatMenu(){
        buttonOfCheatMenu.setOnMouseClicked(e ->{
            try {
                goToCheatMenu();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    private void goToCheatMenu() throws Exception {
        Stage stage = new Stage();
        new CheatMenu(this).start(stage);
    }

    public TextField getEnemyTotalPower() {
        return enemyTotalPower;
    }

    public TextField getEnemyClosePower() {
        return enemyClosePower;
    }

    public TextField getEnemyRangedPower() {
        return enemyRangedPower;
    }

    public TextField getEnemySiegePower() {
        return enemySiegePower;
    }

    public TextField getMyClosePower() {
        return myClosePower;
    }

    public TextField getMyRangedPower() {
        return myRangedPower;
    }

    public TextField getMySiegePower() {
        return mySiegePower;
    }

    public TextField getMyTotalPower() {
        return myTotalPower;
    }

    public ChatBoxMenu getChatBoxMenu() {
        return chatBoxMenu;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void updateEnemyFaction() {
        setDeckImage(Game.getCurrentGame().getNextUser().getFaction(), enemyDeck);
        FactionLeaderCard enemyFactionLead = Game.getCurrentGame().getNextUser().getFactionLeaderCard();
        enemyFactionLead.setPrefHeight(myLeader.getPrefHeight());
        enemyFactionLead.setPrefWidth(myLeader.getPrefWidth());
        enemyLeader.getChildren().add(enemyFactionLead);
    }

    public GameMenuController getController() {
        return controller;
    }
}
