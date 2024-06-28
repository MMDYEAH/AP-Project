package controller;

import model.*;
import view.GameMenu;

public class GameMenuController {
    GameMenu gameMenu;

    public GameMenuController(GameMenu gameMenu) {
        this.gameMenu = gameMenu;
        Game.getCurrentGame().setController(this);
    }

    public void passRound() {
    }

    public void putCard(Card card, Unit unit, boolean isCurrent) {
        PlayBoard currentPlayBoard = Game.getCurrentGame().getCurrentUser().getPlayBoard();
        PlayBoard nextPlayBoard = Game.getCurrentGame().getNextUser().getPlayBoard();
        if (unit instanceof CloseCombatUnit) {
            if (isCurrent) {
                currentPlayBoard.getCloseCombatUnit().addCardToUnit(card);
                card.setUnit(Game.getCurrentGame().getCurrentUser().getPlayBoard().getCloseCombatUnit());
            } else {
                nextPlayBoard.getCloseCombatUnit().addCardToUnit(card);
                card.setUnit(Game.getCurrentGame().getNextUser().getPlayBoard().getCloseCombatUnit());
            }
        } else if (unit instanceof RangedCombatUnit) {
            if (isCurrent) {
                currentPlayBoard.getRangedCombatUnit().addCardToUnit(card);
                card.setUnit(Game.getCurrentGame().getCurrentUser().getPlayBoard().getRangedCombatUnit());
            }
            else{
                nextPlayBoard.getRangedCombatUnit().addCardToUnit(card);
                card.setUnit(Game.getCurrentGame().getNextUser().getPlayBoard().getRangedCombatUnit());
            }
        } else if (unit instanceof SiegeUnit) {
            if (isCurrent) {
                currentPlayBoard.getSiegeUnit().addCardToUnit(card);
                card.setUnit(Game.getCurrentGame().getCurrentUser().getPlayBoard().getSiegeUnit());
            }
            else{
                nextPlayBoard.getSiegeUnit().addCardToUnit(card);
                card.setUnit(Game.getCurrentGame().getNextUser().getPlayBoard().getSiegeUnit());
            }
        } else if (unit instanceof SpellUnit) {
            Game.getCurrentGame().getSpellUnit().addCardToUnit(card);
            card.setUnit(Game.getCurrentGame().getSpellUnit());
        }
        card.apply();
        doRepeatedCards();
        updatePowerText(currentPlayBoard, nextPlayBoard);
    }

    private void updatePowerText(PlayBoard currentPlayBoard, PlayBoard nextPlayBoard) {
        gameMenu.getMyClosePower().setText(Integer.toString(currentPlayBoard.getCloseCombatUnit().getUnitPower()));
        gameMenu.getMyRangedPower().setText(Integer.toString(currentPlayBoard.getRangedCombatUnit().getUnitPower()));
        gameMenu.getMySiegePower().setText(Integer.toString(currentPlayBoard.getSiegeUnit().getUnitPower()));
        int myTotal = currentPlayBoard.getCloseCombatUnit().getUnitPower()+
                currentPlayBoard.getRangedCombatUnit().getUnitPower()+ currentPlayBoard.getSiegeUnit().getUnitPower();
        gameMenu.getMyTotalPower().setText(Integer.toString(myTotal));
        gameMenu.getEnemyClosePower().setText(Integer.toString(nextPlayBoard.getCloseCombatUnit().getUnitPower()));
        gameMenu.getEnemyRangedPower().setText(Integer.toString(nextPlayBoard.getRangedCombatUnit().getUnitPower()));
        gameMenu.getEnemySiegePower().setText(Integer.toString(nextPlayBoard.getSiegeUnit().getUnitPower()));
        int enemyTotal = nextPlayBoard.getCloseCombatUnit().getUnitPower()+
                nextPlayBoard.getRangedCombatUnit().getUnitPower()+ nextPlayBoard.getSiegeUnit().getUnitPower();
        gameMenu.getEnemyTotalPower().setText(Integer.toString(enemyTotal));
    }
    private void doRepeatedCards(){
        //TODO: complete this like commanders and tightBounds and moral and weathers ;
    }

    public Result showHandCard(int number) {
        return null;//TODO: delete this code and write
    }

    public Result showRemainingCardsToPlay() {
        return null;//TODO: delete this code and write
    }

    public Result showPlayersInfo() {
        return null;//TODO: delete this code and write
    }

    public Result showPlayersLives() {
        return null;//TODO: delete this code and write
    }

    public Result showNumberOfCardsInHand() {
        return null;//TODO: delete this code and write
    }

    public Result showTurnInfo() {
        return null;//TODO: delete this code and write
    }

    public Result showTotalScore() {
        return null;//TODO: delete this code and write
    }

    public Result showTotalScoreOfRow(int rowNumber) {
        return null;//TODO: delete this code and write
    }

    public Result showOutOfPlayCards() {
        return null;//TODO: delete this code and write
    }

    public Result showCardsInRow(int rowNumber) {
        return null;//TODO: delete this code and write
    }

    public Result showSpellsInPlay(int rowNumber) {
        return null;//TODO: delete this code and write
    }

    public Result PlaceCard(int rowNumber, int CardNumber) {
        return null;//TODO: delete this code and write
    }

    public Result showCommander() {
        return null;//TODO: delete this code and write
    }

    public Result PlayCommanderPower() {
        return null;//TODO: delete this code and write
    }

    private void doFinalWorksOfRound() {
    }

    private void doFinalWorksOfGame() {
    }

    private boolean canVeto() {
        return false;//TODO: delete this code and write
    }

    private boolean isCardForThisUnit(int cardNumber, int rowNumber) {
        return false;//TODO: delete this code and write
    }
}
