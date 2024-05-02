package model;

import java.util.ArrayList;

public abstract class Unit {
    protected ArrayList<Card> cards = new ArrayList<>();
    public void addCardToUnit(Card card){
        cards.add(card);
    }
    public void removeCardFromUnit(Card card){
        cards.remove(card);
    }
}
