package model;

public class CommandersHorn extends Card {
    public CommandersHorn(String name, int primitiveNumberOfCards) {
        super(name, primitiveNumberOfCards);
    }

    @Override
    public void apply() {
        for (Card card : unit.cards) {
            UnitCard unitCard = (UnitCard) card;
            if (!unitCard.isLegendary) unitCard.setPower(unitCard.getPower() * 2);
        }
    }
}
