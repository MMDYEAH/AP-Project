package model;

import java.util.ArrayList;

public abstract class Faction {
    protected String name;
    protected ArrayList<FactionLeaderCard> factionLeaderCards = new ArrayList<>();

    public Faction(String name, ArrayList<FactionLeaderCard> factionLeaderCards, ArrayList<Card> unitCards) {
        this.name = name;
        this.factionLeaderCards = factionLeaderCards;
        this.unitCards = unitCards;
    }

    protected ArrayList<Card> unitCards = new ArrayList<>();
    public abstract void apply();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<FactionLeaderCard> getFactionLeaderCards() {
        return factionLeaderCards;
    }

    public void setFactionLeaderCards(ArrayList<FactionLeaderCard> factionLeaderCards) {
        this.factionLeaderCards = factionLeaderCards;
    }

    public ArrayList<Card> getUnitCards() {
        return unitCards;
    }

    public void setUnitCards(ArrayList<Card> unitCards) {
        this.unitCards = unitCards;
    }
}
