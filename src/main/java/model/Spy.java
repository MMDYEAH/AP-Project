package model;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Random;

public class Spy extends UnitCard {
    public Spy(String name, int power, boolean isLegendary, String path) {
        super(name, power, isLegendary, path);
    }

    @Override
    public void apply() {
        ObservableList<Card> deckCards = Game.getCurrentGame().getCurrentUser().getPlayBoard().getDeckUnit().cards;
        for (int i = 0; i < 2; i++) {
            if (deckCards.isEmpty()) break;
            Random random = new Random();
            Card card = deckCards.get(random.nextInt(0, deckCards.size() - 1));
            deckCards.remove(card);
            Game.currentGame.getCurrentUser().getPlayBoard().getHandUnit().addCardToUnit(card);
            card.setUnit(Game.getCurrentGame().getCurrentUser().getPlayBoard().getHandUnit());
        }
    }
}
