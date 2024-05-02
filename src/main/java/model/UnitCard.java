package model;

public abstract class UnitCard extends Card{
    public UnitCard(String name, int primitiveNumberOfCards, int power, boolean isLegendary) {
        super(name, primitiveNumberOfCards);
        this.power = power;
        this.isLegendary = isLegendary;

    }

    public abstract void apply();
    protected boolean isLegendary;
    protected int power;

    public boolean isLegendary() {
        return isLegendary;
    }

    public void setLegendary(boolean legendary) {
        isLegendary = legendary;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
