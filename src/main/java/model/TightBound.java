package model;

import java.util.ArrayList;

public class TightBound extends UnitCard{
    public TightBound(String name, int power, boolean isLegendary, String path) {
        super(name, power, isLegendary, path);
    }

    @Override
    public void apply() {
        int numberOfSimilarCards = 0;
        ArrayList<Card> similars = new ArrayList<>();
         for(Card card : this.getUnit().cards){
             UnitCard unitCard = (UnitCard) card;
             if(unitCard.name.equals(this.name) && !unitCard.equals(this)) {
                 similars.add(card);
                 numberOfSimilarCards++;
             }
         }
         this.setPower(this.getPower()*(numberOfSimilarCards+1));
         for (Card card : similars) ((UnitCard)card).setPower(((UnitCard)card).getFirstPower()*(numberOfSimilarCards+1));
    }
}
