package model;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Random;

public class MonstersFaction extends Faction {
    //TODO: makeCards and add to faction leader card and unit cards
    public MonstersFaction(ArrayList<FactionLeaderCard> factionLeaderCards, ArrayList<Card> unitCards) {
        super("Monsters", factionLeaderCards, unitCards);
    }

    @Override
    public void apply() {
        ObservableList<Card> deckCards = Game.getCurrentGame().currentUser.getPlayBoard().getDeckUnit().cards;
        Random random = new Random();
        Card randomCard = deckCards.get(random.nextInt(0,deckCards.size()-1));
        //TODO : put random card
    }
}
