package model;

public abstract class WarUnit extends Unit{
    protected Card specialCard;

    public Card getSpecialCard() {
        return specialCard;
    }

    public void setSpecialCard(Card specialCard) {
        this.specialCard = specialCard;
    }
}
