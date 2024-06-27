package model;

public class TightBound extends UnitCard{
    public TightBound(String name, int power, boolean isLegendary, String path) {
        super(name, power, isLegendary, path);
    }

    @Override
    public void apply() {
        int numberOfSimilarCards = 0;
         for(Card card : this.getUnit().cards){
             UnitCard unitCard = (UnitCard) card;
             if(unitCard.name.equals(this.name) && !unitCard.equals(this))
                 unitCard.setPower(unitCard.getPower()+this.power);
             numberOfSimilarCards++;
         }
         this.setPower(this.getPower()*(numberOfSimilarCards+1));
    }
}
