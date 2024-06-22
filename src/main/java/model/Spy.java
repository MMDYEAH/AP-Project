package model;

import java.util.ArrayList;
import java.util.Random;

public class Spy extends UnitCard {
    public Spy(String name, int primitiveNumberOfCards, int power, boolean isLegendary) {
        super(name, primitiveNumberOfCards, power, isLegendary);
    }

    @Override
    public void apply() {
        ArrayList<Card> deckCards = Game.getCurrentGame().currentUser.getPlayBoard().deckUnit.cards;
        ArrayList<Card> handCards = Game.getCurrentGame().currentUser.getPlayBoard().getHandUnit().cards;
        for (int i = 0; i < 2; i++) {
            if (deckCards.isEmpty()) break;
            Random random = new Random();
            Card card = deckCards.get(random.nextInt(0, deckCards.size() - 1));
            deckCards.remove(card);
            handCards.add(card);
            card.setUnit(Game.getCurrentGame().currentUser.getPlayBoard().handUnit);
        }
    }
}
