package model;

public class Berserker extends UnitCard{
    public Berserker(String name, int power, boolean isLegendary, String path) {
        super(name, power, isLegendary, path);
    }

    @Override
    public void apply() {

    }

    @Override
    public String toJson() {
        return super.toJson()+"(type<Berserker>)}";
    }
}
