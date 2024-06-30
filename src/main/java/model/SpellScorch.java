package model;

import java.util.ArrayList;

public class SpellScorch extends Card{
    public SpellScorch(String name, String path) {
        super(name, path);
    }

    @Override
    public void apply() {
       Unit enemyUnit = getUnitOfEnemyUser();
       int mostPower = getMostPowerOfTheseUnits(this.unit,enemyUnit);
       ArrayList<Card> cardsOfCurrentUser = getStrongestCardsOfUnit(this.unit,mostPower);
       for(Card card : cardsOfCurrentUser){
           card.unit.removeCardFromUnit(card);
           Game.currentGame.getCurrentUser().getPlayBoard().getDiscardPileUnit().addCardToUnit(card);
       }
       ArrayList<Card> cardsOfEnemyUser = getStrongestCardsOfUnit(enemyUnit,mostPower);
       for(Card card : cardsOfEnemyUser){
           card.unit.removeCardFromUnit(card);
           Game.currentGame.enemy.getPlayBoard().getDiscardPileUnit().addCardToUnit(card);
       }
    }
    private Unit getUnitOfEnemyUser(){
        if(this.unit instanceof RangedCombatUnit)
            return Game.getCurrentGame().getEnemy().getPlayBoard().getRangedCombatUnit();
        else if(this.unit instanceof CloseCombatUnit)
            return Game.getCurrentGame().getEnemy().getPlayBoard().getCloseCombatUnit();
        else if(this.unit instanceof SiegeUnit)
            return Game.getCurrentGame().getEnemy().getPlayBoard().getSiegeUnit();
        else return null;
    }
    private int getMostPowerOfTheseUnits(Unit unit1,Unit unit2){
        ArrayList<Card> allCards = new ArrayList<>();
        allCards.addAll(unit1.cards);
        allCards.addAll(unit2.cards);
        int mostPower = 0;
        for(Card card : allCards){
            UnitCard unitCard = (UnitCard) card;
            if(unitCard.power > mostPower && !unitCard.isLegendary)
                mostPower = unitCard.power;
        }
        return mostPower;
    }
    private ArrayList<Card> getStrongestCardsOfUnit(Unit unit,int mostPower){
        ArrayList<Card> strongestCards = new ArrayList<>();
        for(Card card : unit.cards){
            UnitCard unitCard = (UnitCard) card;
            if(unitCard.power == mostPower && !unitCard.isLegendary)
                strongestCards.add(unitCard);
        }
        return strongestCards;
    }
}
