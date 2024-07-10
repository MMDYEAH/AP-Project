package model;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Random;

public class SkelligeFaction extends Faction{
    //TODO: makeCards and add to faction leader card and unit cards
    public SkelligeFaction(ArrayList<FactionLeaderCard> factionLeaderCards, ArrayList<Card> unitCards) {
        super("Skellige", factionLeaderCards, unitCards);
    }

    @Override
    public void apply() {
       ObservableList<Card> cards = Game.getCurrentGame().getCurrentUser().getPlayBoard().getDiscardPileUnit().cards;
        Random random = new Random();
        Card card1 = cards.get(random.nextInt(0,cards.size()-1));
        cards.remove(card1);
        Card card2 = cards.get(random.nextInt(0,cards.size()-1));
        cards.remove(card2);
        //TODO : put random cards
    }
}
