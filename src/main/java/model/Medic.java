package model;

public class Medic extends UnitCard {
    public Medic(String name, int power, boolean isLegendary, String path) {
        super(name, power, isLegendary, path);
    }

    Card chosenCard;
    @Override
    public void apply() {
    }

    public void setChosenCard(Card chosenCard) {
        this.chosenCard = chosenCard;
    }
    @Override
    public String toJson() {
        return super.toJson()+"(type<Medic>)}";
    }
}
