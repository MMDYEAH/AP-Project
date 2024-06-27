package model;

public class MoralBooster extends UnitCard {
    public MoralBooster(String name, int power, boolean isLegendary, String path) {
        super(name, power, isLegendary, path);
    }

    @Override
    public void apply() {
        for (Card card : unit.cards) {
            UnitCard unitCard = (UnitCard) card;
            if (!unitCard.isLegendary && !unitCard.equals(this)) unitCard.setPower(unitCard.getPower() + 1);
        }
    }
}
