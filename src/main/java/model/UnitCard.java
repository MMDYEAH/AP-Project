package model;

public abstract class UnitCard extends Card{
    protected boolean isLegendary;
    protected int power;
    protected int firstPower;
    public UnitCard(String name, int power, boolean isLegendary) {
        super(name);
        this.power = power;
        this.firstPower = power;
        this.isLegendary = isLegendary;

    }
    public abstract void apply();

    public int getFirstPower() {
        return firstPower;
    }

    public void setFirstPower(int firstPower) {
        this.firstPower = firstPower;
    }

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
