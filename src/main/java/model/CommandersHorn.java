package model;

public class CommandersHorn extends Card{
    public CommandersHorn(String name, String path) {
        super(name,path);
    }

    @Override
    public void apply() {
        for (Card card : unit.cards) {
            UnitCard unitCard = (UnitCard) card;
            if (!unitCard.isLegendary) {
                if (unitCard.getPower() < unitCard.getFirstPower()*2)
                    unitCard.setPower(unitCard.getPower() * 2);
            }
        }
    }
}
