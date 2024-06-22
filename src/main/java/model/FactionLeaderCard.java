package model;

import java.util.ArrayList;

public class FactionLeaderCard extends Card {
    public FactionLeaderCard(String name) {
        super(name);
    }

    @Override
    public void apply() {
        if (name.equals("The Siegemaster")) {
            for (Card card : Game.getCurrentGame().currentUser.getPlayBoard().handUnit.cards) {
                if (card instanceof ImpenetrableFog) {
                    card.apply();
                    card.unit = Game.currentGame.spellUnit;
                }
            }
        } else if (name.equals("The Steel-Forged")) {
            for (Card card : Game.getCurrentGame().spellUnit.cards) {
                ((WeatherCard) card).unApply();
            }
        } else if (name.equals("King of Temeria")) {
            boolean isThereCommandersHorn = false;
            for (Card card : Game.getCurrentGame().currentUser.getPlayBoard().siegeUnit.cards) {
                if (card instanceof CommandersHorn) isThereCommandersHorn = true;
            }
            if (!isThereCommandersHorn) {
                for (Card card : Game.getCurrentGame().currentUser.getPlayBoard().siegeUnit.cards) {
                    if (!((UnitCard) card).isLegendary) {
                        ((UnitCard) card).setPower(((UnitCard) card).power * 2);
                    }
                }
            }
        } else if (name.equals("Lord Commander of the North")) {
            if (Game.getCurrentGame().nextUser.getPlayBoard().siegeUnit.getUnitPower() > 10) {
                UnitCard biggestPowerCard = (UnitCard) Game.getCurrentGame().nextUser.getPlayBoard().siegeUnit.cards.get(0);
                for (Card card : Game.getCurrentGame().nextUser.getPlayBoard().siegeUnit.cards) {
                    if (biggestPowerCard.getPower() < ((UnitCard) card).getPower()) {
                        biggestPowerCard = (UnitCard) card;
                    }
                }
                biggestPowerCard.setUnit(Game.getCurrentGame().nextUser.getPlayBoard().discardPileUnit);
                Game.getCurrentGame().nextUser.getPlayBoard().siegeUnit.cards.remove(biggestPowerCard);
                //TODO : in graphic
            }
        } else if (name.equals("Son of Medell")) {
            if (Game.getCurrentGame().nextUser.getPlayBoard().rangedCombatUnit.getUnitPower() > 10) {
                UnitCard biggestPowerCard = (UnitCard) Game.getCurrentGame().nextUser.getPlayBoard().rangedCombatUnit.cards.get(0);
                for (Card card : Game.getCurrentGame().nextUser.getPlayBoard().rangedCombatUnit.cards) {
                    if (biggestPowerCard.getPower() < ((UnitCard) card).getPower()) {
                        biggestPowerCard = (UnitCard) card;
                    }
                }
                biggestPowerCard.setUnit(Game.getCurrentGame().nextUser.getPlayBoard().discardPileUnit);
                Game.getCurrentGame().nextUser.getPlayBoard().rangedCombatUnit.cards.remove(biggestPowerCard);
                //TODO : in graphic
            }
        }
    }
}
