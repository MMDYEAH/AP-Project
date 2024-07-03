package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public abstract class Unit {
    protected ObservableList<Card> cards = FXCollections.observableList(new ArrayList<>());
    public void addCardToUnit(Card card){
        cards.add(card);
    }
    public void removeCardFromUnit(Card card){
        cards.remove(card);
    }

    public ObservableList<Card> getCards() {
        return cards;
    }

    public void setCards(ObservableList<Card> cards) {
        this.cards = cards;
    }
    public abstract String toJson();

    public abstract String arrayToJson();
}
