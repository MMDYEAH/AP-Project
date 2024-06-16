package model;

public class MoralBooster extends UnitCard {
    public MoralBooster(String name, int primitiveNumberOfCards, int power, boolean isLegendary) {
        super(name, primitiveNumberOfCards, power, isLegendary);
    }

    @Override
    public void apply() {
        for (Card card : unit.cards) {
            UnitCard unitCard = (UnitCard) card;
            if (!unitCard.isLegendary && !unitCard.equals(this)) unitCard.setPower(unitCard.getPower() + 1);
        }
    }
}
