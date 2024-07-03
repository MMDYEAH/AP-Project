package model;

import java.util.ArrayList;

public class SociataelFaction extends Faction{
    //TODO: makeCards and add to faction leader card and unit cards
    public SociataelFaction(ArrayList<FactionLeaderCard> factionLeaderCards, ArrayList<Card> unitCards) {
        super("Sociatael", factionLeaderCards, unitCards);
    }

    @Override
    public void apply() {

    }
}
