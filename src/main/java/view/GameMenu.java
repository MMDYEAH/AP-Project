package view;

import controller.GameMenuController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.*;

import java.util.Collections;

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

    private ObservableList<Card> currentHand;
    private ObservableList<Card> currentClose;
    private ObservableList<Card> currentRanged;
    private ObservableList<Card> currentSiege;
    private ObservableList<Card> nextClose;
    private ObservableList<Card> nextRanged;
    private ObservableList<Card> nextSiege;

    private ObservableList<Card> weatherUnit;

    Card chosenCard;
    GameMenuController controller = new GameMenuController(this);
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(GameMenu.class.getResource("/game.fxml"));
        Pane pane = fxmlLoader.load();
        Scene scene = new Scene(pane);
        initializeElements(scene);
        transparentTextFields();
        Faction rn = App.getRealmsNorthenFaction();
        System.out.println(rn.getUnitCards().size());
        scrollPane.setBackground(Background.fill(Color.rgb(89, 45, 6)));
        tilePane.setHgap(10);
        tilePane.setBackground(Background.fill(Color.rgb(89, 45, 6)));
        Collections.shuffle(rn.getUnitCards());
        int number = 0;
        for (Card card : rn.getUnitCards()) {
            setCardOnMouseCliked(card);
            chosenCard = card;
            if (number > 9) break;
            card.setPrefWidth(50);
            card.setPrefHeight(90);
            tilePane.getChildren().add(card);
            number++;
        }
        addListeners();
        scrollPane.setContent(new Group(tilePane));
        stage.setTitle("GWENT-GAME");
        stage.setScene(scene);
        stage.show();
    }

    private void addListeners() {
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
                    tilePane.getChildren().addAll(change.getAddedSubList());
                } else if (change.wasRemoved()) {
                    tilePane.getChildren().removeAll(change.getRemoved());
                }
            }
        });
        currentClose.addListener((ListChangeListener.Change<? extends Card> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    myCloseTile.getChildren().addAll(change.getAddedSubList());
                } else if (change.wasRemoved()) {
                    myCloseTile.getChildren().removeAll(change.getRemoved());
                }
                myClosePower.setText(Integer.toString(currentPlayBoard.getCloseCombatUnit().getUnitPower()));
            }
        });
        currentRanged.addListener((ListChangeListener.Change<? extends Card> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    myRangedTile.getChildren().addAll(change.getAddedSubList());
                } else if (change.wasRemoved()) {
                    myRangedTile.getChildren().removeAll(change.getRemoved());
                }
                myRangedPower.setText(Integer.toString(currentPlayBoard.getRangedCombatUnit().getUnitPower()));
            }
        });
        currentSiege.addListener((ListChangeListener.Change<? extends Card> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    mySiegeTile.getChildren().addAll(change.getAddedSubList());
                } else if (change.wasRemoved()) {
                    mySiegeTile.getChildren().removeAll(change.getRemoved());
                }
                mySiegePower.setText(Integer.toString(currentPlayBoard.getSiegeUnit().getUnitPower()));
            }
        });
        nextClose.addListener((ListChangeListener.Change<? extends Card> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    enemyCloseTile.getChildren().addAll(change.getAddedSubList());
                } else if (change.wasRemoved()) {
                    enemyCloseTile.getChildren().removeAll(change.getRemoved());
                }
                enemyClosePower.setText(Integer.toString(nextPlayBoard.getCloseCombatUnit().getUnitPower()));
            }

        });
        nextRanged.addListener((ListChangeListener.Change<? extends Card> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    enemyRangedTile.getChildren().addAll(change.getAddedSubList());
                } else if (change.wasRemoved()) {
                    enemyRangedTile.getChildren().removeAll(change.getRemoved());
                }
                enemyRangedPower.setText(Integer.toString(nextPlayBoard.getRangedCombatUnit().getUnitPower()));
            }
        });
        nextSiege.addListener((ListChangeListener.Change<? extends Card> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    enemySiegeTile.getChildren().addAll(change.getAddedSubList());
                } else if (change.wasRemoved()) {
                    enemySiegeTile.getChildren().removeAll(change.getRemoved());
                }
                enemySiegePower.setText(Integer.toString(nextPlayBoard.getSiegeUnit().getUnitPower()));
            }
        });
        weatherUnit.addListener((ListChangeListener.Change<? extends Card> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    spellTile.getChildren().addAll(change.getAddedSubList());
                } else if (change.wasRemoved()) {
                    spellTile.getChildren().removeAll(change.getRemoved());
                }
            }
        });

    }

    private void setCardOnMouseCliked(Card card) {
        PlayBoard currentPlayBoard = Game.getCurrentGame().getCurrentUser().getPlayBoard();
        PlayBoard nextPlayBoard = Game.getCurrentGame().getNextUser().getPlayBoard();
        card.setOnMouseReleased(mouseEvent -> {
            System.out.println(mouseEvent.getSceneX());
            System.out.println(mouseEvent.getSceneY());
            String type = card.getName().substring(card.getName().indexOf("<"));
            type = type.substring(1, type.length() - 1);
            System.out.println(type);
            boolean isSpy = card instanceof Spy;
            if (!isSpy) {
                if (type.equals("close")) {
                    if (mouseEvent.getSceneX() > 530 && mouseEvent.getSceneX() < 1130
                            && mouseEvent.getSceneY() > 330 && mouseEvent.getSceneY() < 410) {
                        controller.putCard(card,currentPlayBoard.getCloseCombatUnit(),true);
                    }
                } else if (type.equals("ranged")) {
                    if (mouseEvent.getSceneX() > 530 && mouseEvent.getSceneX() < 1130
                            && mouseEvent.getSceneY() > 420 && mouseEvent.getSceneY() < 500) {
                        controller.putCard(card,currentPlayBoard.getRangedCombatUnit(),true);
                    }
                } else if (type.equals("siege")) {
                    if (mouseEvent.getSceneX() > 530 && mouseEvent.getSceneX() < 1130
                            && mouseEvent.getSceneY() > 510 && mouseEvent.getSceneY() < 590) {
                        controller.putCard(card,currentPlayBoard.getSiegeUnit(),true);
                    }
                } else if (type.equals("agile")) {
                    if (mouseEvent.getSceneX() > 530 && mouseEvent.getSceneX() < 1130
                            && mouseEvent.getSceneY() > 330 && mouseEvent.getSceneY() < 410) {
                        controller.putCard(card,currentPlayBoard.getCloseCombatUnit(),true);
                    } else if (mouseEvent.getSceneX() > 530 && mouseEvent.getSceneX() < 1130
                            && mouseEvent.getSceneY() > 420 && mouseEvent.getSceneY() < 500) {
                        controller.putCard(card,currentPlayBoard.getRangedCombatUnit(),true);
                    }
                } else if (type.equals("weather")) {
                    if (mouseEvent.getSceneX() > 110 && mouseEvent.getSceneX() < 310
                            && mouseEvent.getSceneY() > 340 && mouseEvent.getSceneY() < 430) {
                        tilePane.getChildren().remove(card);
                        if (spellTile.getChildren().size() == 3) {
                            spellTile.getChildren().remove(0);
                        }
                        controller.putCard(card,Game.getCurrentGame().getSpellUnit(),true);
                    }
                }
            } else {
                if (type.equals("close")) {
                    if (mouseEvent.getSceneX() > 530 && mouseEvent.getSceneX() < 1130
                            && mouseEvent.getSceneY() > 220 && mouseEvent.getSceneY() < 300) {
                        controller.putCard(card,nextPlayBoard.getCloseCombatUnit(),false);
                    }
                } else if (type.equals("ranged")) {
                    if (mouseEvent.getSceneX() > 530 && mouseEvent.getSceneX() < 1130
                            && mouseEvent.getSceneY() > 130 && mouseEvent.getSceneY() < 210) {
                        controller.putCard(card,nextPlayBoard.getRangedCombatUnit(),false);
                    }
                } else if (type.equals("siege")) {
                    if (mouseEvent.getSceneX() > 530 && mouseEvent.getSceneX() < 1130
                            && mouseEvent.getSceneY() > 40 && mouseEvent.getSceneY() < 120) {
                        controller.putCard(card,nextPlayBoard.getSiegeUnit(),false);
                    }
                } else if (type.equals("agile")) {
                    if (mouseEvent.getSceneX() > 530 && mouseEvent.getSceneX() < 1130
                            && mouseEvent.getSceneY() > 220 && mouseEvent.getSceneY() < 300) {
                        controller.putCard(card,nextPlayBoard.getCloseCombatUnit(),false);
                    } else if (mouseEvent.getSceneX() > 530 && mouseEvent.getSceneX() < 1130
                            && mouseEvent.getSceneY() > 130 && mouseEvent.getSceneY() < 210) {
                        controller.putCard(card,nextPlayBoard.getRangedCombatUnit(),false);
                    }
                }
            }

        });
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
        enemyClosePower.setText("0");
        enemyRangedPower.setText("0");
        enemySiegePower.setText("0");
        enemyTotalPower.setText("0");
        myTotalPower.setText("0");
        myClosePower.setText("0");
        myRangedPower.setText("0");
        mySiegePower.setText("0");
    }


    private void initializeElements(Scene scene) {
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
    }

    public ObservableList<Card> getCurrentHand() {
        return currentHand;
    }

    public ObservableList<Card> getCurrentClose() {
        return currentClose;
    }

    public ObservableList<Card> getCurrentRanged() {
        return currentRanged;
    }

    public ObservableList<Card> getCurrentSiege() {
        return currentSiege;
    }

    public ObservableList<Card> getNextClose() {
        return nextClose;
    }

    public ObservableList<Card> getNextRanged() {
        return nextRanged;
    }

    public ObservableList<Card> getNextSiege() {
        return nextSiege;
    }

    public ObservableList<Card> getWeatherUnit() {
        return weatherUnit;
    }
}
