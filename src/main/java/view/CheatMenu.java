package view;

import controller.GameMenuController;
import enums.CheatCodes;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.App;
import model.Card;
import model.Game;

import java.util.Random;

public class CheatMenu extends Application {
    @FXML
    private Button buttonOfBackToGame;
    @FXML
    private TextField cheatCode;
    private GameMenu gameMenu;
    public CheatMenu(GameMenu gameMenu){
        this.gameMenu = gameMenu;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(CheatMenu.class.getResource("/CheatMenu.fxml"));
        Pane pane = fxmlLoader.load();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        cheatCode = (TextField) scene.lookup("#cheatCode");
        buttonOfBackToGame = (Button) scene.lookup("#buttonOfBackToGame");
        buttonOfBackToGame.setOnMouseClicked(e -> {
            try {
                backToGame(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        stage.show();
    }

    public void applyCheatCode() {
        if(CheatCodes.ADD_CARD_FORM_DECK_TO_MY_HAND.getApplyOfCheatCode(cheatCode.getText())!=null){
            // we add a random card from deck of current user to hand of him
            Random random = new Random();
            Card card = Game.getCurrentGame().getCurrentUser().getPlayBoard().getDeckUnit().getCards().get
                    (random.nextInt(0,Game.getCurrentGame().getCurrentUser().getPlayBoard().getDeckUnit().getCards().size()-1));
            Game.getCurrentGame().getCurrentUser().getPlayBoard().getDeckUnit().getCards().remove(card);
            Game.getCurrentGame().getCurrentUser().getPlayBoard().getHandUnit().addCardToUnit(card);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Done successfully");
            alert.show();
        }
        else if(CheatCodes.REMOVE_CARD_FROM_ENEMY_HAND.getApplyOfCheatCode(cheatCode.getText())!=null){
            // we remove a random card from enemy hand and add it to his deck
            Random random = new Random();
            Card card = Game.getCurrentGame().getEnemy().getPlayBoard().getHandUnit().getCards().get
                    (random.nextInt(0,Game.getCurrentGame().getEnemy().getPlayBoard().getHandUnit().getCards().size()-1));
            Game.getCurrentGame().getEnemy().getPlayBoard().getHandUnit().getCards().remove(card);
            Game.getCurrentGame().getEnemy().getPlayBoard().getDeckUnit().addCardToUnit(card);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Done successfully");
            alert.show();
        }
        else if(CheatCodes.DESTROY_SIEGE_FACTION_OF_ENEMY.getApplyOfCheatCode(cheatCode.getText())!=null){
            // we remove all cards in siege of enemy user
            for(Card card : Game.getCurrentGame().getEnemy().getPlayBoard().getSiegeUnit().getCards()){
                Game.getCurrentGame().getEnemy().getPlayBoard().getDiscardPileUnit().addCardToUnit(card);
            }
            Game.getCurrentGame().getEnemy().getPlayBoard().getSiegeUnit().getCards().clear();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Done successfully");
            alert.show();
        }
        else if(CheatCodes.DESTROY_CLOSE_COMBAT_OF_ENEMY.getApplyOfCheatCode(cheatCode.getText())!=null){
            // we remove all cards in close combat of enemy user
            for(Card card : Game.getCurrentGame().getEnemy().getPlayBoard().getRangedCombatUnit().getCards()){
                Game.getCurrentGame().getEnemy().getPlayBoard().getDiscardPileUnit().addCardToUnit(card);
            }
            Game.getCurrentGame().getEnemy().getPlayBoard().getCloseCombatUnit().getCards().clear();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Done successfully");
            alert.show();
        }
        else if(CheatCodes.DESTROY_RANGED_COMBAT_OF_ENEMY.getApplyOfCheatCode(cheatCode.getText())!=null){
            // we remove all cards in ranged combat of enemy user
            for(Card card : Game.getCurrentGame().getEnemy().getPlayBoard().getCloseCombatUnit().getCards()){
                Game.getCurrentGame().getEnemy().getPlayBoard().getDiscardPileUnit().addCardToUnit(card);
            }
            Game.getCurrentGame().getEnemy().getPlayBoard().getRangedCombatUnit().getCards().clear();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Done successfully");
            alert.show();
        }
        else if(CheatCodes.ADD_CARD_FROM_HAND_TO_BORD.getApplyOfCheatCode(cheatCode.getText())!=null){
            // we add a random card from hand to bord
            Random random = new Random();
            Card card = Game.getCurrentGame().getCurrentUser().getPlayBoard().getHandUnit().getCards().get
                    (random.nextInt(0,Game.getCurrentGame().getCurrentUser().getPlayBoard().getHandUnit()
                            .getCards().size()-1));
            Game.getCurrentGame().getEnemy().getPlayBoard().getHandUnit().getCards().remove(card);
            if(card.getName().contains("<siege>")) gameMenu.controller.putCard(card,Game.getCurrentGame().getCurrentUser()
                    .getPlayBoard().getSiegeUnit(),true);
            else if(card.getName().contains("<ranged>")) gameMenu.controller.putCard(card,Game.getCurrentGame().getCurrentUser()
                    .getPlayBoard().getRangedCombatUnit(),true);
            else if(card.getName().contains("<close>")) gameMenu.controller.putCard(card,Game.getCurrentGame().getCurrentUser()
                    .getPlayBoard().getCloseCombatUnit(),true);
            else gameMenu.controller.putCard(card,Game.getCurrentGame().getSpellUnit(),true);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Done successfully");
            alert.show();

        }
        else if(CheatCodes.REMOVE_ALL_CARDS_OF_CURRENT_USER.getApplyOfCheatCode(cheatCode.getText())!=null){
            Game.getCurrentGame().getCurrentUser().getPlayBoard().getSiegeUnit().getCards().clear();
            Game.getCurrentGame().getCurrentUser().getPlayBoard().getCloseCombatUnit().getCards().clear();
            Game.getCurrentGame().getCurrentUser().getPlayBoard().getRangedCombatUnit().getCards().clear();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Done successfully");
            alert.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid cheat code!");
            alert.show();
        }
    }

    public void backToGame(Stage stage) throws Exception {
        stage.close();
    }
}
