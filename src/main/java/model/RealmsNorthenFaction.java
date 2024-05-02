package model;

import java.util.ArrayList;

public class RealmsNorthenFaction extends Faction{
    //TODO: makeCards and add to faction leader card and unit cards
    public RealmsNorthenFaction(ArrayList<FactionLeaderCard> factionLeaderCards, ArrayList<Card> unitCards) {
        super("RealmsNorthen", factionLeaderCards, unitCards);
    }

    @Override
    public void apply() {

    }
}
