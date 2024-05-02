package model;

public abstract class Card {
    protected String name;
    protected Unit unit;
    protected int primitiveNumberOfCards;
    protected int remainingNumberOfCards;
    
    public abstract void apply();

    public Card(String name, int primitiveNumberOfCards) {
        this.name = name;
        this.primitiveNumberOfCards = primitiveNumberOfCards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public int getPrimitiveNumberOfCards() {
        return primitiveNumberOfCards;
    }

    public void setPrimitiveNumberOfCards(int primitiveNumberOfCards) {
        this.primitiveNumberOfCards = primitiveNumberOfCards;
    }

    public int getRemainingNumberOfCards() {
        return remainingNumberOfCards;
    }

    public void setRemainingNumberOfCards(int remainingNumberOfCards) {
        this.remainingNumberOfCards = remainingNumberOfCards;
    }
}
