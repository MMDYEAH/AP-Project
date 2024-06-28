package model;

public abstract class WarUnit extends Unit{
    protected Card specialCard;

    public Card getSpecialCard() {
        return specialCard;
    }

    public void setSpecialCard(Card specialCard) {
        this.specialCard = specialCard;
    }

    public int getUnitPower(){
        int power = 0;
        for (Card card : cards){
            if (card instanceof  UnitCard) power += ((UnitCard)card).power;
        }
        return power;
    }
}
