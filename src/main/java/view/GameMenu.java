package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.App;
import model.Card;
import model.Faction;

import java.util.Scanner;

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


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(GameMenu.class.getResource("/game.fxml"));
        Pane pane = fxmlLoader.load();
        Scene scene = new Scene(pane);
        initializeElements(scene);
        transparentTextFields();
        Faction rn = App.getRealmsNorthenFaction();
        System.out.println(rn.getUnitCards().size());
        scrollPane = (ScrollPane) scene.lookup("#scrollPane");
        tilePane = (TilePane) scrollPane.getContent().lookup("#tilePane");
        for (Card card : rn.getUnitCards()){
            tilePane.getChildren().add(card);
        }
        scrollPane.setContent(new Group(tilePane));
        stage.setTitle("GWENT-GAME");
        stage.setScene(scene);
        stage.show();
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
    }
}
