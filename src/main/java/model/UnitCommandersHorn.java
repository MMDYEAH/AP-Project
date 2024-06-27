package model;

public class UnitCommandersHorn extends UnitCard{
    public UnitCommandersHorn(String name, int power, boolean isLegendary, String path) {
        super(name, power, isLegendary, path);
    }

    @Override
    public void apply() {
        for (Card card : unit.cards) {
            UnitCard unitCard = (UnitCard) card;
            if (!unitCard.isLegendary) unitCard.setPower(unitCard.getPower() * 2);
        }
    }
}
