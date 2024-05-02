package model;

import java.util.ArrayList;

public class MonstersFaction extends Faction {
    //TODO: makeCards and add to faction leader card and unit cards
    public MonstersFaction(ArrayList<FactionLeaderCard> factionLeaderCards, ArrayList<Card> unitCards) {
        super("Monsters", factionLeaderCards, unitCards);
    }

    @Override
    public void apply() {

    }
}
