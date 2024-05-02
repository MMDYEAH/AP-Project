package model;

import java.util.ArrayList;

public class SkelligeFaction extends Faction{
    //TODO: makeCards and add to faction leader card and unit cards
    public SkelligeFaction(ArrayList<FactionLeaderCard> factionLeaderCards, ArrayList<Card> unitCards) {
        super("Skellige", factionLeaderCards, unitCards);
    }

    @Override
    public void apply() {

    }
}
