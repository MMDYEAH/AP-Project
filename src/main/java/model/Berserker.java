package model;

public class Berserker extends UnitCard{
    public Berserker(String name, int power, boolean isLegendary, String path) {
        super(name, power, isLegendary, path);
    }

    @Override
    public void apply() {
        // This method will be left empty as the Berserker's transformation is handled by Mardroeme
    }

    @Override
    public String toJson() {
        return super.toJson()+"(type<Berserker>)}";
    }
}
