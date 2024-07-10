package model;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Random;

public class RealmsNorthenFaction extends Faction{
    //TODO: makeCards and add to faction leader card and unit cards
    public RealmsNorthenFaction(ArrayList<FactionLeaderCard> factionLeaderCards, ArrayList<Card> unitCards) {
        super("RealmsNorthen", factionLeaderCards, unitCards);
    }

    @Override
    public void apply() {
        ObservableList<Card> deckCards = Game.getCurrentGame().currentUser.getPlayBoard().getDeckUnit().cards;
        ObservableList<Card> handCards = Game.getCurrentGame().currentUser.getPlayBoard().getHandUnit().cards;
        Random random = new Random();
        Card randomCard = deckCards.get(random.nextInt(0,deckCards.size()-1));
        deckCards.remove(randomCard);
        handCards.add(randomCard);
    }
}
