package model;

public class UnitCommandersHorn extends UnitCard{
    public UnitCommandersHorn(String name, int power, boolean isLegendary, String path) {
        super(name, power, isLegendary, path);
    }

    @Override
    public void apply() {
        for (Card card : unit.cards) {
            UnitCard unitCard = (UnitCard) card;
            if (unitCard instanceof TightBound){
                int similars = 0;
                for (Card c : unit.cards){
                    if (c.getName().equals(unitCard.getName())) similars++;
                }
                if (!unitCard.isLegendary) {
                    if (unitCard.getPower() < unitCard.getFirstPower()*similars*2)
                        unitCard.setPower(unitCard.getPower() * 2);
                }
            }
            if (!unitCard.isLegendary && !(card instanceof UnitCommandersHorn)) {
                if (unitCard.getPower() < unitCard.getFirstPower()*2)
                    unitCard.setPower(unitCard.getPower() * 2);
            }
        }
    }

    @Override
    public String toJson() {
        return super.toJson()+"(type<UnitCommandersHorn>)}";
    }
}
