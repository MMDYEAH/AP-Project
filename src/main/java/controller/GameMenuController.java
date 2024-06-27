package controller;

import model.*;
import view.GameMenu;

public class GameMenuController {
    GameMenu gameMenu;

    public GameMenuController(GameMenu gameMenu) {
        this.gameMenu = gameMenu;
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
