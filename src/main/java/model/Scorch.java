package model;

import java.util.ArrayList;

public class Scorch extends UnitCard {
    public Scorch(String name, int power, boolean isLegendary, String path) {
        super(name, power, isLegendary, path);
    }

    @Override
    public void apply() {
        if (this.name.equals("ClanDimunPirate")) {
            for (Card card : getStrongestCards()) {
                card.unit.removeCardFromUnit(card);
                Game.currentGame.enemy.getPlayBoard().getDiscardPileUnit().addCardToUnit(card);
            }
        } else if (this.name.equals("Schirru") &&
                isSumOfPowersInThisUnitMoreThanTen(Game.getCurrentGame().enemy.getPlayBoard().siegeUnit))
            killStrongestCardsInThisUnit(Game.getCurrentGame().enemy.getPlayBoard().siegeUnit);
        else if(this.name.equals("Toad") &&
        isSumOfPowersInThisUnitMoreThanTen(Game.getCurrentGame().enemy.getPlayBoard().rangedCombatUnit))
            killStrongestCardsInThisUnit(Game.getCurrentGame().enemy.getPlayBoard().rangedCombatUnit);
        else if(this.name.equals("Villentretenmerth") &&
        isSumOfPowersInThisUnitMoreThanTen(Game.getCurrentGame().enemy.getPlayBoard().closeCombatUnit))
            killStrongestCardsInThisUnit(Game.getCurrentGame().enemy.getPlayBoard().closeCombatUnit);

    }

    private int getMostPowerInEnemyCards() {
        int mostPower = 0;
        ArrayList<Card> allCardsOfEnemy = new ArrayList<>();
        allCardsOfEnemy.addAll(Game.getCurrentGame().enemy.getPlayBoard().getCloseCombatUnit().cards);
        allCardsOfEnemy.addAll(Game.getCurrentGame().enemy.getPlayBoard().getRangedCombatUnit().cards);
        allCardsOfEnemy.addAll(Game.getCurrentGame().enemy.getPlayBoard().getSiegeUnit().cards);
        for (Card card : allCardsOfEnemy) {
            UnitCard unitCard = (UnitCard) card;
            if (mostPower < unitCard.getPower() && !unitCard.isLegendary)
                mostPower = unitCard.power;
        }
        return mostPower;
    }

    private ArrayList<Card> getStrongestCards() {
        ArrayList<Card> allCardsOfEnemy = new ArrayList<>();
        allCardsOfEnemy.addAll(Game.getCurrentGame().enemy.getPlayBoard().getCloseCombatUnit().cards);
        allCardsOfEnemy.addAll(Game.getCurrentGame().enemy.getPlayBoard().getRangedCombatUnit().cards);
        allCardsOfEnemy.addAll(Game.getCurrentGame().enemy.getPlayBoard().getSiegeUnit().cards);
        ArrayList<Card> strongestCards = new ArrayList<>();
        int mostPower = getMostPowerInEnemyCards();
        for (Card card : allCardsOfEnemy) {
            UnitCard unitCard = (UnitCard) card;
            if (unitCard.getPower() == mostPower && !unitCard.isLegendary)
                strongestCards.add(unitCard);
        }
        return strongestCards;
    }

    private boolean isSumOfPowersInThisUnitMoreThanTen(Unit unit) {
        int sumOfPowers = 0;
        for (Card card : unit.cards) {
            UnitCard unitCard = (UnitCard) card;
            if (!unitCard.isLegendary) sumOfPowers += unitCard.power;
        }
        if (sumOfPowers >= 10) return true;
        else return false;
    }

    private void killStrongestCardsInThisUnit(Unit unit) {
        int mostPower = 0;
        for (Card card : unit.cards) {
            UnitCard unitCard = (UnitCard) card;
            if (!unitCard.isLegendary && unitCard.power > mostPower)
                mostPower = unitCard.power;
        }
        for (Card card : unit.cards) {
            UnitCard unitCard = (UnitCard) card;
            if (!unitCard.isLegendary && unitCard.power == mostPower) {
                unitCard.unit.removeCardFromUnit(unitCard);
                Game.currentGame.enemy.getPlayBoard().getDiscardPileUnit().cards.add(unitCard);
            }
        }
    }
}
