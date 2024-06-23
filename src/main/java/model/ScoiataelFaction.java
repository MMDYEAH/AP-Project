package model;

import java.util.ArrayList;

public class ScoiataelFaction extends Faction{
    //TODO: makeCards and add to faction leader card and unit cards
    public ScoiataelFaction(ArrayList<FactionLeaderCard> factionLeaderCards, ArrayList<Card> unitCards) {
        super("Sociatael", factionLeaderCards, unitCards);
    }

    @Override
    public void apply() {

    }
}
