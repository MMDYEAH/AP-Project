package model;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;

public class FactionLeaderCard extends Card {
    public FactionLeaderCard(String name, String path) {
        super(name,path);
    }

    @Override
    public void apply() {
        PlayBoard currentPlayerPlayBoard = Game.getCurrentGame().getCurrentUser().getPlayBoard();
        PlayBoard nextPlayerPlayBoard = Game.getCurrentGame().getNextUser().getPlayBoard();
        if (name.equals("The Siegemaster")) {
            for (Card card : currentPlayerPlayBoard.getDeckUnit().cards) {
                if (card instanceof ImpenetrableFog) {
                    card.getUnit().removeCardFromUnit(card);
                    card.apply();
                    card.setUnit(Game.getCurrentGame().spellUnit);
                    card.getUnit().addCardToUnit(card);
                }
            }
        } else if (name.equals("The Steel-Forged")) {
            for (Card card : Game.getCurrentGame().getSpellUnit().cards) {
                ((WeatherCard) card).unApply();
            }
        } else if (name.equals("King of Temeria")) {
            doubleStrangeOfUnit(currentPlayerPlayBoard.getSiegeUnit().cards);
        } else if (name.equals("Lord Commander of the North")) {
            killBiggestUnitCard(nextPlayerPlayBoard.getSiegeUnit().getUnitPower(),
                    nextPlayerPlayBoard.getSiegeUnit().cards);
        } else if (name.equals("Son of Medell")) {
            killBiggestUnitCard(nextPlayerPlayBoard.getRangedCombatUnit().getUnitPower(),
                   nextPlayerPlayBoard.getRangedCombatUnit().cards);
        } else if (name.equals("Bringer of Death")) {
            doubleStrangeOfUnit(currentPlayerPlayBoard.getCloseCombatUnit().cards);
        } else if (name.equals("King Of The Wild Hunt")) {
            ArrayList<Card> notLegends = getNotLegendInUnit(currentPlayerPlayBoard.getDiscardPileUnit());
            Card chosenCard = null;
            //TODO: in graphic using this arraylist of not legends
            chosenCard.apply();
            //TODO: change card unit and remove from arraylist and add to another
        } else if (name.equals("Commander Of Red Riders")) {
            ArrayList<Card> weathers = new ArrayList<>();
            for (Card card: currentPlayerPlayBoard.getDeckUnit().cards){
                if (card instanceof WeatherCard){
                    weathers.add(card);
                }
            }
            //TODO: in graphic using this arraylist of not legends
            Card chosenCard = null;
            //TODO: in graphic
            chosenCard.getUnit().removeCardFromUnit(chosenCard);
            chosenCard.apply();
            chosenCard.setUnit(Game.getCurrentGame().getSpellUnit());
            chosenCard.getUnit().addCardToUnit(chosenCard);
        } else if (name.equals("Destroyer Of Worlds")){
            //TODO: not legendries
            ArrayList<Card> notLegendsInDiscard = getNotLegendInUnit(currentPlayerPlayBoard.getDiscardPileUnit());
            ArrayList<Card> notLegendsInDeck = getNotLegendInUnit(currentPlayerPlayBoard.getDeckUnit());
            //TODO: in graphic using this arraylist of not legends
            ArrayList<Card> cardsWantToDelete = new ArrayList<>();
            Card wantToGet = null;
            //TODO: get them in graphic
            for (Card card : cardsWantToDelete){
                card.getUnit().removeCardFromUnit(card);
                card.setUnit(currentPlayerPlayBoard.getDiscardPileUnit());
                card.getUnit().addCardToUnit(card);
            }
            wantToGet.getUnit().removeCardFromUnit(wantToGet);
            //TODO: change card unit and remove from arraylist and add to another
        } else if (name.equals("The Treacherous")) {
            doubleStrangeOfSpy(currentPlayerPlayBoard.getCloseCombatUnit().cards);
            doubleStrangeOfSpy(currentPlayerPlayBoard.getRangedCombatUnit().cards);
            doubleStrangeOfSpy(currentPlayerPlayBoard.getSiegeUnit().cards);
            doubleStrangeOfSpy(nextPlayerPlayBoard.getCloseCombatUnit().cards);
            doubleStrangeOfSpy(nextPlayerPlayBoard.getRangedCombatUnit().cards);
            doubleStrangeOfSpy(nextPlayerPlayBoard.getSiegeUnit().cards);
        } else if (name.equals("Queen Of Dol Blathanna")){
            killBiggestUnitCard(nextPlayerPlayBoard.getCloseCombatUnit().getUnitPower(),
                    nextPlayerPlayBoard.getCloseCombatUnit().cards);
        } else if (name.equals("The Beautiful")){
            doubleStrangeOfUnit(currentPlayerPlayBoard.getRangedCombatUnit().cards);
        } else if (name.equals("Daisy Of The Valley")) {
            int random = App.getRandom().nextInt(0,currentPlayerPlayBoard.getDeckUnit().cards.size()-1);
            Card card = currentPlayerPlayBoard.getDeckUnit().cards.get(random);
            //TODO: apply in graphic
        } else if (name.equals("Pureblood Elf")){
            for (Card card : currentPlayerPlayBoard.getDeckUnit().cards) {
                if (card instanceof BitingFrost) {
                    card.getUnit().removeCardFromUnit(card);
                    card.apply();
                    card.setUnit(Game.getCurrentGame().spellUnit);
                    card.getUnit().addCardToUnit(card);
                }
            }
        } else if (name.equals("Hope Of The Aen Seidhe")) {
            //TODO: agile card and write this ability
        } else if (name.equals("Crach An Craite")) {
            ObservableList<Card> discardPile = currentPlayerPlayBoard.getDiscardPileUnit().cards;
            Collections.shuffle(discardPile);
            for (Card card : discardPile){
                currentPlayerPlayBoard.getDiscardPileUnit().removeCardFromUnit(card);
                card.setUnit(currentPlayerPlayBoard.getDeckUnit());
                currentPlayerPlayBoard.getDeckUnit().addCardToUnit(card);
            }
        } else if (name.equals("King Bran")) {
            //TODO: need some fields
        } else if (name.equals("Emperor Of Nilfgaard")) {
            //TODO: check and write
        } else if (name.equals("His Impreial Majesty")) {
            ObservableList<Card> enemyCards = nextPlayerPlayBoard.getHandUnit().cards;
            ArrayList<Integer> randomNumbers = new ArrayList<>();
            ArrayList<Card> shownCard = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                int random = App.getRandom().nextInt(0,enemyCards.size());
                while (randomNumbers.contains(random)){
                    random = App.getRandom().nextInt(0,enemyCards.size());
                }
                randomNumbers.add(random);
            }
            for (int i = 0; i < 3; i++) {
                shownCard.add(enemyCards.get(randomNumbers.get(i)));
            }
            //TODO: show in graphic
        } else if (name.equals("Invader Of The North")) {
            int EnemyRandom = App.getRandom().nextInt(0,nextPlayerPlayBoard.getDiscardPileUnit().cards.size());
            int MyRandom = App.getRandom().nextInt(0,nextPlayerPlayBoard.getDiscardPileUnit().cards.size());
            //TODO: shown and change unit of cards and apply
        } else if (name.equals("The Relentless")) {
            //TODO: graphic
        } else if (name.equals("The White Flame")) {
            for (Card card : currentPlayerPlayBoard.getDeckUnit().cards) {
                if (card instanceof TorrentialRain) {
                    card.getUnit().removeCardFromUnit(card);
                    card.apply();
                    card.setUnit(Game.getCurrentGame().spellUnit);
                    card.getUnit().addCardToUnit(card);
                }
            }
        }
    }

    private ArrayList<Card> getNotLegendInUnit(Unit unit) {
        ObservableList<Card> discardPile = unit.cards;
        ArrayList<Card> notLegends = new ArrayList<>();
        for (Card card : discardPile){
            if (!((UnitCard)card).isLegendary){
                notLegends.add(card);
            }
        }
        return notLegends;
    }

    private void killBiggestUnitCard(int power, ObservableList<Card> unit) {
        if (power > 10) {
            UnitCard biggestPowerCard = (UnitCard) unit.get(0);
            for (Card card : unit) {
                if (biggestPowerCard.getPower() < ((UnitCard) card).getPower()) {
                    biggestPowerCard = (UnitCard) card;
                }
            }
            if (!biggestPowerCard.isLegendary){
                biggestPowerCard.setUnit(Game.getCurrentGame().getNextUser().getPlayBoard().getDiscardPileUnit());
                biggestPowerCard.getUnit().addCardToUnit(biggestPowerCard);
                unit.remove(biggestPowerCard);
                //TODO : in graphic
            }
        }
    }

    private void doubleStrangeOfUnit(ObservableList<Card> warUnit) {
        boolean isThereCommandersHorn = false;
        for (Card card : warUnit) {
            if (card instanceof CommandersHorn) isThereCommandersHorn = true;
        }
        if (!isThereCommandersHorn) {
            for (Card card : warUnit) {
                if (!((UnitCard) card).isLegendary) {
                    ((UnitCard) card).setPower(((UnitCard) card).power * 2);
                }
            }
        }
    }

    private void doubleStrangeOfSpy(ObservableList<Card> unitCards){
        for (Card card : unitCards){
            if (card instanceof Spy && !((UnitCard)card).isLegendary){
                ((Spy) card).setPower(((Spy) card).getPower()*2);
            }
        }
    }
    @Override
    public String toJson() {
        return "{leader(name<"+name+">)(path<"+path+">)}";
    }
}
