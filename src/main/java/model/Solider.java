package model;

public class Solider extends UnitCard{
    public Solider(String name, int power, boolean isLegendary, String path) {
        super(name, power, isLegendary, path);
    }

    @Override
    public void apply() {

    }

    @Override
    public String toJson() {
        return super.toJson()+"(type<Solider>)}";
    }
}
